package com.billow.base.trade;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.billow.base.config.AliPayBaseConfig;
import lombok.NonNull;

/**
 * 交易查询接口
 *
 * @author liuyongtao
 * @create 2019-12-23 22:01
 */
public class AliPayQuery {

    private AliPayBaseConfig aliPayBaseConfig;

    public AliPayQuery(@NonNull AliPayBaseConfig aliPayBaseConfig) {
        this.aliPayBaseConfig = aliPayBaseConfig;
    }

    /**
     * 统一收单线下交易查询接口，https://docs.open.alipay.com/api_1/alipay.trade.query
     *
     * @param queryModel:outTradeNo,tradeNo 订单支付时传入的商户订单号,和支付宝交易号不能同时为空。trade_no,out_trade_no如果同时存在优先取trade_no <br/>
     * @return com.alipay.api.response.AlipayTradeQueryResponse
     * @author billow
     * @date 2019/12/23 22:16
     */
    public AlipayTradeQueryResponse tradeQuery(AlipayTradeQueryModel queryModel) throws Exception {
        if (queryModel.getOutTradeNo() == null && queryModel.getTradeNo() == null) {
            throw new RuntimeException("outTradeNo,tradeNo 订单支付时传入的商户订单号,和支付宝交易号不能同时为空");
        }
        AlipayClient alipayClient = new DefaultAlipayClient(aliPayBaseConfig.getGatewayUrl(), aliPayBaseConfig.getAppId(),
                aliPayBaseConfig.getPrivateKey(), aliPayBaseConfig.getFormat(), aliPayBaseConfig.getCharset(),
                aliPayBaseConfig.getAliPayPublicKey(), aliPayBaseConfig.getSignType());
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.setBizModel(queryModel);
        AlipayTradeQueryResponse response = alipayClient.execute(request);
        // 如果没有成功，2秒后再查询一次
        if (!response.isSuccess()) {
            Thread.sleep(2000);
            response = alipayClient.execute(request);
        }
        if (!response.isSuccess()) {
            throw new RuntimeException("统一收单线下交易查询接口,查询失败，请稍后重试！");
        }
        return response;
    }

    /**
     * 统一收单交易退款查询 https://docs.open.alipay.com/api_1/alipay.trade.fastpay.refund.query
     *
     * @param refundModel outTradeNo,tradeNo 订单支付时传入的商户订单号,和支付宝交易号不能同时为空。trade_no,out_trade_no如果同时存在优先取trade_no <br/>
     *                    outRequestNo 请求退款接口时，传入的退款请求号，如果在退款请求时未传入，则该值为创建交易时的外部交易号
     * @return com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse
     * @author billow
     * @date 2019/12/23 22:31
     */
    public AlipayTradeFastpayRefundQueryResponse refundQuery(AlipayTradeRefundModel refundModel) throws Exception {
        if (refundModel.getOutTradeNo() == null && refundModel.getTradeNo() == null) {
            throw new RuntimeException("outTradeNo,tradeNo 订单支付时传入的商户订单号,和支付宝交易号不能同时为空");
        }
        AlipayClient alipayClient = new DefaultAlipayClient(aliPayBaseConfig.getGatewayUrl(), aliPayBaseConfig.getAppId(),
                aliPayBaseConfig.getPrivateKey(), aliPayBaseConfig.getFormat(), aliPayBaseConfig.getCharset(),
                aliPayBaseConfig.getAliPayPublicKey(), aliPayBaseConfig.getSignType());
        AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
        request.setBizModel(refundModel);
        AlipayTradeFastpayRefundQueryResponse response = alipayClient.execute(request);
        // 如果没有成功，2秒后再查询一次
        if (!response.isSuccess()) {
            Thread.sleep(2000);
            response = alipayClient.execute(request);
        }
        if (!response.isSuccess()) {
            throw new RuntimeException("统一收单交易退款查询接口,查询失败，请稍后重试！");
        }
        return response;
    }
}
