package com.billow.wx.base.annotation;

/**
 * 用于 bean 转 map
 *
 * @author LiuYongTao
 * @date 2019/12/26 15:27
 */
public @interface ApiField {

    // map 的key
    String value() default "";

    // 当属性值为空时，是否还加入到 map 中
    boolean isAdd() default false;
}
