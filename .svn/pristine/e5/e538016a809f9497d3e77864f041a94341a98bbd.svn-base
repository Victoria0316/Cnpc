package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.CityListResponse;
import com.bluemobi.cnpc.network.response.OilPaySaveOrderInfoResponse;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liufy on 2015/8/24.
 * P6_2_2加油-提交订单
 */
public class OilPaySaveOrderInfoRequest extends CnpcHttpJsonRequest<OilPaySaveOrderInfoResponse> {

    private static final String APIPATH = "mobi/oilfuturesorder/saveOrderInfo";
    private String userId;//	用户登录信息ID
    private String productId;//	油品ID
    private String orderType;//	订单类型 固定值“2”
    private String productNum;//	加油升数 没有传0
    private String productTotalValue;//	加油金额 没有传0.00
    private String preferentialPrice;//	优惠金额金额 没有传0.00
    private String changeAmount;//	零钱包优惠金额 没有传0.00
    private String payChannel;//支付渠道（1现金，2银行卡转账，3支付宝，4微信，5银联，11内部转账）
    private String actualAmount;//支付渠道实际支付金额 如果支付渠道传值，此值必须传
    private String vipAmount;//	会员优惠金额 没有传0.00
    private String oilAmount;//	油钱包金额
    private String couponAmount;//	优惠券优惠金额
    private String customerCouponId;//优惠券ID ，当有优惠券金额时，此项必填
    private String gasStationId	;//string	是	加油站ID


    public OilPaySaveOrderInfoRequest(Response.Listener<OilPaySaveOrderInfoResponse> listener,
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
        map.put("productId", productId);//	油品ID
        map.put("orderType", "2");//	订单类型 固定值“2”
        map.put("productNum", productNum == null ? "0" : productNum);//	加油升数 没有传0
        map.put("productTotalValue", productTotalValue==null?"0.00":productTotalValue);//	加油金额 没有传0.00
        map.put("preferentialPrice", preferentialPrice==null?"0.00":preferentialPrice);//	优惠金额金额 没有传0.00
        map.put("changeAmount", changeAmount==null?"0.00":changeAmount);//	零钱包优惠金额 没有传0.00
        map.put("payChannel", payChannel);//支付渠道（1现金，2银行卡转账，3支付宝，4微信，5银联，11内部转账）
        map.put("actualAmount", actualAmount);//支付渠道实际支付金额 如果支付渠道传值，此值必须传
        map.put("vipAmount", vipAmount==null?"0.00":vipAmount);//	会员优惠金额 没有传0.00
        map.put("oilAmount", oilAmount);//	油钱包金额
        map.put("couponAmount", couponAmount);//	优惠券优惠金额
        map.put("customerCouponId", customerCouponId);//优惠券ID ，当有优惠券金额时，此项必填
        map.put("gasStationId", gasStationId);//优惠券ID ，当有优惠券金额时，此项必填

        return map;
    }

    @Override
    public Class<OilPaySaveOrderInfoResponse> getResponseClass() {
        return OilPaySaveOrderInfoResponse.class;
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

    public String getProductTotalValue() {
        return productTotalValue;
    }

    public void setProductTotalValue(String productTotalValue) {
        this.productTotalValue = productTotalValue;
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

    public String getGasStationId() {
        return gasStationId;
    }

    public void setGasStationId(String gasStationId) {
        this.gasStationId = gasStationId;
    }
}
