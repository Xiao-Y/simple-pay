package com.billow.alipay.wap.config;

import com.billow.alipay.base.config.AliPayBaseConfig;
import lombok.Data;

/**
 * 支付宝wap配置
 *
 * @author liuyongtao
 * @create 2019-12-24 11:28
 */
@Data
public class AliPayWapConfig extends AliPayBaseConfig {

    // 异步支付通知回调
    private String notifyUrl;

}
