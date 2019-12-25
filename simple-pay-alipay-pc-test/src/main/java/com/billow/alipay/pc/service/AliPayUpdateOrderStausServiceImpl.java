package com.billow.alipay.pc.service;

import com.alibaba.fastjson.JSONObject;
import com.billow.alipay.pc.model.OrderInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AliPayUpdateOrderStausServiceImpl implements AliPayUpdateOrderStausService {


    @Override
    public boolean updateOrderStaus(OrderInfo orderInfo) {
        log.info(JSONObject.toJSONString(orderInfo));
        return true;
    }

    @Override
    public boolean checkOrderData(OrderInfo orderInfo) {
        log.info(JSONObject.toJSONString(orderInfo));
        return true;
    }
}
