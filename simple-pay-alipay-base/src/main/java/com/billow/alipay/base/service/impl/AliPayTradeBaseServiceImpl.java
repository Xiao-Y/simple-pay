package com.billow.alipay.base.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayDataDataserviceBillDownloadurlQueryModel;
import com.alipay.api.domain.AlipayTradeCancelModel;
import com.alipay.api.domain.AlipayTradeCloseModel;
import com.alipay.api.domain.AlipayTradeCreateModel;
import com.alipay.api.domain.AlipayTradeFastpayRefundQueryModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.request.AlipayDataDataserviceBillDownloadurlQueryRequest;
import com.alipay.api.request.AlipayTradeCancelRequest;
import com.alipay.api.request.AlipayTradeCloseRequest;
import com.alipay.api.request.AlipayTradeCreateRequest;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayDataDataserviceBillDownloadurlQueryResponse;
import com.alipay.api.response.AlipayTradeCancelResponse;
import com.alipay.api.response.AlipayTradeCloseResponse;
import com.alipay.api.response.AlipayTradeCreateResponse;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.billow.alipay.base.service.AliPayTradeBaseService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * 交易查询接口
 *
 * @author liuyongtao
 * @create 2019-12-23 22:01
 */
@Slf4j
public class AliPayTradeBaseServiceImpl implements AliPayTradeBaseService {

    private AlipayClient alipayClient;

    @Override
    public void setAlipayClient(@NonNull AlipayClient alipayClient) {
        this.alipayClient = alipayClient;
    }

    @Override
    public AlipayTradeQueryResponse tradeQuery(AlipayTradeQueryModel model) throws Exception {
        log.debug("请求入参：{}", JSONObject.toJSONString(model));
        if (model.getOutTradeNo() == null && model.getTradeNo() == null) {
            throw new RuntimeException("outTradeNo,tradeNo 订单支付时传入的商户订单号,和支付宝交易号不能同时为空");
        }
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.setBizModel(model);
        AlipayTradeQueryResponse execute = alipayClient.execute(request);
        // 如果没有成功，2秒后再查询一次
        if (!execute.isSuccess()) {
            Thread.sleep(2000);
            execute = alipayClient.execute(request);
        }
        log.debug("请求出参：{}", JSONObject.toJSONString(execute));
        if (!execute.isSuccess()) {
            throw new RuntimeException(execute.getSubMsg());
        }
        return execute;
    }

    @Override
    public AlipayTradeFastpayRefundQueryResponse refundQuery(AlipayTradeFastpayRefundQueryModel model) throws Exception {
        log.debug("请求入参：{}", JSONObject.toJSONString(model));
        if (model.getOutTradeNo() == null && model.getTradeNo() == null) {
            throw new RuntimeException("outTradeNo,tradeNo 订单支付时传入的商户订单号,和支付宝交易号不能同时为空");
        }

        if (model.getOutRequestNo() == null) {
            throw new RuntimeException("outRequestNo 请求退款接口时，传入的退款请求号，如果在退款请求时未传入，则该值为创建交易时的外部交易号");
        }
        AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
        request.setBizModel(model);
        AlipayTradeFastpayRefundQueryResponse execute = alipayClient.execute(request);
        // 如果没有成功，2秒后再查询一次
        if (!execute.isSuccess()) {
            Thread.sleep(2000);
            execute = alipayClient.execute(request);
        }
        log.debug("请求出参：{}", JSONObject.toJSONString(execute));
        if (!execute.isSuccess()) {
            throw new RuntimeException(execute.getSubMsg());
        }
        return execute;
    }

    @Override
    public AlipayTradeRefundResponse tradeRefund(AlipayTradeRefundModel model) throws Exception {
        log.debug("请求入参：{}", JSONObject.toJSONString(model));
        if (model.getOutTradeNo() == null && model.getTradeNo() == null) {
            throw new RuntimeException("outTradeNo,tradeNo 订单支付时传入的商户订单号,和支付宝交易号不能同时为空");
        }

        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.setBizModel(model);
        AlipayTradeRefundResponse execute = alipayClient.execute(request);
        log.debug("请求出参：{}", JSONObject.toJSONString(execute));
        if (!execute.isSuccess()) {
            throw new RuntimeException(execute.getSubMsg());
        }
        return execute;
    }

    @Override
    public AlipayTradeCloseResponse tradeClose(AlipayTradeCloseModel model) throws Exception {
        log.debug("请求入参：{}", JSONObject.toJSONString(model));
        AlipayTradeCloseRequest request = new AlipayTradeCloseRequest();
        request.setBizModel(model);
        AlipayTradeCloseResponse execute = alipayClient.execute(request);
        log.debug("请求出参：{}", JSONObject.toJSONString(execute));
        if (!execute.isSuccess()) {
            throw new RuntimeException(execute.getSubMsg());
        }
        return execute;
    }

    @Override
    public AlipayDataDataserviceBillDownloadurlQueryResponse downloadUrlQuery(AlipayDataDataserviceBillDownloadurlQueryModel model) throws Exception {
        log.debug("请求入参：{}", JSONObject.toJSONString(model));
        AlipayDataDataserviceBillDownloadurlQueryRequest request = new AlipayDataDataserviceBillDownloadurlQueryRequest();
        request.setBizModel(model);
        AlipayDataDataserviceBillDownloadurlQueryResponse execute = alipayClient.execute(request);
        // 如果没有成功，2秒后再查询一次
        if (!execute.isSuccess()) {
            Thread.sleep(2000);
            execute = alipayClient.execute(request);
        }
        log.debug("请求出参：{}", JSONObject.toJSONString(execute));
        if (!execute.isSuccess()) {
            throw new RuntimeException(execute.getSubMsg());
        }
        return execute;
    }

    @Override
    public AlipayTradeCancelResponse tradeCancel(AlipayTradeCancelModel model) throws Exception {
        log.debug("请求入参：{}", JSONObject.toJSONString(model));
        AlipayTradeCancelRequest request = new AlipayTradeCancelRequest();
        request.setBizModel(model);
        AlipayTradeCancelResponse execute = alipayClient.execute(request);
        log.debug("请求出参参：{}", JSONObject.toJSONString(execute));
        if (!execute.isSuccess()) {
            throw new RuntimeException(execute.getSubMsg());
        }
        return execute;
    }

    @Override
    public AlipayTradeCreateResponse tradeCreate(AlipayTradeCreateModel model) throws Exception {
        log.debug("请求入参：{}", JSONObject.toJSONString(model));
        AlipayTradeCreateRequest request = new AlipayTradeCreateRequest();
        request.setBizModel(model);
        AlipayTradeCreateResponse execute = alipayClient.execute(request);
        log.debug("请求出参参：{}", JSONObject.toJSONString(execute));
        if (!execute.isSuccess()) {
            throw new RuntimeException(execute.getSubMsg());
        }
        return execute;
    }
}
