package com.billow.wx.base.enums;

public enum TradeTypeEnum {

    JSAPI("JSAPI","JSAPI支付"),
    NATIVE("NATIVE","Native支付"),
    APP("APP","APP支付");

    private String code;
    private String codeName;

    TradeTypeEnum(String code, String codeName){
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
