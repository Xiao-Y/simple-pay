package com.billow.wx.jsapi.service;

import com.billow.wx.base.model.UnifiedOrderModel;
import com.billow.wx.base.service.WxPayBaseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface WxPayJsApiService extends WxPayBaseService {

    /**
     * 统一下单接口,返回支付连接，用于生成支付二维码连接
     *
     * @param model
     * @return java.lang.String
     * @author LiuYongTao
     * @date 2019/12/25 11:20
     */
    String unifiedOrderToQrCode(UnifiedOrderModel model) throws Exception;

    /**
     * 支付结果通知
     *
     * @param request                 支付结果通知的xml格式数据
     * @param updateOrderStausService 更新订单状态
     * @author LiuYongTao
     * @date 2019/12/25 16:14
     */
    void payResultNotify(HttpServletRequest request, HttpServletResponse response, WxPayUpdateOrderStausService updateOrderStausService);

    /**
     * 退款结果通知
     *
     * @param request
     * @param response
     * @param updateOrderStausService
     * @return void
     * @author LiuYongTao
     * @date 2019/12/25 16:50
     */
    void refundResultNotify(HttpServletRequest request, HttpServletResponse response, WxPayUpdateOrderStausService updateOrderStausService);
}
