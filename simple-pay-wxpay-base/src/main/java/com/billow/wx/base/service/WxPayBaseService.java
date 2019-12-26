package com.billow.wx.base.service;

import com.billow.wx.base.model.RefundModel;
import com.billow.wx.base.model.UnifiedOrderModel;
import com.github.wxpay.sdk.WXPay;

import java.util.Map;

public interface WxPayBaseService {

    /**
     * 设置基本参数
     *
     * @param wxPay
     * @return void
     * @author LiuYongTao
     * @date 2019/12/26 8:43
     */
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

    /**
     * 下载对账单
     * https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_6 <br/>
     * <p>
     * 注意：<br/>
     * <p>
     * 1、微信侧未成功下单的交易不会出现在对账单中。支付成功后撤销的交易会出现在对账单中，跟原支付单订单号一致； <br/>
     * <p>
     * 2、微信在次日9点启动生成前一天的对账单，建议商户10点后再获取； <br/>
     * <p>
     * 3、对账单中涉及金额的字段单位为“元”。 <br/>
     * <p>
     * 4、对账单接口只能下载三个月以内的账单。<br/>
     * <p>
     * 5、对账单是以商户号纬度来生成的，如一个商户号与多个appid有绑定关系，则使用其中任何一个appid都可以请求下载对账单。对账单中的appid取自交易时候提交的appid，与请求下载对账单时使用的appid无关<br/>
     *
     * @param billType ALL（默认值），返回当日所有订单信息（不含充值退款订单）
     *                 <p>
     *                 SUCCESS，返回当日成功支付的订单（不含充值退款订单）
     *                 <p>
     *                 REFUND，返回当日退款订单（不含充值退款订单）
     *                 <p>
     *                 RECHARGE_REFUND，返回当日充值退款订单
     * @param billDate 下载对账单的日期，格式：20140603
     * @return java.util.Map<java.lang.String, java.lang.String>
     * @author LiuYongTao
     * @date 2019/12/26 8:56
     */
    Map<String, Object> downloadBill(String billType, String billDate) throws Exception;
}
