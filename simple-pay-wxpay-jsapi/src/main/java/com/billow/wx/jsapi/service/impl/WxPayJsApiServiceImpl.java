package com.billow.wx.jsapi.service.impl;

import com.billow.wx.base.enums.NotifyTypeEnum;
import com.billow.wx.base.model.UnifiedOrderModel;
import com.billow.wx.base.service.impl.WxPayBaseServiceImpl;
import com.billow.wx.jsapi.config.WXPayJsApiConfig;
import com.billow.wx.jsapi.model.OrderInfo;
import com.billow.wx.jsapi.service.WxPayJsApiService;
import com.billow.wx.jsapi.service.WxPayUpdateOrderStausService;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liuyongtao
 * @create 2019-12-25 10:55
 */
@Slf4j
public class WxPayJsApiServiceImpl extends WxPayBaseServiceImpl implements WxPayJsApiService {

    private WXPay wxpay;

    WxPayJsApiServiceImpl(WXPayJsApiConfig config) {
        this.wxpay = new WXPay(config);
        super.setWxPay(wxpay);
    }

    @Override
    public String unifiedOrderToQrCode(UnifiedOrderModel model) throws Exception {
        Map<String, String> map = this.unifiedOrder(model);
        if (!WXPayConstants.SUCCESS.equals(map.get("result_code"))) {
            throw new RuntimeException("获取支付连接失败！");
        }
        return map.get("code_url");
    }

    @Override
    public void payResultNotify(HttpServletRequest request, HttpServletResponse response, WxPayUpdateOrderStausService updateOrderStausService) {
        this.resultNotify(request, response, updateOrderStausService, NotifyTypeEnum.PAY_RESULT, "支付通知：系统异常：{}", "支付通知：返回微信平台失败：{}");
    }


    @Override
    public void refundResultNotify(HttpServletRequest request, HttpServletResponse response, WxPayUpdateOrderStausService updateOrderStausService) {
        this.resultNotify(request, response, updateOrderStausService, NotifyTypeEnum.REFUND_RESULT, "退款通知：系统异常：{}", "退款通知：返回微信平台失败：{}");
    }

    /**
     * 通知结果
     *
     * @param request
     * @param response
     * @param updateOrderStausService
     * @param payResult
     * @param s
     * @param s2
     * @return void
     * @author LiuYongTao
     * @date 2019/12/25 17:01
     */
    private void resultNotify(HttpServletRequest request, HttpServletResponse response, WxPayUpdateOrderStausService updateOrderStausService,
                              NotifyTypeEnum payResult, String s, String s2) {
        Map<String, String> resultMap = new HashMap<>();

        try (InputStream inStream = request.getInputStream();
             ByteArrayOutputStream outSteam = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
            // 获取微信调用我们notify_url的返回信息
            String result = new String(outSteam.toByteArray(), "utf-8");
            // xml转换为map
            Map<String, String> notifyMap = WXPayUtil.xmlToMap(result);// 转换成map
            // 签名正确
            if (wxpay.isPayResultNotifySignatureValid(notifyMap)) {
                // 进行处理。
                if (updateOrderStausService != null) {
                    OrderInfo orderInfo = new OrderInfo(notifyMap.get("appid"), notifyMap.get("out_trade_no"),
                            notifyMap.get("total_fee"), notifyMap.get("transaction_id"), payResult);
                    // 注意特殊情况：订单已经退款，但收到了支付结果成功的通知，不应把商户侧订单状态从退款改成支付成功
                    updateOrderStausService.checkOrderData(orderInfo);
                    updateOrderStausService.updateOrderStatus(orderInfo);
                }
                // 通知成功
                resultMap.put("return_code", WXPayConstants.SUCCESS);
                resultMap.put("return_msg", "OK");
            } else {
                // 通知失败
                resultMap.put("return_code", WXPayConstants.FAIL);
                resultMap.put("return_msg", "");
            }
        } catch (Exception e) {
            log.error(s, e.getLocalizedMessage());
        } finally {
            try (BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());) {
                out.write(WXPayUtil.mapToXml(resultMap).getBytes());
                out.flush();
            } catch (Exception ee) {
                log.error(s2, ee.getLocalizedMessage());
            }
        }
    }
}
