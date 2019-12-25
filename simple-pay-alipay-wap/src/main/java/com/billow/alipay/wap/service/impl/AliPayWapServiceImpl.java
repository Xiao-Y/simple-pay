package com.billow.alipay.wap.service.impl;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.billow.alipay.base.service.impl.AliPayTradeBaseServiceImpl;
import com.billow.alipay.wap.config.AliPayWapConfig;
import com.billow.alipay.wap.model.OrderInfo;
import com.billow.alipay.wap.service.AliPayUpdateOrderStausService;
import com.billow.alipay.wap.service.AliPayWapService;
import com.billow.alipay.wap.utils.AliPayUtils;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author liuyongtao
 * @create 2019-12-24 11:30
 */
@Slf4j
public class AliPayWapServiceImpl extends AliPayTradeBaseServiceImpl implements AliPayWapService {

    private AliPayWapConfig aliPayWapConfig;

    private AlipayClient alipayClient;

    public AliPayWapServiceImpl(AliPayWapConfig aliPayWapConfig) {
        this.aliPayWapConfig = aliPayWapConfig;
        this.alipayClient = new DefaultAlipayClient(aliPayWapConfig.getGatewayUrl(), aliPayWapConfig.getAppId(),
                aliPayWapConfig.getPrivateKey(), aliPayWapConfig.getFormat(), aliPayWapConfig.getCharset(),
                aliPayWapConfig.getAliPayPublicKey(), aliPayWapConfig.getSignType());
        super.setAlipayClient(this.alipayClient);
    }


    @Override
    public AlipayTradeWapPayResponse wapPay(AlipayTradeWapPayModel model) throws Exception {
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        request.setBizModel(model);
        return alipayClient.execute(request);
    }

    @Override
    public String notifyUrl(HttpServletRequest request, AliPayUpdateOrderStausService updateOrderStausService) {
        log.debug("异常通知========>>>>>");
        try {
            return this.callBackNotify(request, updateOrderStausService);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        log.debug("notify_url 验证失败");
        return "failure";
    }

    private String callBackNotify(HttpServletRequest request, AliPayUpdateOrderStausService updateOrderStausService) throws Exception {
        Map<String, String> map = AliPayUtils.toMap(request);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            log.debug("异步回调返回：" + entry.getKey() + " = " + entry.getValue());
        }
        // 验签名
        boolean verifyResult = AlipaySignature.rsaCheckV1(map, aliPayWapConfig.getAliPayPublicKey(),
                aliPayWapConfig.getCharset(), aliPayWapConfig.getSignType());
        log.debug("验证:{}", verifyResult);
        if (verifyResult) {
            if (updateOrderStausService != null) {
                OrderInfo orderInfo = new OrderInfo(map.get("app_id"), map.get("out_trade_no"),
                        map.get("total_amount"), map.get("trade_no"));
                // 业务校验
                updateOrderStausService.checkOrderData(orderInfo);
                // 更新订单状态
                updateOrderStausService.updateOrderStatus(orderInfo);
            }
            return "success";
        }
        return "failure";
    }
}
