package com.billow.alipay.scan.model;

import lombok.Data;

/**
 * 订单信息
 *
 * @author liuyongtao
 * @create 2019-12-22 15:24
 */
@Data
public class OrderInfo {

    /**
     * 应用ID
     */
    private String appId;

    /**
     * 商户订单号，商户网站订单系统中唯一订单号，必填
     */
    private String outTradeNo;

    /**
     * 付款金额，必填
     */
    private String totalAmount;

    /**
     * 支付交易号，唯一
     */
    private String tradeNo;

    /**
     * 支付状态，false-失败，true-成功
     */
    private boolean paySataus;
}
