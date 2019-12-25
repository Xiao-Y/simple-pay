package com.billow.alipay.base.config;

import lombok.Data;

/**
 * 公用配置文件
 *
 * @author liuyongtao
 * @create 2019-12-23 22:07
 */
@Data
public class AliPayBaseConfig {
    private String appId;
    // 商户私钥
    private String privateKey;
    // 支付宝公钥
    private String aliPayPublicKey;
    // 支付宝的网关
    private String gatewayUrl;
    // 编码格式,默认：UTF-8
    private String charset = "UTF-8";
    // 商户生成签名字符串所使用的签名算法类型,默认：RSA2
    private String signType = "RSA2";
    // 数据格式
    private String format = "json";
}
