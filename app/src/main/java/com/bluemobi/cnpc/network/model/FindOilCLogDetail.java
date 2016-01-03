package com.bluemobi.cnpc.network.model;

import java.io.Serializable;

/**
 * Created by gaozq on 2015/9/14.
 */
public class FindOilCLogDetail implements Serializable {


    private String orderNo;    //订单编号",
    private String transactionTime;    //订单日期",
    private String productName;    //油品名称",
    private String productNum;    //加油升数",
    private String productPrice;    //油品单价",
    private String payAmount;    //加油金额",
    private String preferentialPrice;    //优惠额",
    private String preferentialAmount;    //实际金额",
    private String changeAmount;    //零钱包",
    private String oilAmount;    //油钱包",
    private String couponAmount;    //优惠卷",
    private String actualAmount;    //其他支付",
    private String awardedIntegral;    //赠积分",
    private String actualPayMoney;    //实际支付",

    public String getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(String actualAmount) {
        this.actualAmount = actualAmount;
    }

    public String getActualPayMoney() {
        return actualPayMoney;
    }

    public void setActualPayMoney(String actualPayMoney) {
        this.actualPayMoney = actualPayMoney;
    }

    public String getAwardedIntegral() {
        return awardedIntegral;
    }

    public void setAwardedIntegral(String awardedIntegral) {
        this.awardedIntegral = awardedIntegral;
    }

    public String getChangeAmount() {
        return changeAmount;
    }

    public void setChangeAmount(String changeAmount) {
        this.changeAmount = changeAmount;
    }

    public String getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(String couponAmount) {
        this.couponAmount = couponAmount;
    }

    public String getOilAmount() {
        return oilAmount;
    }

    public void setOilAmount(String oilAmount) {
        this.oilAmount = oilAmount;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(String payAmount) {
        this.payAmount = payAmount;
    }

    public String getPreferentialAmount() {
        return preferentialAmount;
    }

    public void setPreferentialAmount(String preferentialAmount) {
        this.preferentialAmount = preferentialAmount;
    }

    public String getPreferentialPrice() {
        return preferentialPrice;
    }

    public void setPreferentialPrice(String preferentialPrice) {
        this.preferentialPrice = preferentialPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductNum() {
        return productNum;
    }

    public void setProductNum(String productNum) {
        this.productNum = productNum;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }
}
