package com.billow.wx.base.model;

import com.billow.wx.base.annotation.ApiField;
import lombok.Data;
import lombok.NonNull;

/**
 * 退款模型
 *
 * @author liuyongtao
 * @create 2019-12-25 14:51
 */
@Data
public class RefundModel {

    // 通过微信订单号
    @ApiField
    private String transactionId;

    // 商户订单号
    @ApiField
    private String outTradeNo;

    // 商户退款单号
    @NonNull
    @ApiField
    private String outRefundNo;

    // 订单金额
    @NonNull
    @ApiField
    private String totalFee;

    // 退款金额
    @NonNull
    @ApiField
    private String refundFee;

    // 退款货币种类
    @ApiField
    private String refundFeeType = "CNY";

    // 退款原因
    @ApiField
    private String refundDesc;

    // 退款资金来源.仅针对老资金流商户使用 REFUND_SOURCE_UNSETTLED_FUNDS---未结算资金退款（默认使用未结算资金退款） REFUND_SOURCE_RECHARGE_FUNDS---可用余额退款
    @ApiField
    private String refundAccount;

    // 退款结果通知url
    @ApiField
    private String notifyUrl;
}
