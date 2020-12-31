package com.billow.alipay.scan.starter.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.billow.alipay.base.service.impl.AliPayBaseServiceImpl;
import com.billow.alipay.base.utils.AliPayUtils;
import com.billow.alipay.scan.starter.properties.AliPayScanProperties;
import com.billow.alipay.scan.starter.model.OrderInfo;
import com.billow.alipay.scan.starter.service.AliPayScanService;
import com.billow.alipay.scan.starter.service.AliPayUpdateOrderStausService;
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
public class AliPayScanServiceImpl extends AliPayBaseServiceImpl implements AliPayScanService {

    private AliPayScanProperties aliPayScanProperties;

    private AlipayClient alipayClient;

    public AliPayScanServiceImpl(@NonNull AliPayScanProperties aliPayScanProperties) {
        this.aliPayScanProperties = aliPayScanProperties;
        this.alipayClient = new DefaultAlipayClient(aliPayScanProperties.getGatewayUrl(), aliPayScanProperties.getAppId(),
                aliPayScanProperties.getPrivateKey(), aliPayScanProperties.getFormat(), aliPayScanProperties.getCharset(),
                aliPayScanProperties.getAliPayPublicKey(), aliPayScanProperties.getSignType());
        super.setAlipayClient(this.alipayClient);
    }

    @Override
    public String tradePrecreate(AlipayTradePrecreateModel model) throws Exception {
        log.debug("请求入参：{}", JSONObject.toJSONString(model));
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        request.setBizModel(model);
        request.setNotifyUrl(aliPayScanProperties.getNotifyUrl());
        AlipayTradePrecreateResponse execute = alipayClient.execute(request);
        log.debug("请求出参：{}", JSONObject.toJSONString(execute));
        if (!execute.isSuccess()) {
            log.error(execute.getSubMsg());
            throw new RuntimeException("统一收单线下交易预创建,预创建失败，请稍后重试！");
        }
        return execute.getQrCode();
    }

    @Override
    public OrderInfo notifyUrl(HttpServletRequest request, AliPayUpdateOrderStausService updateOrderStausService) {
        log.debug("异常通知========>>>>>");
        try {
            return this.callBackNotify(request, updateOrderStausService);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        log.debug("notify_url 验证失败");
        return new OrderInfo();
    }

    /**
     * 回调通知，更新校验订单、更新订单状态
     *
     * @param request
     * @param updateOrderStausService
     * @return java.lang.String
     * @author LiuYongTao
     * @date 2020/5/26 18:06
     */
    private OrderInfo callBackNotify(HttpServletRequest request, AliPayUpdateOrderStausService updateOrderStausService) throws Exception {
        OrderInfo orderInfo = new OrderInfo();
        // 通知返回参数转map
        Map<String, String> map = AliPayUtils.toMap(request);
        log.debug("请求入参：{}", JSONObject.toJSONString(map));
        // 填充通知返回参数
        orderInfo.setAppId(map.get("app_id"));
        orderInfo.setOutTradeNo(map.get("out_trade_no"));
        orderInfo.setTotalAmount(map.get("total_amount"));
        orderInfo.setTradeNo(map.get("trade_no"));
        // 验签名
        boolean verifyResult = AlipaySignature.rsaCheckV1(map, aliPayScanProperties.getAliPayPublicKey(),
                aliPayScanProperties.getCharset(), aliPayScanProperties.getSignType());
        log.debug("验证:{}", verifyResult);
        if (verifyResult) {
            orderInfo.setPaySataus(true);
            if (updateOrderStausService != null) {
                try {
                    // 业务校验
                    boolean flag = updateOrderStausService.checkOrderData(orderInfo);
                    // 更新订单状态
                    if (flag) {
                        updateOrderStausService.updateOrderStatus(orderInfo);
                    }
                } catch (Exception e) {
                    log.error("订单更新异常，订单号：{}", orderInfo.getOutTradeNo(), e);
                    orderInfo.setPaySataus(false);
                }
            }
        }
        return orderInfo;
    }
}
