package com.bluemobi.cnpc.network.model;

import java.io.Serializable;

/**
 * Created by gaoyn on 2015/8/28.
 */
public class OrderPaymentSuccessBean implements Serializable{

    private String transactionTime;// 订单创建时间
    private String orderNo;//订单号
    private String productName;//油品名称
    private String productPrice;//油品单价
    private String productNum;//升数
    private String payAmount;//预购金额
    private String productProcedure;//手续费
    private String preferentialAmount;//实际金额
    private String changeAmount;//零钱包支付
    private String actualAmount;// 其他支付
    private String awardedIntegral;//赠积分
    private String availableAmount;//当前累计积分
    private String actualPayment; //实际支付
    private String oilFuturesLiters; //预购油总升数
    private String oilBorrowLiters; //借用油总升数


    public String getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
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

    public String getChangeAmount() {
        return changeAmount;
    }

    public void setChangeAmount(String changeAmount) {
        this.changeAmount = changeAmount;
    }


    public String getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(String actualAmount) {
        this.actualAmount = actualAmount;
    }

    public String getActualPayment() {
        return actualPayment;
    }

    public void setActualPayment(String actualPayment) {
        this.actualPayment = actualPayment;
    }

    public String getAwardedIntegral() {
        return awardedIntegral;
    }

    public void setAwardedIntegral(String awardedIntegral) {
        this.awardedIntegral = awardedIntegral;
    }

    public String getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(String availableAmount) {
        this.availableAmount = availableAmount;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductNum() {
        return productNum;
    }

    public void setProductNum(String productNum) {
        this.productNum = productNum;
    }

    public String getProductProcedure() {
        return productProcedure;
    }

    public void setProductProcedure(String productProcedure) {
        this.productProcedure = productProcedure;
    }

    public String getOilFuturesLiters() {
        return oilFuturesLiters;
    }

    public void setOilFuturesLiters(String oilFuturesLiters) {
        this.oilFuturesLiters = oilFuturesLiters;
    }

    public String getOilBorrowLiters() {
        return oilBorrowLiters;
    }

    public void setOilBorrowLiters(String oilBorrowLiters) {
        this.oilBorrowLiters = oilBorrowLiters;
    }
}
