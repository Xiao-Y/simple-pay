package com.billow.wx.base.utils;

public @interface ApiField {

    // map 的key
    String value() default "";

    // 当属性值为空时，是否还加入到 map 中
    boolean isAdd() default false;
}
