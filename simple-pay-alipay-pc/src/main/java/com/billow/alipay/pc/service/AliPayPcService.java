package com.billow.alipay.pc.service;

import com.alipay.api.domain.AlipayTradePagePayModel;
import com.billow.alipay.base.service.AliPayBaseService;
import com.billow.alipay.pc.model.OrderInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AliPayPcService extends AliPayBaseService {

    /**
     * 调用支付宝pc 支付接口，返回支付宝支付页面，使用以下直接写入页面<br/>
     * <p>
     * response.setContentType("text/html;charset=UTF-8");<br/>
     * PrintWriter out = response.getWriter();<br/>
     * out.write(form);<br/>
     * out.flush();<br/>
     * out.close();<br/>
     *
     * @param model    订单参数，<br/>
     *                 outTradeNo:商户订单号，商户网站订单系统中唯一订单号，必填<br/>
     *                 totalAmount:付款金额，必填<br/>
     *                 subject:订单名称，必填<br/>
     * @param response
     * @return void
     * @throws Exception
     * @author billow
     * @date 2019/12/22 15:38
     */
    void tradePage(HttpServletResponse response, AlipayTradePagePayModel model) throws Exception;

    /**
     * 同步支付通知
     *
     * @param updateOrderStausService
     * @param request
     * @return com.billow.alipay.pc.model.OrderInfo
     * @author billow
     * @date 2019/12/22 16:55
     */

    OrderInfo returnUrl(HttpServletRequest request, AliPayUpdateOrderStausService updateOrderStausService);

    /**
     * 异步支付通知
     *
     * @param updateOrderStausService
     * @param request
     * @return com.billow.alipay.pc.model.OrderInfo
     * @author billow
     * @date 2019/12/22 17:14
     */
    OrderInfo notifyUrl(HttpServletRequest request, AliPayUpdateOrderStausService updateOrderStausService);
}
