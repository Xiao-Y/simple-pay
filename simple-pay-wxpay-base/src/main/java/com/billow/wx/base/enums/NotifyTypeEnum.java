package com.billow.wx.base.enums;

public enum NotifyTypeEnum {

    PAY_RESULT("payResult", "支付通知结果"),
    REFUND_RESULT("refundResult", "退款通知结果");

    private String code;
    private String codeName;

    NotifyTypeEnum(String code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    public String getCode() {
        return code;
    }

    public String getCodeName() {
        return codeName;
    }
}
