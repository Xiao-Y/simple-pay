package com.billow.wx.base.model;

import com.billow.wx.base.enums.TradeTypeEnum;
import com.billow.wx.base.utils.ApiField;
import com.sun.istack.internal.NotNull;
import lombok.Data;

/**
 * 统一下单模型
 *
 * @author liuyongtao
 * @create 2019-12-25 10:41
 */
@Data
public class UnifiedOrderModel {

    // 商品简单描述,必填
    @NotNull
    @ApiField
    private String body;

    // 商户订单号,要求32个字符内，只能是数字、大小写字母_-|* 且在同一个商户号下唯一,必填
    @NotNull
    @ApiField
    private String outTradeNo;

    // 订单总金额，单位为分,必填
    @NotNull
    @ApiField
    private String totalFee;

    // 终端IP,支持IPV4和IPV6两种格式的IP地址。用户的客户端IP,必填
    @NotNull
    @ApiField
    private String spbillCreateIp;

    // 通知地址.异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。,必填
    @NotNull
    @ApiField
    private String notifyUrl;

    // JSAPI -JSAPI支付,NATIVE -Native支付,APP -APP支付,必填
    @NotNull
    @ApiField
    private String tradeType = TradeTypeEnum.NATIVE.getCode();

    // trade_type=NATIVE时，此参数必传。此参数为二维码中包含的商品ID，商户自行定义。,必填
    @NotNull
    @ApiField
    private String productId;

    // 设备号,自定义参数
    @ApiField
    private String deviceInfo;

    // 标价币种,符合ISO 4217标准的三位字母代码，默认人民币：CNY，
    @ApiField
    private String feeType = "CNY";
}
