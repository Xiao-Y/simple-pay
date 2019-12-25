package com.billow.alipay.wap.service;

import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.billow.alipay.base.service.AliPayTradeBaseService;

import javax.servlet.http.HttpServletRequest;

public interface AliPayWapService extends AliPayTradeBaseService {

    /**
     * 手机网站支付接口2.0 https://docs.open.alipay.com/api_1/alipay.trade.wap.pay
     *
     * @param model
     * @return com.alipay.api.response.AlipayTradeWapPayResponse
     * @author LiuYongTao
     * @date 2019/12/24 11:36
     */
    AlipayTradeWapPayResponse wapPay(AlipayTradeWapPayModel model) throws Exception;

    /**
     * 异常支付通知
     *
     * @param request
     * @param updateOrderStausService
     * @return java.lang.String
     * @author LiuYongTao
     * @date 2019/12/24 13:40
     */
    String notifyUrl(HttpServletRequest request, AliPayUpdateOrderStausService updateOrderStausService);
}
