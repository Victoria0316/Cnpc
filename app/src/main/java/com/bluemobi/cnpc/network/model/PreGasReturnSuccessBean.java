package com.bluemobi.cnpc.network.model;

import java.io.Serializable;

/**
 * Created by gaoyn on 2015/9/11.
 */
public class PreGasReturnSuccessBean implements Serializable {
    private String tradeTime; //订单日期
    private String orderNo; //订单编号
    private String oilId; //油品id
    private String oilName; //油品名称
    private String oilPrice; //油品单价
    private String oilVolume; //购买量
    private String totalPrice; //当前金额/转出金额
    private String handlingCharge; //手续费
    private String totalRevenue; //盈亏
    private String storeTotalFee; //预购油-代储费
    private String oilFuturesPrice; //预购油-预购单价
    private String oilFuturesAmount; //预购油-预购金额
    private String oilBorrowPrice; //借用油-借用单价
    private String guarantyMoney; //借用油-担保金
    private String borrowTotalFee; //借用油-借用费
    private String oilBalance; //剩余升数
    private String oilMoneyBalance; //油钱包余额
    private String changeBalance; //零钱包余额
    private String realTotalPrice; //实际转出金额
    private String futuresSellPoundage; //退货手续费
    private String borrowSellPoundage; //归还手续费

    public String getChangeBalance() {
        return changeBalance;
    }

    public void setChangeBalance(String changeBalance) {
        this.changeBalance = changeBalance;
    }

    public String getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOilId() {
        return oilId;
    }

    public void setOilId(String oilId) {
        this.oilId = oilId;
    }

    public String getOilName() {
        return oilName;
    }

    public void setOilName(String oilName) {
        this.oilName = oilName;
    }

    public String getOilPrice() {
        return oilPrice;
    }

    public void setOilPrice(String oilPrice) {
        this.oilPrice = oilPrice;
    }

    public String getOilVolume() {
        return oilVolume;
    }

    public void setOilVolume(String oilVolume) {
        this.oilVolume = oilVolume;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getHandlingCharge() {
        return handlingCharge;
    }

    public void setHandlingCharge(String handlingCharge) {
        this.handlingCharge = handlingCharge;
    }

    public String getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(String totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public String getStoreTotalFee() {
        return storeTotalFee;
    }

    public void setStoreTotalFee(String storeTotalFee) {
        this.storeTotalFee = storeTotalFee;
    }

    public String getOilFuturesPrice() {
        return oilFuturesPrice;
    }

    public void setOilFuturesPrice(String oilFuturesPrice) {
        this.oilFuturesPrice = oilFuturesPrice;
    }

    public String getOilFuturesAmount() {
        return oilFuturesAmount;
    }

    public void setOilFuturesAmount(String oilFuturesAmount) {
        this.oilFuturesAmount = oilFuturesAmount;
    }

    public String getOilBorrowPrice() {
        return oilBorrowPrice;
    }

    public void setOilBorrowPrice(String oilBorrowPrice) {
        this.oilBorrowPrice = oilBorrowPrice;
    }

    public String getGuarantyMoney() {
        return guarantyMoney;
    }

    public void setGuarantyMoney(String guarantyMoney) {
        this.guarantyMoney = guarantyMoney;
    }

    public String getBorrowTotalFee() {
        return borrowTotalFee;
    }

    public void setBorrowTotalFee(String borrowTotalFee) {
        this.borrowTotalFee = borrowTotalFee;
    }

    public String getOilBalance() {
        return oilBalance;
    }

    public void setOilBalance(String oilBalance) {
        this.oilBalance = oilBalance;
    }

    public String getOilMoneyBalance() {
        return oilMoneyBalance;
    }

    public void setOilMoneyBalance(String oilMoneyBalance) {
        this.oilMoneyBalance = oilMoneyBalance;
    }

    public String getRealTotalPrice() {
        return realTotalPrice;
    }

    public void setRealTotalPrice(String realTotalPrice) {
        this.realTotalPrice = realTotalPrice;
    }

    public String getFuturesSellPoundage() {
        return futuresSellPoundage;
    }

    public void setFuturesSellPoundage(String futuresSellPoundage) {
        this.futuresSellPoundage = futuresSellPoundage;
    }

    public String getBorrowSellPoundage() {
        return borrowSellPoundage;
    }

    public void setBorrowSellPoundage(String borrowSellPoundage) {
        this.borrowSellPoundage = borrowSellPoundage;
    }
}
