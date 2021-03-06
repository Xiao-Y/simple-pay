package com.billow.wx.base.service.impl;

import com.billow.wx.base.exception.ReturnCodeException;
import com.billow.wx.base.model.QueryModel;
import com.billow.wx.base.model.RefundModel;
import com.billow.wx.base.model.UnifiedOrderModel;
import com.billow.wx.base.service.WxPayBaseService;
import com.billow.wx.base.utils.ConvertUtils;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuyongtao
 * @create 2019-12-25 11:27
 */
@Slf4j
public class WxPayBaseServiceImpl implements WxPayBaseService {

    private WXPay wxPay;

    @Override
    public void setWxPay(WXPay wxPay) {
        this.wxPay = wxPay;
    }

    @Override
    public Map<String, String> unifiedOrder(UnifiedOrderModel model) throws Exception {
        Map<String, String> data = ConvertUtils.entityToMap(model);
        log.debug("unifiedorder:====>>>> {}", data);
        Map<String, String> resp = wxPay.unifiedOrder(data);
        log.debug("unifiedorder:<<<<==== {}", resp);
        // SUCCESS/FAIL
        String returnCode = resp.get("return_code");
        if (WXPayConstants.FAIL.equals(returnCode)) {
            throw new ReturnCodeException(resp);
        }
        return resp;
    }

    @Override
    public Map<String, String> orderQueryByTransactionId(@NonNull String transactionId) throws Exception {
        return this.orderQuery(transactionId, null);
    }

    @Override
    public Map<String, String> orderQueryByOutTradeNo(@NonNull String outTradeNo) throws Exception {
        return this.orderQuery(null, outTradeNo);
    }

    @Override
    public Map<String, String> refundQueryByTransactionId(@NonNull String transactionId) throws Exception {
        return this.refundQuery(transactionId, null, null, null);
    }

    @Override
    public Map<String, String> refundQueryByOutTradeNo(@NonNull String outTradeNo) throws Exception {
        return this.refundQuery(null, outTradeNo, null, null);
    }

    @Override
    public Map<String, String> refundQueryByOutRefundNo(@NonNull String outRefundNo) throws Exception {
        return this.refundQuery(null, null, outRefundNo, null);
    }

    @Override
    public Map<String, String> refundQueryByRefundId(@NonNull String refundId) throws Exception {
        return this.refundQuery(null, null, null, refundId);
    }

    @Override
    public Map<String, String> refundByTransactionId(RefundModel model) throws Exception {
        @NonNull String transactionId = model.getTransactionId();
        Map<String, String> data = ConvertUtils.entityToMap(model);
        log.debug("refundByTransactionId:====>>>> {}", data);
        Map<String, String> resp = wxPay.refund(data);
        String returnCode = resp.get("return_code");
        if (WXPayConstants.FAIL.equals(returnCode)) {
            throw new ReturnCodeException(resp);
        }
        return resp;
    }

    @Override
    public Map<String, String> refundByOutTradeNo(RefundModel model) throws Exception {
        @NonNull String tradeNo = model.getOutTradeNo();
        Map<String, String> data = ConvertUtils.entityToMap(model);
        log.debug("refundByOutTradeNo:====>>>> {}", data);
        Map<String, String> resp = wxPay.refund(data);
        String returnCode = resp.get("return_code");
        if (WXPayConstants.FAIL.equals(returnCode)) {
            throw new ReturnCodeException(resp);
        }
        return resp;
    }

    @Override
    public Map<String, Object> downloadBill(String billType, @NonNull String billDate) throws Exception {
        Map<String, String> reqData = new HashMap<>();
        if (billType != null) {
            reqData.put("bill_type", billType);
        }
        reqData.put("bill_date", billDate);
        Map<String, String> resp = wxPay.downloadBill(reqData);
        String returnCode = resp.get("return_code");
        if (WXPayConstants.FAIL.equals(returnCode)) {
            throw new ReturnCodeException(resp);
        }
        // 返回账单数据
        String data = resp.get("data");
        String[] split = data.split("\r\n");
        // 标题
        String[] title1 = split[0].substring(1).split(",`");
        List<String[]> titleData1 = new ArrayList<>();
        for (int i = 1; i < split.length - 2; i++) {
            titleData1.add(split[i].substring(1).split(",`"));
        }
        // 汇总标题
        String[] title2 = split[split.length - 2].substring(1).split(",`");
        // 汇总数据
        String[] titleData2 = split[split.length - 1].substring(1).split(",`");

        Map<String, Object> map = new HashMap<>();
        map.put("title1", title1);
        map.put("titleData1", titleData1);
        map.put("title2", title2);
        map.put("titleData2", titleData2);
        return map;
    }

    /**
     * 查询退款
     *
     * @param transactionId
     * @param outTradeNo
     * @param outRefundNo
     * @param refundId
     * @return java.util.Map<java.lang.String, java.lang.String>
     * @author LiuYongTao
     * @date 2019/12/25 14:44
     */
    private Map<String, String> refundQuery(String transactionId, String outTradeNo, String outRefundNo, String refundId) throws Exception {
        QueryModel model = new QueryModel();
        model.setTransactionId(transactionId);
        model.setOutTradeNo(outTradeNo);
        model.setOutRefundNo(outRefundNo);
        model.setRefundId(refundId);
        Map<String, String> data = ConvertUtils.entityToMap(model);
        log.debug("QueryModel:====>>>> {}", data);
        Map<String, String> resp = wxPay.refundQuery(data);
        String returnCode = resp.get("return_code");
        if (WXPayConstants.FAIL.equals(returnCode)) {
            throw new ReturnCodeException(resp);
        }
        return resp;
    }

    /**
     * 查询订单信息
     *
     * @param transactionId
     * @param outTradeNo
     * @return java.util.Map<java.lang.String, java.lang.String>
     * @author LiuYongTao
     * @date 2019/12/25 13:45
     */
    private Map<String, String> orderQuery(String transactionId, String outTradeNo) throws Exception {
        QueryModel model = new QueryModel();
        model.setTransactionId(transactionId);
        model.setOutTradeNo(outTradeNo);
        Map<String, String> data = ConvertUtils.entityToMap(model);
        log.debug("QueryModel:====>>>> {}", data);
        Map<String, String> resp = wxPay.orderQuery(data);
        String returnCode = resp.get("return_code");
        if (WXPayConstants.FAIL.equals(returnCode)) {
            throw new ReturnCodeException(resp);
        }
        return resp;
    }
}
