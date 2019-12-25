package com.billow.wx.base.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.billow.wx.base.constant.StatusCode;
import com.billow.wx.base.exception.ReturnCodeException;
import com.billow.wx.base.model.UnifiedOrderModel;
import com.billow.wx.base.service.WxPayBaseService;
import com.github.wxpay.sdk.WXPay;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liuyongtao
 * @create 2019-12-25 11:27
 */
@Slf4j
public class WxPayBaseServiceImpl implements WxPayBaseService {

    private WXPay wxPay;

    @Override
    public void setWxPay(WXPay wxPay) {
        this.wxPay = wxPay;
    }

    @Override
    public Map<String, String> unifiedOrder(UnifiedOrderModel model) throws Exception {
        Map<String, String> data = new HashMap<>();
        data.put("body", model.getBody());
        data.put("out_trade_no", model.getOutTradeNo());
        data.put("device_info", model.getDeviceInfo());
        data.put("fee_type", model.getFeeType());
        data.put("total_fee", model.getTotalFee());
        data.put("spbill_create_ip", model.getSpbillCreateIp());
        data.put("notify_url", model.getNotifyUrl());
        data.put("trade_type", model.getTradeTypeEnum().getCode());
        data.put("product_id", model.getProductId());
        log.debug("unifiedorder:====>>>> {}", JSONObject.toJSONString(data));
        Map<String, String> resp = wxPay.unifiedOrder(data);
        log.debug("unifiedorder:<<<<==== {}", JSONObject.toJSONString(resp));
        // SUCCESS/FAIL
        String returnCode = resp.get("return_code");
        if (StatusCode.FAIL.equals(returnCode)) {
            throw new ReturnCodeException(resp);
        }
        return resp;
    }

    @Override
    public Map<String, String> orderQueryByTransactionId(@NonNull String transactionId) throws Exception {
        return this.orderQuery(transactionId, null);
    }

    @Override
    public Map<String, String> orderQueryByOutTradeNo(@NonNull String outTradeNo) throws Exception {
        return this.orderQuery(null, outTradeNo);
    }

    /**
     * 查询订单信息
     *
     * @param transactionId
     * @param outTradeNo
     * @return java.util.Map<java.lang.String, java.lang.String>
     * @author LiuYongTao
     * @date 2019/12/25 13:45
     */
    private Map<String, String> orderQuery(String transactionId, String outTradeNo) throws Exception {
        Map<String, String> data = new HashMap<>();
        if (transactionId != null) {
            data.put("transaction_id", transactionId);
        }
        if (outTradeNo != null) {
            data.put("out_trade_no", outTradeNo);
        }
        Map<String, String> resp = wxPay.orderQuery(data);
        String returnCode = resp.get("return_code");
        if (StatusCode.FAIL.equals(returnCode)) {
            throw new ReturnCodeException(resp);
        }
        return resp;
    }
}
