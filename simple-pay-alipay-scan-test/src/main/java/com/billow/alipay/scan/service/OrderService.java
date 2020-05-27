package com.billow.alipay.scan.service;

import com.billow.alipay.scan.model.OrderInfo;

import javax.servlet.http.HttpServletRequest;

public interface OrderService {

    OrderInfo updateOrder(HttpServletRequest request);
}
