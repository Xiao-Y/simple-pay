package com.billow.wx.base.model;

import com.billow.wx.base.annotation.ApiField;
import lombok.Data;

/**
 * 公用查询条件
 *
 * @author liuyongtao
 * @create 2019-12-25 15:38
 */
@Data
public class QueryModel {

    // 通过微信订单号
    @ApiField
    private String transactionId;

    // 商户订单号
    @ApiField
    private String outTradeNo;

    // 商户退款单号
    @ApiField
    private String outRefundNo;

    // 退款单号
    @ApiField
    private String refundId;
}
