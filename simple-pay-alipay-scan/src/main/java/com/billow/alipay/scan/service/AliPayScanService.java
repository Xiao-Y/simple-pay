package com.billow.alipay.scan.service;

import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.billow.alipay.base.service.AliPayTradeBaseService;

import javax.servlet.http.HttpServletRequest;

public interface AliPayScanService extends AliPayTradeBaseService {


    /**
     * 统一收单线下交易预创建
     * https://docs.open.alipay.com/api_1/alipay.trade.precreate
     * out_trade_no,total_amount,subject 必选
     *
     * @param model
     * @return java.lang.String
     * @author LiuYongTao
     * @date 2019/12/27 10:00
     */
    String tradePrecreate(AlipayTradePrecreateModel model) throws Exception;


    /**
     * 异常支付通知
     * https://docs.open.alipay.com/194/103296/
     *
     * @param updateOrderStausService
     * @param request
     * @return java.lang.String
     * @author billow
     * @date 2019/12/22 17:14
     */
    String notifyUrl(HttpServletRequest request, AliPayUpdateOrderStausService updateOrderStausService);
}
