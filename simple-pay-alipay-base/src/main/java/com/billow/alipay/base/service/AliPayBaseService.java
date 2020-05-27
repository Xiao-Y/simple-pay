package com.billow.alipay.base.service;

import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayDataDataserviceBillDownloadurlQueryModel;
import com.alipay.api.domain.AlipayTradeCancelModel;
import com.alipay.api.domain.AlipayTradeCloseModel;
import com.alipay.api.domain.AlipayTradeCreateModel;
import com.alipay.api.domain.AlipayTradeFastpayRefundQueryModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.response.AlipayDataDataserviceBillDownloadurlQueryResponse;
import com.alipay.api.response.AlipayTradeCancelResponse;
import com.alipay.api.response.AlipayTradeCloseResponse;
import com.alipay.api.response.AlipayTradeCreateResponse;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;

/**
 * 交易接口
 *
 * @author liuyongtao
 * @create 2019-12-23 22:01
 */
public interface AliPayBaseService {

    String STATUS_FAIL = "failure";

    String STATUS_SUCCESS = "success";

    /**
     * 设置 alipay 基类参数
     *
     * @param alipayClient
     * @return void
     * @author LiuYongTao
     * @date 2019/12/24 8:57
     */
    void setAlipayClient(AlipayClient alipayClient);

    /**
     * 统一收单线下交易查询接口，https://docs.open.alipay.com/api_1/alipay.trade.query
     *
     * @param queryModel:outTradeNo,tradeNo 订单支付时传入的商户订单号,和支付宝交易号不能同时为空。trade_no,out_trade_no如果同时存在优先取trade_no <br/>
     * @return com.alipay.api.response.AlipayTradeQueryResponse
     * @author billow
     * @date 2019/12/23 22:16
     */
    AlipayTradeQueryResponse tradeQuery(AlipayTradeQueryModel queryModel) throws Exception;

    /**
     * 统一收单交易退款查询 https://docs.open.alipay.com/api_1/alipay.trade.fastpay.refund.query
     *
     * @param queryModel outTradeNo,tradeNo 订单支付时传入的商户订单号,和支付宝交易号不能同时为空。trade_no,out_trade_no如果同时存在优先取trade_no <br/>
     *                   outRequestNo 请求退款接口时，传入的退款请求号，如果在退款请求时未传入，则该值为创建交易时的外部交易号
     * @return com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse
     * @author billow
     * @date 2019/12/23 22:31
     */
    AlipayTradeFastpayRefundQueryResponse refundQuery(AlipayTradeFastpayRefundQueryModel queryModel) throws Exception;

    /**
     * 统一收单交易退款接口 https://docs.open.alipay.com/api_1/alipay.trade.refund
     *
     * @param refundModel outTradeNo,tradeNo 订单支付时传入的商户订单号,和支付宝交易号不能同时为空。trade_no,out_trade_no如果同时存在优先取trade_no <br/>
     *                    refundAmount 需要退款的金额，该金额不能大于订单金额,单位为元，支持两位小数(必选) <br/>
     * @return com.alipay.api.response.AlipayTradeRefundResponse
     * @author LiuYongTao
     * @date 2019/12/24 9:19
     */
    AlipayTradeRefundResponse tradeRefund(AlipayTradeRefundModel refundModel) throws Exception;

    /**
     * 统一收单交易关闭接口 https://docs.open.alipay.com/api_1/alipay.trade.close <br/>
     * 用于交易创建后，用户在一定时间内未进行支付，可调用该接口直接将未付款的交易进行关闭 <br/>
     *
     * @param closeModel outTradeNo,tradeNo 订单支付时传入的商户订单号,和支付宝交易号不能同时为空。trade_no,out_trade_no如果同时存在优先取trade_no <br/>
     * @return com.alipay.api.response.AlipayTradeCloseResponse
     * @author LiuYongTao
     * @date 2019/12/24 9:27
     */
    AlipayTradeCloseResponse tradeClose(AlipayTradeCloseModel closeModel) throws Exception;

    /**
     * 查询对账单下载地址 https://docs.open.alipay.com/api_15/alipay.data.dataservice.bill.downloadurl.query <br/>
     * 为方便商户快速查账，支持商户通过本接口获取商户离线账单下载地址
     *
     * @param queryModel billType 账单类型：trade、signcustomer；trade 指商户基于支付宝交易收单的业务账单；signcustomer 是指基于商户支付宝余额收入及支出等资金变动的帐务账单。
     *                   billDate 账单时间：日账单格式为yyyy-MM-dd，最早可下载2016年1月1日开始的日账单；月账单格式为yyyy-MM，最早可下载2016年1月开始的月账单。
     * @return com.alipay.api.response.AlipayDataDataserviceBillDownloadurlQueryResponse
     * @author LiuYongTao
     * @date 2019/12/24 9:52
     */
    AlipayDataDataserviceBillDownloadurlQueryResponse downloadUrlQuery(AlipayDataDataserviceBillDownloadurlQueryModel queryModel) throws Exception;


    /**
     * 统一收单交易撤销接口
     * https://docs.open.alipay.com/api_1/alipay.trade.cancel
     * 支付交易返回失败或支付系统超时，调用该接口撤销交易
     *
     * @param model outTradeNo,tradeNo 订单支付时传入的商户订单号,和支付宝交易号不能同时为空。trade_no,out_trade_no如果同时存在优先取trade_no <br/>
     * @return com.alipay.api.response.AlipayTradeCancelResponse
     * @author LiuYongTao
     * @date 2019/12/27 10:06
     */
    AlipayTradeCancelResponse tradeCancel(AlipayTradeCancelModel model) throws Exception;

    /**
     * 统一收单交易创建接口
     * https://docs.open.alipay.com/api_1/alipay.trade.create
     * 商户通过该接口进行交易的创建下单
     *
     * @param model out_trade_no,total_amount,subject 必选
     * @return com.alipay.api.response.AlipayTradeCreateResponse
     * @author LiuYongTao
     * @date 2019/12/27 10:14
     */
    AlipayTradeCreateResponse tradeCreate(AlipayTradeCreateModel model) throws Exception;
}
