package com.billow.wx.jsapi.service;


import com.billow.wx.jsapi.model.OrderInfo;

public interface WxPayUpdateOrderStausService {

    /**
     * 更新订单状态
     *
     * @param orderInfo 订单信息
     * @return boolean 是否更新成功
     * @author billow
     * @date 2019/12/22 18:14
     */
    boolean updateOrderStatus(OrderInfo orderInfo);

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
