package com.billow.alipay.pc.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author liuyongtao
 * @create 2019-12-22 15:57
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "alipay.pc")
public class AliPayPcProperties {

    private String appId;
    // 商户私钥
    private String privateKey;
    // 支付宝公钥
    private String aliPayPublicKey;
    // 异步支付通知回调
    private String notifyUrl;
    // 同步支付通知回调
    private String returnUrl;
    // 支付宝的网关
    private String gatewayUrl;
}
