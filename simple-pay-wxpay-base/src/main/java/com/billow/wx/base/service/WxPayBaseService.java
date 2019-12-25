package com.billow.wx.base.service;

import com.billow.wx.base.model.UnifiedOrderModel;
import com.github.wxpay.sdk.WXPay;

import java.util.Map;

public interface WxPayBaseService {

    void setWxPay(WXPay wxPay);

    /**
     * 统一下单接口,https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1
     *
     * @param model
     * @return java.util.Map<java.lang.String, java.lang.String>
     * @author LiuYongTao
     * @date 2019/12/25 10:39
     */
    Map<String, String> unifiedOrder(UnifiedOrderModel model) throws Exception;

    /**
     * 查询订单信息，商户可以通过查询订单接口主动查询订单状态，完成下一步的业务逻辑
     * https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_2
     *
     * @param transactionId 微信的订单号，建议优先使用
     * @return java.util.Map<java.lang.String, java.lang.String>
     * @author LiuYongTao
     * @date 2019/12/25 13:31
     */
    Map<String, String> orderQueryByTransactionId(String transactionId) throws Exception;

    /**
     * 查询订单信息，商户可以通过查询订单接口主动查询订单状态，完成下一步的业务逻辑
     * https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_2
     *
     * @param outTradeNo 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一
     * @return java.util.Map<java.lang.String, java.lang.String>
     * @author LiuYongTao
     * @date 2019/12/25 13:31
     */
    Map<String, String> orderQueryByOutTradeNo(String outTradeNo) throws Exception;
}
