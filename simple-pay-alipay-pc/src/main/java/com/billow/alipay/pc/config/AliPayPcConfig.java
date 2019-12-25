package com.billow.alipay.pc.config;

import com.billow.alipay.base.config.AliPayBaseConfig;
import lombok.Data;

/**
 * 支付宝支付配置数据
 *
 * @author liuyongtao
 * @create 2019-12-22 14:27
 */
@Data
public class AliPayPcConfig extends AliPayBaseConfig {
    // 异步支付通知回调
    private String notifyUrl;
    // 同步支付通知回调
    private String returnUrl;

}
