package com.billow.alipay.scan.config;


import com.billow.alipay.scan.properties.AliPayScanProperties;
import com.billow.alipay.scan.service.AliPayScanService;
import com.billow.alipay.scan.service.impl.AliPayScanServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liuyongtao
 * @create 2019-12-22 15:42
 */
@Configuration
public class BeanConfig {

    @Bean
    public AliPayScanService aliPayScanService(AliPayScanProperties aliPayScanProperties) {
        AliPayScanConfig aliPayScanConfig = new AliPayScanConfig();

        aliPayScanConfig.setAppId(aliPayScanProperties.getAppId());
        aliPayScanConfig.setAliPayPublicKey(aliPayScanProperties.getAliPayPublicKey());
        aliPayScanConfig.setPrivateKey(aliPayScanProperties.getPrivateKey());
        aliPayScanConfig.setGatewayUrl(aliPayScanProperties.getGatewayUrl());
        aliPayScanConfig.setNotifyUrl(aliPayScanProperties.getNotifyUrl());
        return new AliPayScanServiceImpl(aliPayScanConfig);
    }
}
