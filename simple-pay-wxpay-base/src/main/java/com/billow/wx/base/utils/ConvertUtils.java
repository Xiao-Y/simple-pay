package com.billow.wx.base.utils;

import com.billow.wx.base.annotation.ApiField;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 转换工具类
 *
 * @author liuyongtao
 * @create 2019-12-25 15:05
 */
public class ConvertUtils {

    public static final char UNDERLINE = '_';

    /**
     * Bean 转 map，使用 @ApiField 注解
     *
     * @param t
     * @return java.util.Map<java.lang.String, java.lang.String>
     * @author LiuYongTao
     * @date 2019/12/25 15:21
     */
    public static <T> Map<String, String> entityToMap(T t) {

        Map<String, String> data = new HashMap<>();
        if (t == null) {
            return data;
        }
        Class<?> clazz = t.getClass();
        Field[] fields = clazz.getDeclaredFields();
        // 获取标记 @Title 的属性和 order
        for (Field field : fields) {
            field.setAccessible(true);
            ApiField apiField = field.getAnnotation(ApiField.class);
            if (apiField != null) {
                Object value = null;
                String camel = null;
                try {
                    // 驼峰转下划线
                    camel = camelToUnderline(field.getName(), 1);
                    // 属性值
                    value = field.get(t);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                // 如果指定了，应用指定的
                if (!"".equals(apiField.value())) {
                    camel = apiField.value();
                }
                if (value == null) {
                    // 当属性值为空时，是否还加入到 map 中
                    if (apiField.isAdd()) {
                        data.put(camel, "");
                    }
                } else {
                    data.put(camel, value.toString());
                }
            }
        }
        return data;
    }

    /**
     * 驼峰转下划线
     *
     * @param param
     * @param charType 1-统一都转小写，2-统一都转大写
     * @return java.lang.String
     * @author LiuYongTao
     * @date 2019/12/25 15:30
     */
    public static String camelToUnderline(String param, Integer charType) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append(UNDERLINE);
            }
            if (charType == 2) {
                sb.append(Character.toUpperCase(c));  //统一都转大写
            } else {
                sb.append(Character.toLowerCase(c));  //统一都转小写
            }


        }
        return sb.toString();
    }
}
