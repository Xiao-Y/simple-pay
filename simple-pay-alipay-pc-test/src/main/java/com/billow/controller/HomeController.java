package com.billow.controller;

import com.alipay.api.domain.AlipayTradePagePayModel;
import com.billow.service.AliPayPcService;
import com.billow.service.AliPayUpdateOrderStausService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class HomeController {

    @Autowired
    private AliPayPcService aliPayPcService;
    @Autowired
    private AliPayUpdateOrderStausService aliPayUpdateOrderStausService;

    @RequestMapping(value = "/pay")
    public void pay(HttpServletResponse response) throws Exception {

        AlipayTradePagePayModel model = new AlipayTradePagePayModel();
        model.setOutTradeNo(UUID.randomUUID().toString());
        model.setTotalAmount("0.01");
        model.setSubject("测试商品");
        model.setBody("这是一个测试商品");

        aliPayPcService.tradePage(response, model);
    }

    @RequestMapping(value = "/return")
    @ResponseBody
    public String returnUrl(HttpServletRequest request) throws Exception {
        return aliPayPcService.returnUrl(request, aliPayUpdateOrderStausService);
    }

    @RequestMapping(value = "/notify")
    @ResponseBody
    public String notify(HttpServletRequest request) throws Exception {
        return aliPayPcService.notifyUrl(request, aliPayUpdateOrderStausService);
    }
}
