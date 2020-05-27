package com.billow.alipay.pc.config;

import com.billow.alipay.base.config.AliPayBaseConfig;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * 支付宝支付配置数据
 *
 * @author liuyongtao
 * @create 2019-12-22 14:27
 */
@Data
@NoArgsConstructor
public class AliPayPcConfig extends AliPayBaseConfig {
    // 异步支付通知回调
    @NonNull
    private String notifyUrl;
    // 同步支付通知回调
    @NonNull
    private String returnUrl;

}
