package com.billow.config;


import com.billow.properties.AliPayPcProperties;
import com.billow.service.AliPayPcService;
import com.billow.service.impl.AliPayPcServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liuyongtao
 * @create 2019-12-22 15:42
 */
@Configuration
public class BeanConfig {

    @Bean
    public AliPayPcService aliPayPcService(AliPayPcProperties aliPayPcProperties) {
        AliPayPcConfig aliPayPcConfig = new AliPayPcConfig();

        aliPayPcConfig.setAppId(aliPayPcProperties.getAppId());
        aliPayPcConfig.setAliPayPublicKey(aliPayPcProperties.getAliPayPublicKey());
        aliPayPcConfig.setPrivateKey(aliPayPcProperties.getPrivateKey());
        aliPayPcConfig.setGatewayUrl(aliPayPcProperties.getGatewayUrl());
        aliPayPcConfig.setNotifyUrl(aliPayPcProperties.getNotifyUrl());
        aliPayPcConfig.setReturnUrl(aliPayPcProperties.getReturnUrl());

        return new AliPayPcServiceImpl(aliPayPcConfig);
    }

}
