package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.SettlementResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyn on 2015/8/24.
 *
 * P4_4套餐-提交订单
 */
public class SettlementRequest extends CnpcHttpJsonRequest<SettlementResponse> {

    private static final String APIPATH = "mobi/oilfuturesorder/saveOrderInfo";

    private String userId ; //	用户登录信息ID
    private String productId ; //	套餐ID
    private String orderType ; //	订单类型 固定值“1”
    private String productNum ; //	套餐交易数量 没有传0
    private String preferentialPrice ; // 	优惠价格 没有传0.00
    private String preferentialAmount ; //	优惠后应该支付金额 没有传0.00
    private String customerBonusId ; //	使用的红包ID 如果没有传“-1”
    private String redAmount  ; //	红包优惠金额 没有传0.00

    private String changeAmount ; //	零钱包优惠金额 没有传0.00
    private String payChannel 	; //	支付渠道（1现金，2银行卡转账，3支付宝，4微信，5银联，11内部转账）
    private String actualAmount ; //支付渠道实际支付金额 如果支付渠道传值，此值必须传


    public SettlementRequest(Response.Listener<SettlementResponse> listener, Response.ErrorListener errorListener) {
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
        map.put("orderType", "1");
        map.put("productNum", productNum == null? "0":productNum);
        map.put("preferentialPrice", preferentialPrice == null? "0.00":preferentialPrice);
        map.put("preferentialAmount", preferentialAmount == null? "0.00":preferentialAmount);
        map.put("customerBonusId", customerBonusId == null? "-1":customerBonusId);
        map.put("redAmount", redAmount == null? "0.00":redAmount);
        map.put("changeAmount", changeAmount == null? "0.00":changeAmount);
        map.put("payChannel", payChannel == null? "":payChannel);
        map.put("actualAmount", actualAmount == null? "":actualAmount);
        return map;
    }

    @Override
    public Class<SettlementResponse> getResponseClass() {
        return SettlementResponse.class;
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

    public String getPreferentialPrice() {
        return preferentialPrice;
    }

    public void setPreferentialPrice(String preferentialPrice) {
        this.preferentialPrice = preferentialPrice;
    }

    public String getPreferentialAmount() {
        return preferentialAmount;
    }

    public void setPreferentialAmount(String preferentialAmount) {
        this.preferentialAmount = preferentialAmount;
    }

    public String getCustomerBonusId() {
        return customerBonusId;
    }

    public void setCustomerBonusId(String customerBonusId) {
        this.customerBonusId = customerBonusId;
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

    public String getRedAmount() {
        return redAmount;
    }

    public void setRedAmount(String redAmount) {
        this.redAmount = redAmount;
    }

}
