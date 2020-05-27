package com.billow.alipay.base.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * 公用配置文件
 *
 * @author liuyongtao
 * @create 2019-12-23 22:07
 */
@Data
@NoArgsConstructor
public class AliPayBaseConfig {
    @NonNull
    private String appId;
    // 商户私钥
    @NonNull
    private String privateKey;
    // 支付宝公钥
    @NonNull
    private String aliPayPublicKey;
    // 支付宝的网关
    @NonNull
    private String gatewayUrl;
    // 编码格式,默认：UTF-8
    private String charset = "UTF-8";
    // 商户生成签名字符串所使用的签名算法类型,默认：RSA2
    private String signType = "RSA2";
    // 数据格式
    private String format = "json";
}
