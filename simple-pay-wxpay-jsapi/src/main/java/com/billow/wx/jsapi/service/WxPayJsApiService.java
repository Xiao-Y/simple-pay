package com.billow.wx.jsapi.service;

import com.billow.wx.base.model.UnifiedOrderModel;
import com.billow.wx.base.service.WxPayBaseService;

public interface WxPayJsApiService extends WxPayBaseService {

    /**
     * 统一下单接口,返回支付连接，用于生成支付二维码
     *
     * @param model
     * @return java.lang.String
     * @author LiuYongTao
     * @date 2019/12/25 11:20
     */
    String unifiedOrderToQrCode(UnifiedOrderModel model) throws Exception;

}
