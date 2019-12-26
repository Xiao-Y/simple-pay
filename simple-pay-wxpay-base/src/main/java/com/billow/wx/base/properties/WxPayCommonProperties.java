package com.billow.wx.base.properties;

import lombok.Data;

/**
 * 微信基数配置
 *
 * @author liuyongtao
 * @create 2019-12-26 9:14
 */
@Data
public class WxPayCommonProperties {

    // 证书路径
    private String certPath;
    // app id
    private String appID;
    // 随机数
    private String mchID;
    // 秘钥
    private String key;
    // 连接超时
    private int httpConnectTimeoutMs;
    // 读取超时
    private int httpReadTimeoutMs;
}
