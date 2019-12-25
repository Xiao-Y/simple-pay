package com.billow.wx.base.service;

import com.billow.wx.base.model.RefundModel;
import com.billow.wx.base.model.UnifiedOrderModel;
import com.github.wxpay.sdk.WXPay;

import java.util.Map;

public interface WxPayBaseService {

    void setWxPay(WXPay wxPay);

    /**
     * 统一下单接口
     * https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1
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

    /**
     * 通过微信订单号，查询退款
     * https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_5
     *
     * @param transactionId 通过微信订单号
     * @return java.util.Map<java.lang.String, java.lang.String>
     * @author LiuYongTao
     * @date 2019/12/25 14:36
     */
    Map<String, String> refundQueryByTransactionId(String transactionId) throws Exception;

    /**
     * 通过商户订单号，查询退款。商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一
     * https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_5
     *
     * @param outTradeNo 商户订单号
     * @return java.util.Map<java.lang.String, java.lang.String>
     * @author LiuYongTao
     * @date 2019/12/25 14:36
     */
    Map<String, String> refundQueryByOutTradeNo(String outTradeNo) throws Exception;

    /**
     * 通过商户退款单号，查询退款。商户系统内部的退款单号，商户系统内部唯一，只能是数字、大小写字母_-|*@ ，同一退款单号多次请求只退一笔
     * https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_5
     *
     * @param outRefundNo 商户退款单号
     * @return java.util.Map<java.lang.String, java.lang.String>
     * @author LiuYongTao
     * @date 2019/12/25 14:36
     */
    Map<String, String> refundQueryByOutRefundNo(String outRefundNo) throws Exception;

    /**
     * 微信退款单号，查询退款。微信生成的退款单号，在申请退款接口有返回
     * https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_5
     *
     * @param refundId 退款单号
     * @return java.util.Map<java.lang.String, java.lang.String>
     * @author LiuYongTao
     * @date 2019/12/25 14:36
     */
    Map<String, String> refundQueryByRefundId(String refundId) throws Exception;

    /**
     * 通过微信订单号，退款
     *
     * @param model 退款数据
     * @return java.util.Map<java.lang.String, java.lang.String>
     * @author LiuYongTao
     * @date 2019/12/25 14:58
     */
    Map<String, String> refundByTransactionId(RefundModel model) throws Exception;

    /**
     * 通过商户订单号，退款
     *
     * @param model 退款数据
     * @return java.util.Map<java.lang.String, java.lang.String>
     * @author LiuYongTao
     * @date 2019/12/25 14:59
     */
    Map<String, String> refundByOutTradeNo(RefundModel model) throws Exception;
}
