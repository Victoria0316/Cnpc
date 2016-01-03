package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.CustomerFinancePayResponse;
import com.bluemobi.cnpc.network.response.LoginResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liufy on 2015/8/19.
 * P6_2_2加油-提交订单
 */
public class CustomerFinancePayRequest extends CnpcHttpJsonRequest<CustomerFinancePayResponse> {

    private static final String APIPATH = "mobi/oilfuturesorder/saveOrderInfo";
    public String userId;//	用户登录信息ID
    public String productId;//	油品ID
    public String orderType;//	订单类型 固定值“2”
    public String productNum;//	加油升数 没有传0
    public String productTotalValue;//	加油金额 没有传0.00
    public String preferentialPrice;//	优惠金额金额 没有传0.00
    public String changeAmount;//	零钱包优惠金额 没有传0.00
    public String payChannel;//	支付渠道（1现金，2银行卡转账，3支付宝，4微信，5银联，11内部转账）
    public String actualAmount;//	支付渠道实际支付金额 如果支付渠道传值，此值必须传
    public String vipAmount;//	会员优惠金额 没有传0.00
    public String oilAmount;//	油钱包金额
    public String couponAmount;//	优惠券优惠金额
    public String customerCouponId;//	优惠券ID ，当有优惠券金额时，此项必填

    public CustomerFinancePayRequest(Response.Listener<CustomerFinancePayResponse> listener,
                                     Response.ErrorListener errorListener) {
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
        map.put("orderType", "2");
        map.put("productId", productId);
        map.put("productNum", productNum);
        map.put("productTotalValue", productTotalValue);
        map.put("preferentialPrice", preferentialPrice);
        map.put("changeAmount", changeAmount);
        map.put("payChannel", payChannel);
        map.put("actualAmount", actualAmount);
        map.put("vipAmount", vipAmount);
        map.put("oilAmount", oilAmount);
        map.put("couponAmount", couponAmount);
        map.put("customerCouponId", customerCouponId);


        return map;
    }

    @Override
    public Class getResponseClass() {
        return CustomerFinancePayResponse.class;
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

    public String getProductTotalValue() {
        return productTotalValue;
    }

    public void setProductTotalValue(String productTotalValue) {
        this.productTotalValue = productTotalValue;
    }

    public String getPreferentialPrice() {
        return preferentialPrice;
    }

    public void setPreferentialPrice(String preferentialPrice) {
        this.preferentialPrice = preferentialPrice;
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

    public String getVipAmount() {
        return vipAmount;
    }

    public void setVipAmount(String vipAmount) {
        this.vipAmount = vipAmount;
    }

    public String getOilAmount() {
        return oilAmount;
    }

    public void setOilAmount(String oilAmount) {
        this.oilAmount = oilAmount;
    }

    public String getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(String couponAmount) {
        this.couponAmount = couponAmount;
    }

    public String getCustomerCouponId() {
        return customerCouponId;
    }

    public void setCustomerCouponId(String customerCouponId) {
        this.customerCouponId = customerCouponId;
    }

}
