package com.billow.wx.base.exception;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author liuyongtao
 * @create 2019-12-25 11:15
 */
@Slf4j
public class ReturnCodeException extends RuntimeException {

    public ReturnCodeException(Map<String, String> resp) {
        super("微信支付调用异常，请稍后重试！");
        log.error("err_code:{},err_code_des:{}", resp.get("err_code"), resp.get("err_code_des"));
    }
}
