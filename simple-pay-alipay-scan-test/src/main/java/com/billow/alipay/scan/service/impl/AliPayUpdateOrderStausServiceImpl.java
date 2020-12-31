package com.billow.alipay.scan.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.billow.alipay.scan.model.OrderInfo;
import com.billow.alipay.scan.service.AliPayUpdateOrderStausService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AliPayUpdateOrderStausServiceImpl implements AliPayUpdateOrderStausService {


    @Override
    public boolean updateOrderStatus(OrderInfo orderInfo) {
        log.info(JSONObject.toJSONString(orderInfo));
        return true;
    }

    @Override
    public boolean checkOrderData(OrderInfo orderInfo) {
        log.info(JSONObject.toJSONString(orderInfo));
        return true;
    }
}
