package com.billow.base.service.impl;

import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayDataDataserviceBillDownloadurlQueryModel;
import com.alipay.api.domain.AlipayTradeCloseModel;
import com.alipay.api.domain.AlipayTradeFastpayRefundQueryModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.request.AlipayDataDataserviceBillDownloadurlQueryRequest;
import com.alipay.api.request.AlipayTradeCloseRequest;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayDataDataserviceBillDownloadurlQueryResponse;
import com.alipay.api.response.AlipayTradeCloseResponse;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.billow.base.service.AliPayTradeBaseService;

/**
 * 交易查询接口
 *
 * @author liuyongtao
 * @create 2019-12-23 22:01
 */
public class AliPayTradeBaseServiceImpl implements AliPayTradeBaseService {

    private AlipayClient alipayClient;

    @Override
    public void setAlipayClient(AlipayClient alipayClient) {
        this.alipayClient = alipayClient;
    }

    @Override
    public AlipayTradeQueryResponse tradeQuery(AlipayTradeQueryModel queryModel) throws Exception {
        if (queryModel.getOutTradeNo() == null && queryModel.getTradeNo() == null) {
            throw new RuntimeException("outTradeNo,tradeNo 订单支付时传入的商户订单号,和支付宝交易号不能同时为空");
        }
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

    @Override
    public AlipayTradeFastpayRefundQueryResponse refundQuery(AlipayTradeFastpayRefundQueryModel queryModel) throws Exception {
        if (queryModel.getOutTradeNo() == null && queryModel.getTradeNo() == null) {
            throw new RuntimeException("outTradeNo,tradeNo 订单支付时传入的商户订单号,和支付宝交易号不能同时为空");
        }

        if (queryModel.getOutRequestNo() == null) {
            throw new RuntimeException("outRequestNo 请求退款接口时，传入的退款请求号，如果在退款请求时未传入，则该值为创建交易时的外部交易号");
        }
        AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
        request.setBizModel(queryModel);
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

    @Override
    public AlipayTradeRefundResponse tradeRefund(AlipayTradeRefundModel refundModel) throws Exception {
        if (refundModel.getOutTradeNo() == null && refundModel.getTradeNo() == null) {
            throw new RuntimeException("outTradeNo,tradeNo 订单支付时传入的商户订单号,和支付宝交易号不能同时为空");
        }

        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.setBizModel(refundModel);
        AlipayTradeRefundResponse response = alipayClient.execute(request);
        if (!response.isSuccess()) {
            throw new RuntimeException("统一收单交易退款接口,退款失败，请稍后重试！");
        }
        return response;
    }

    @Override
    public AlipayTradeCloseResponse tradeClose(AlipayTradeCloseModel closeModel) throws Exception {
        AlipayTradeCloseRequest request = new AlipayTradeCloseRequest();
        request.setBizModel(closeModel);
        AlipayTradeCloseResponse response = alipayClient.execute(request);
        if (!response.isSuccess()) {
            throw new RuntimeException("统一收单交易关闭接口,关闭失败，请稍后重试！");
        }
        return response;
    }

    @Override
    public AlipayDataDataserviceBillDownloadurlQueryResponse downloadUrlQuery(AlipayDataDataserviceBillDownloadurlQueryModel queryModel) throws Exception {
        AlipayDataDataserviceBillDownloadurlQueryRequest request = new AlipayDataDataserviceBillDownloadurlQueryRequest();
        request.setBizModel(queryModel);
        AlipayDataDataserviceBillDownloadurlQueryResponse response = alipayClient.execute(request);
        // 如果没有成功，2秒后再查询一次
        if (!response.isSuccess()) {
            Thread.sleep(2000);
            response = alipayClient.execute(request);
        }
        if (!response.isSuccess()) {
            throw new RuntimeException("查询对账单下载地址,查询失败，请稍后重试！");
        }
        return response;
    }
}
