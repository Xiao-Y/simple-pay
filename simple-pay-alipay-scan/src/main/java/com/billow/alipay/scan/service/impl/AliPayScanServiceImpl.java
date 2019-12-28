package com.billow.alipay.scan.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.billow.alipay.base.service.impl.AliPayTradeBaseServiceImpl;
import com.billow.alipay.base.utils.AliPayUtils;
import com.billow.alipay.scan.config.AliPayScanConfig;
import com.billow.alipay.scan.model.OrderInfo;
import com.billow.alipay.scan.service.AliPayScanService;
import com.billow.alipay.scan.service.AliPayUpdateOrderStausService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 支付扫描处理
 *
 * @author liuyongtao
 * @create 2019-12-27 9:55
 */
@Slf4j
public class AliPayScanServiceImpl extends AliPayTradeBaseServiceImpl implements AliPayScanService {

    private AliPayScanConfig aliPayScanConfig;

    private AlipayClient alipayClient;

    public AliPayScanServiceImpl(@NonNull AliPayScanConfig aliPayScanConfig) {
        this.aliPayScanConfig = aliPayScanConfig;
        this.alipayClient = new DefaultAlipayClient(aliPayScanConfig.getGatewayUrl(), aliPayScanConfig.getAppId(),
                aliPayScanConfig.getPrivateKey(), aliPayScanConfig.getFormat(), aliPayScanConfig.getCharset(),
                aliPayScanConfig.getAliPayPublicKey(), aliPayScanConfig.getSignType());
        super.setAlipayClient(this.alipayClient);
    }

    @Override
    public String tradePrecreate(AlipayTradePrecreateModel model) throws Exception {
        log.debug("请求入参：{}", JSONObject.toJSONString(model));
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        request.setBizModel(model);
        request.setNotifyUrl(aliPayScanConfig.getNotifyUrl());
        AlipayTradePrecreateResponse execute = alipayClient.execute(request);
        log.debug("请求出参：{}", JSONObject.toJSONString(execute));
        if (!execute.isSuccess()) {
            log.error(execute.getSubMsg());
            throw new RuntimeException("统一收单线下交易预创建,预创建失败，请稍后重试！");
        }
        return execute.getQrCode();
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
        log.debug("请求入参：{}", JSONObject.toJSONString(map));
        // 验签名
        boolean verifyResult = AlipaySignature.rsaCheckV1(map, aliPayScanConfig.getAliPayPublicKey(),
                aliPayScanConfig.getCharset(), aliPayScanConfig.getSignType());
        log.debug("验证:{}", verifyResult);
        if (verifyResult) {
            // 业务处理
            if (updateOrderStausService != null) {
                OrderInfo orderInfo = new OrderInfo(map.get("app_id"), map.get("out_trade_no"),
                        map.get("total_amount"), map.get("trade_no"));
                // 业务校验
                updateOrderStausService.checkOrderData(orderInfo);
                // 更新订单状态
                updateOrderStausService.updateOrderStatus(orderInfo);
            }
            return "success";
        }
        return "failure";
    }
}
