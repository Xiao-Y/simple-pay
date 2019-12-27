package com.billow.alipay.scan.model;

import lombok.Data;
import lombok.NonNull;

/**
 * 订单信息
 *
 * @author liuyongtao
 * @create 2019-12-22 15:24
 */
@Data
public class OrderInfo {
    @NonNull
    private String appId; //应用ID
    @NonNull
    private String outTradeNo; // 商户订单号，商户网站订单系统中唯一订单号，必填
    @NonNull
    private String totalAmount; //付款金额，必填
    @NonNull
    private String tradeNo; //支付交易号，唯一
}
