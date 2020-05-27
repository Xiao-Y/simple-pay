package com.billow.alipay.pc.service;

import com.billow.alipay.pc.model.OrderInfo;

import javax.servlet.http.HttpServletRequest;

public interface OrderService {

    OrderInfo updateOrder(HttpServletRequest request);
}
