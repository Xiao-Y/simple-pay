package com.billow.alipay.scan.starter.config;

import com.billow.alipay.scan.starter.properties.AliPayScanProperties;
import com.billow.alipay.scan.starter.service.AliPayScanService;
import com.billow.alipay.scan.starter.service.impl.AliPayScanServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liuyongtao
 * @create 2019-12-22 15:42
 */
@Configuration
@EnableConfigurationProperties(AliPayScanProperties.class)
@ConditionalOnProperty(prefix = "alipay.scan", name = "appId")
public class AliPayScanAutoConfig {

    @Bean
    public AliPayScanService aliPayScanService(AliPayScanProperties aliPayScanProperties) {
        return new AliPayScanServiceImpl(aliPayScanProperties);
    }
}
