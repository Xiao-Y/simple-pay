package com.billow.alipay.pc.service.impl;

import com.billow.alipay.pc.model.OrderInfo;
import com.billow.alipay.pc.service.AliPayPcService;
import com.billow.alipay.pc.service.AliPayUpdateOrderStausService;
import com.billow.alipay.pc.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liuyongtao
 * @create 2020-05-27 9:43
 */
public class OrderServiceImpl implements OrderService {

    @Autowired
    private AliPayPcService aliPayPcService;
    @Autowired
    private AliPayUpdateOrderStausService aliPayUpdateOrderStausService;

    // 添加事务
    @Override
    public OrderInfo updateOrder(HttpServletRequest request) {
        // 库存调整
        // 支付，订单检查，更新订单
        OrderInfo orderInfo = aliPayPcService.returnUrl(request, aliPayUpdateOrderStausService);
        if (!orderInfo.isPaySataus()) {
            // 事务回滚
            throw new RuntimeException("订单号：" + orderInfo.getOutTradeNo() + "，支付失败！");
        }
        // 回写交易号
        // 订单生成
        // 消息通知
        // 物流更新
        return orderInfo;
    }
}
