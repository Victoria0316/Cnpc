package com.bluemobi.cnpc.network.model;

import java.io.Serializable;

/**
 * Created by gaoyn on 2015/8/29.
 */
public class OilInBean implements Serializable {

    private String tradeTime; //交易时间
    private String orderno;//订单编号
    private String oilstation;//加油站
    private String cardno;//加油卡号
    private String cardin;//油卡转入
    private String purseout;//油钱包转出
    private String balance;//油钱包当前余额

    public String getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getOilstation() {
        return oilstation;
    }

    public void setOilstation(String oilstation) {
        this.oilstation = oilstation;
    }

    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    public String getCardin() {
        return cardin;
    }

    public void setCardin(String cardin) {
        this.cardin = cardin;
    }

    public String getPurseout() {
        return purseout;
    }

    public void setPurseout(String purseout) {
        this.purseout = purseout;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
