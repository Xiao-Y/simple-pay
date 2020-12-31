package com.billow.alipay.scan.api;

import com.alipay.api.domain.AlipayDataDataserviceBillDownloadurlQueryModel;
import com.alipay.api.domain.AlipayTradeCancelModel;
import com.alipay.api.domain.AlipayTradeFastpayRefundQueryModel;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.response.AlipayDataDataserviceBillDownloadurlQueryResponse;
import com.alipay.api.response.AlipayTradeCloseResponse;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.billow.alipay.scan.service.OrderService;
import com.billow.alipay.scan.starter.model.OrderInfo;
import com.billow.alipay.scan.starter.service.AliPayScanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Slf4j
@RestController
public class AlipayScanApi {

    @Autowired
    private AliPayScanService aliPayScanService;
    @Autowired
    private OrderService orderService;

    @GetMapping(value = "/pay")
    public void pay() throws Exception {
        String outTradeNo = UUID.randomUUID().toString();
        System.out.println("outTradeNo:" + outTradeNo);
        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
        model.setOutTradeNo(outTradeNo);
        model.setTotalAmount("0.01");
        model.setSubject("测试商品");
        model.setBody("这是一个测试商品");

        String qrCode = aliPayScanService.tradePrecreate(model);
        log.info("生成的连接=====>>>> {}", qrCode);
    }

    @PostMapping(value = "/notify")
    public String notify(HttpServletRequest request) throws Exception {
        log.info("notify----start");
        String payStatus = AliPayScanService.STATUS_SUCCESS;
        try {
            OrderInfo orderInfo = orderService.updateOrder(request);
            if (!orderInfo.isPaySataus()) {
                payStatus = AliPayScanService.STATUS_FAIL;
            }
        } catch (Exception e) {
            payStatus = AliPayScanService.STATUS_FAIL;
        }
        return payStatus;
    }

    // 交易查询
    @GetMapping(value = "/tradeQuery")
    public AlipayTradeQueryResponse tradeQuery() throws Exception {
        AlipayTradeQueryModel queryModel = new AlipayTradeQueryModel();
        queryModel.setOutTradeNo("32951049-343f-4fda-a752-a0a1d6b0dec3");
        // 966861ca-fa93-4174-b5b9-864beaf6e75f

        // 32951049-343f-4fda-a752-a0a1d6b0dec3
        // 2019122822001439661000054512
        return aliPayScanService.tradeQuery(queryModel);
    }

    // 交易退款
    @GetMapping(value = "/tradeRefund")
    public AlipayTradeRefundResponse tradeRefund() throws Exception {
        AlipayTradeRefundModel refundModel = new AlipayTradeRefundModel();
        refundModel.setTradeNo("2019122822001439661000054512");
        refundModel.setRefundAmount("0.01");
        return aliPayScanService.tradeRefund(refundModel);
    }

    // 交易退款查询
    @GetMapping(value = "/refundQuery")
    public AlipayTradeFastpayRefundQueryResponse refundQuery() throws Exception {
        AlipayTradeFastpayRefundQueryModel queryModel = new AlipayTradeFastpayRefundQueryModel();
        queryModel.setTradeNo("2019122822001439661000054512");
        queryModel.setOutRequestNo("32951049-343f-4fda-a752-a0a1d6b0dec3");
        return aliPayScanService.refundQuery(queryModel);
    }

    // 统一收单交易关闭接口
    @GetMapping(value = "/tradeClose")
    public AlipayTradeCloseResponse tradeClose() throws Exception {
//        AlipayTradeCloseModel closeModel = new AlipayTradeCloseModel();
//        closeModel.setTradeNo("2019122822001439661000054512");

        AlipayTradeCancelModel closeModel = new AlipayTradeCancelModel();
        closeModel.setOutTradeNo("966861ca-fa93-4174-b5b9-864beaf6e75f");
        aliPayScanService.tradeCancel(closeModel);
        return null;
    }

    // 查询对账单下载地址
    @GetMapping(value = "/downloadUrlQuery")
    public AlipayDataDataserviceBillDownloadurlQueryResponse downloadUrlQuery() throws Exception {
        AlipayDataDataserviceBillDownloadurlQueryModel queryModel = new AlipayDataDataserviceBillDownloadurlQueryModel();
        queryModel.setBillType("trade");
        queryModel.setBillDate("2019-12-28");
        return aliPayScanService.downloadUrlQuery(queryModel);
    }
}
