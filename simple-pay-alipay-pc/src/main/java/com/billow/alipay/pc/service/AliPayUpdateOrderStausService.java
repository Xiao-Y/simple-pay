package com.billow.alipay.pc.service;

import com.billow.alipay.pc.model.OrderInfo;

public interface AliPayUpdateOrderStausService {

    /**
     * 更新订单状态（只有checkOrderData为true时才更新）
     *
     * @param orderInfo 订单信息
     * @return boolean 是否更新成功
     * @author billow
     * @date 2019/12/22 18:14
     */
    boolean updateOrderStatus(OrderInfo orderInfo);

    /**
     * 校验订单是否需要更新，如果想要页面显示支付失败，请抛出异常，
     *
     * @param orderInfo 订单信息
     * @return boolean
     * @author billow
     * @date 2019/12/22 18:15
     */
    boolean checkOrderData(OrderInfo orderInfo);
}
