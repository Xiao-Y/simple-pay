package com.billow.wx.base.config;

import com.billow.wx.base.properties.WxPayCommonProperties;
import com.github.wxpay.sdk.IWXPayDomain;
import com.github.wxpay.sdk.WXPayConfig;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * 设置 wx 参数
 *
 * @author liuyongtao
 * @create 2019-12-25 10:36
 */
public class WxPayBaseConfig extends WXPayConfig {

    private byte[] certData;
    private WxPayCommonProperties properties;

    public WxPayBaseConfig(WxPayCommonProperties properties) throws Exception {
        this.properties = properties;

        String certPath = properties.getCertPath();
        File file = new File(certPath);
        InputStream certStream = new FileInputStream(file);
        this.certData = new byte[(int) file.length()];
        certStream.read(this.certData);
        certStream.close();
    }

    @Override
    public String getAppID() {
        return properties.getAppID();
    }

    @Override
    public String getMchID() {
        return properties.getMchID();
    }

    @Override
    public String getKey() {
        return properties.getKey();
    }

    @Override
    public InputStream getCertStream() {
        ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return properties.getHttpConnectTimeoutMs();
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return properties.getHttpReadTimeoutMs();
    }

    @Override
    public boolean shouldAutoReport() {
        return false;
    }

    @Override
    public IWXPayDomain getWXPayDomain() {
//        return new IWXPayDomain() {
//            @Override
//            public void report(String domain, long elapsedTimeMillis, Exception ex) {
//
//            }
//
//            @Override
//            public DomainInfo getDomain(WXPayConfig config) {
//                return new IWXPayDomain.DomainInfo(WXPayConstants.DOMAIN_API, true);
//            }
//        };
        return null;
    }
}
