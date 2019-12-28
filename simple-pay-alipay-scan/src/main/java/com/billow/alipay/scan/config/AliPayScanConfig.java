package com.billow.alipay.scan.config;

import com.billow.alipay.base.config.AliPayBaseConfig;
import lombok.Data;

/**
 * @author liuyongtao
 * @create 2019-12-27 10:20
 */
@Data
public class AliPayScanConfig extends AliPayBaseConfig {
    // 异步支付通知回调
    private String notifyUrl;

}