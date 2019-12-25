package com.billow.alipay.pc.service.impl;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.billow.alipay.pc.utils.AliPayUtils;
import com.billow.alipay.base.service.impl.AliPayTradeBaseServiceImpl;
import com.billow.alipay.pc.config.AliPayPcConfig;
import com.billow.alipay.pc.model.OrderInfo;
import com.billow.alipay.pc.service.AliPayPcService;
import com.billow.alipay.pc.service.AliPayUpdateOrderStausService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

/**
 * 支付宝 pc 支付
 *
 * @author liuyongtao
 * @create 2019-12-22 15:06
 */
@Slf4j
public class AliPayPcServiceImpl extends AliPayTradeBaseServiceImpl implements AliPayPcService {

    // alipay 配置数据
    private AliPayPcConfig aliPayPcConfig;
    // alipay 基本参数
    private AlipayClient alipayClient;

    public AliPayPcServiceImpl(@NonNull AliPayPcConfig aliPayPcConfig) {
        this.aliPayPcConfig = aliPayPcConfig;
        //获得初始化的AlipayClient
        this.alipayClient = new DefaultAlipayClient(aliPayPcConfig.getGatewayUrl(), aliPayPcConfig.getAppId(),
                aliPayPcConfig.getPrivateKey(), aliPayPcConfig.getFormat(), aliPayPcConfig.getCharset(),
                aliPayPcConfig.getAliPayPublicKey(), aliPayPcConfig.getSignType());
        super.setAlipayClient(this.alipayClient);
    }

    @Override
    public void tradePage(HttpServletResponse response, AlipayTradePagePayModel model) throws Exception {
        // 固定的
        model.setProductCode("FAST_INSTANT_TRADE_PAY");
        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(aliPayPcConfig.getReturnUrl());
        alipayRequest.setNotifyUrl(aliPayPcConfig.getNotifyUrl());
        alipayRequest.setBizModel(model);
        //请求
        String form = alipayClient.pageExecute(alipayRequest).getBody();
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.write(form);
        out.flush();
        out.close();
    }

    @Override
    public String returnUrl(HttpServletRequest request, AliPayUpdateOrderStausService updateOrderStausService) {
        // TODO 跳转到支付状态页面，使用异步来获取支付结果
        log.debug("同步通知========>>>>>");
        try {
            return this.callBackNotify(request, updateOrderStausService);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        log.debug("return_url 验证失败");
        return "failure";
    }

    @Override
    public String notifyUrl(HttpServletRequest request, AliPayUpdateOrderStausService updateOrderStausService) {
        log.debug("异常通知========>>>>>");
        try {
            return this.callBackNotify(request, updateOrderStausService);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        log.debug("notify_url 验证失败");
        return "failure";
    }

    private String callBackNotify(HttpServletRequest request, AliPayUpdateOrderStausService updateOrderStausService) throws Exception {
        Map<String, String> map = AliPayUtils.toMap(request);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            log.debug("异步回调返回：" + entry.getKey() + " = " + entry.getValue());
        }

        // 验签名
        boolean verifyResult = AlipaySignature.rsaCheckV1(map, aliPayPcConfig.getAliPayPublicKey(),
                aliPayPcConfig.getCharset(), aliPayPcConfig.getSignType());
        log.debug("验证:{}", verifyResult);
        if (verifyResult) {
            if (updateOrderStausService != null) {
                OrderInfo orderInfo = new OrderInfo(map.get("app_id"), map.get("out_trade_no"),
                        map.get("total_amount"), map.get("trade_no"));
                // 业务校验
                updateOrderStausService.checkOrderData(orderInfo);
                // 更新订单状态
                updateOrderStausService.updateOrderStaus(orderInfo);
            }
            return "success";
        }
        return "failure";
    }
}
