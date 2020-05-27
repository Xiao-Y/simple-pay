package com.billow.alipay.pc.controller;

import com.alipay.api.domain.AlipayDataDataserviceBillDownloadurlQueryModel;
import com.alipay.api.domain.AlipayTradeCloseModel;
import com.alipay.api.domain.AlipayTradeFastpayRefundQueryModel;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.response.AlipayDataDataserviceBillDownloadurlQueryResponse;
import com.alipay.api.response.AlipayTradeCloseResponse;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.billow.alipay.pc.model.OrderInfo;
import com.billow.alipay.pc.service.AliPayPcService;
import com.billow.alipay.pc.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AlipayPcController {

    @Autowired
    private AliPayPcService aliPayPcService;
    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/pay")
    public void pay(HttpServletResponse response) throws Exception {
        String outTradeNo = UUID.randomUUID().toString();
        System.out.println("outTradeNo:" + outTradeNo);
        AlipayTradePagePayModel model = new AlipayTradePagePayModel();
        model.setOutTradeNo(outTradeNo);
        model.setTotalAmount("0.01");
        model.setSubject("测试商品");
        model.setBody("这是一个测试商品");

        aliPayPcService.tradePage(response, model);
    }

    @RequestMapping(value = "/return")
    @ResponseBody
    public String returnUrl(HttpServletRequest request) throws Exception {
        String payStatus = AliPayPcService.STATUS_SUCCESS;
        try {
            OrderInfo orderInfo = orderService.updateOrder(request);
            if (!orderInfo.isPaySataus()) {
                payStatus = AliPayPcService.STATUS_FAIL;
            }
        } catch (Exception e) {
            payStatus = AliPayPcService.STATUS_FAIL;
        }
        return payStatus;
    }

    @RequestMapping(value = "/notify")
    @ResponseBody
    public String notify(HttpServletRequest request) throws Exception {
        String payStatus = AliPayPcService.STATUS_SUCCESS;
        try {
            OrderInfo orderInfo = orderService.updateOrder(request);
            if (!orderInfo.isPaySataus()) {
                payStatus = AliPayPcService.STATUS_FAIL;
            }
        } catch (Exception e) {
            payStatus = AliPayPcService.STATUS_FAIL;
        }
        return payStatus;
    }

    // 交易查询
    @RequestMapping(value = "/tradeQuery")
    @ResponseBody
    public AlipayTradeQueryResponse tradeQuery() throws Exception {
        AlipayTradeQueryModel queryModel = new AlipayTradeQueryModel();
        queryModel.setOutTradeNo("8fc4b16e-3dee-4577-80f2-fd70042a7f7a");
        return aliPayPcService.tradeQuery(queryModel);
    }

    // 交易退款
    @RequestMapping(value = "/tradeRefund")
    @ResponseBody
    public AlipayTradeRefundResponse tradeRefund() throws Exception {
        AlipayTradeRefundModel refundModel = new AlipayTradeRefundModel();
        refundModel.setTradeNo("2019122422001439661000047096");
        refundModel.setRefundAmount("0.01");
        return aliPayPcService.tradeRefund(refundModel);
    }

    // 交易退款查询
    @RequestMapping(value = "/refundQuery")
    @ResponseBody
    public AlipayTradeFastpayRefundQueryResponse refundQuery() throws Exception {
        AlipayTradeFastpayRefundQueryModel queryModel = new AlipayTradeFastpayRefundQueryModel();
        queryModel.setTradeNo("2019122422001439661000047096");
        queryModel.setOutRequestNo("8fc4b16e-3dee-4577-80f2-fd70042a7f7a");
        return aliPayPcService.refundQuery(queryModel);
    }

    // 统一收单交易关闭接口
    @RequestMapping(value = "/tradeClose")
    @ResponseBody
    public AlipayTradeCloseResponse tradeClose() throws Exception {
        AlipayTradeCloseModel closeModel = new AlipayTradeCloseModel();
        closeModel.setTradeNo("2019122422001439661000047096");
        return aliPayPcService.tradeClose(closeModel);
    }

    // 查询对账单下载地址
    @RequestMapping(value = "/downloadUrlQuery")
    @ResponseBody
    public AlipayDataDataserviceBillDownloadurlQueryResponse downloadUrlQuery() throws Exception {
        AlipayDataDataserviceBillDownloadurlQueryModel queryModel = new AlipayDataDataserviceBillDownloadurlQueryModel();
        queryModel.setBillType("trade");
        queryModel.setBillDate("2019-12");
        return aliPayPcService.downloadUrlQuery(queryModel);
    }
}
