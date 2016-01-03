package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.OrderPaymentSuccessResponse;
import com.bluemobi.cnpc.network.response.SettlementResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyn on 2015/8/24.
 *
 * P5_2_2预购-提交订单
 *
 * P5_3_2预借-提交订单
 */
public class OrderPaymentSuccessRequest extends CnpcHttpJsonRequest<OrderPaymentSuccessResponse> {

    private static final String APIPATH = "mobi/oilfuturesorder/saveOrderInfo";
                                           

    private String userId ; //	用户登录信息ID
    private String productId ; //	油品ID
    private String orderType ; //	订单类型 固定值“3”
    private String productNum ; //	预购油升数 没有传0
    private String productTotalValue ; // 	预购金额 没有传0.00
    private String productProcedure ; //	手续费金额 没有传0.00
    private String changeAmount ; //	零钱包优惠金额 没有传0.00
    private String actualAmount ; //	支付渠道实际支付金额 如果支付渠道传值，此值必须传
    private String payChannel 	; //	支付渠道（1现金，2银行卡转账，3支付宝，4微信，5银联，11内部转账）
    private String vipAmount ; //会员优惠金额 没有传0.00



    public OrderPaymentSuccessRequest(Response.Listener<OrderPaymentSuccessResponse> listener, Response.ErrorListener errorListener) {
        super(Method.POST, APIPATH, listener, errorListener);
    }

    @Override
    public String GetApiPath() {
        return APIPATH;
    }

    @Override
    public Map<String, String> GetParameters() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userId);
        map.put("productId", productId);
        map.put("orderType", orderType);
        map.put("productNum", productNum == null? "0":productNum);
        map.put("productTotalValue", productTotalValue == null? "0.00":productTotalValue);
        map.put("productProcedure", productProcedure == null? "0.00":productProcedure);
        map.put("changeAmount", changeAmount == null? "0.00":changeAmount);
        map.put("actualAmount", actualAmount == null? "0.00":actualAmount);
        map.put("payChannel", payChannel == null? "":payChannel);
        map.put("vipAmount", vipAmount == null? "":vipAmount);
        return map;
    }

    @Override
    public Class<OrderPaymentSuccessResponse> getResponseClass() {
        return OrderPaymentSuccessResponse.class;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getProductNum() {
        return productNum;
    }

    public void setProductNum(String productNum) {
        this.productNum = productNum;
    }



    public String getChangeAmount() {
        return changeAmount;
    }

    public void setChangeAmount(String changeAmount) {
        this.changeAmount = changeAmount;
    }

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    public String getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(String actualAmount) {
        this.actualAmount = actualAmount;
    }

    public String getProductTotalValue() {
        return productTotalValue;
    }

    public void setProductTotalValue(String productTotalValue) {
        this.productTotalValue = productTotalValue;
    }

    public String getProductProcedure() {
        return productProcedure;
    }

    public void setProductProcedure(String productProcedure) {
        this.productProcedure = productProcedure;
    }

    public String getVipAmount() {
        return vipAmount;
    }

    public void setVipAmount(String vipAmount) {
        this.vipAmount = vipAmount;
    }
}
