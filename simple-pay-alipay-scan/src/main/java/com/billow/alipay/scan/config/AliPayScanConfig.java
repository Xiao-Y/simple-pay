package com.billow.alipay.scan.config;

import com.billow.alipay.base.config.AliPayBaseConfig;

/**
 * @author liuyongtao
 * @create 2019-12-27 10:20
 */
public class AliPayScanConfig extends AliPayBaseConfig {
    // 异步支付通知回调
    private String notifyUrl;
    // 同步支付通知回调
    private String returnUrl;

}