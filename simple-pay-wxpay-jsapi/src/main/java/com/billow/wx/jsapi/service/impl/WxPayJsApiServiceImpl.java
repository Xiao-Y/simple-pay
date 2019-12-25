package com.billow.wx.jsapi.service.impl;

import com.billow.wx.base.constant.StatusCode;
import com.billow.wx.base.model.UnifiedOrderModel;
import com.billow.wx.base.service.impl.WxPayBaseServiceImpl;
import com.billow.wx.jsapi.config.WXPayJsApiConfig;
import com.billow.wx.jsapi.service.WxPayJsApiService;
import com.github.wxpay.sdk.WXPay;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author liuyongtao
 * @create 2019-12-25 10:55
 */
@Slf4j
public class WxPayJsApiServiceImpl extends WxPayBaseServiceImpl implements WxPayJsApiService {

    private WXPay wxpay;

    WxPayJsApiServiceImpl(WXPayJsApiConfig config) {
        this.wxpay = new WXPay(config);
        super.setWxPay(wxpay);
    }

    @Override
    public String unifiedOrderToQrCode(UnifiedOrderModel model) throws Exception {
        Map<String, String> map = this.unifiedOrder(model);
        if (!StatusCode.SUCCESS.equals(map.get("result_code"))) {
            throw new RuntimeException("获取支付连接失败！");
        }
        return map.get("code_url");
    }
}
