package com.billow.alipay.scan.starter.properties;

import com.billow.alipay.base.config.AliPayBaseConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author liuyongtao
 * @create 2019-12-27 10:20
 */
@Data
@ConfigurationProperties(prefix = "alipay.scan")
public class AliPayScanProperties extends AliPayBaseConfig {
    // 异步支付通知回调
    private String notifyUrl;
    // 同步支付通知回调
    private String returnUrl;

}