package com.billow.alipay.pc.service;

import com.billow.alipay.pc.model.OrderInfo;

public interface AliPayUpdateOrderStausService {

    /**
     * 更新订单状态
     *
     * @param orderInfo 订单信息
     * @return boolean 是否更新成功
     * @author billow
     * @date 2019/12/22 18:14
     */
    boolean updateOrderStaus(OrderInfo orderInfo);

    /**
     * 校验订单是否正确
     *
     * @param orderInfo 订单信息
     * @return boolean
     * @author billow
     * @date 2019/12/22 18:15
     */
    boolean checkOrderData(OrderInfo orderInfo);
}
