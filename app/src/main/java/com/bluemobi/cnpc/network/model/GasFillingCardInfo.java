package com.bluemobi.cnpc.network.model;

import java.io.Serializable;

/**
 * Created by gaoyn on 2015/9/1.
 */
public class GasFillingCardInfo implements Serializable {

    private String id;   //加油卡id
    private String cardNumber;   //加油卡号
    private String gasStationName;   //加油站名称
    private String headquartersNo;   //总部编号
    private String cardBalance;   //余额
    private String cardIntegral;   //积分
    private String isDefault;   //是否是默认0不默认1默认

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getGasStationName() {
        return gasStationName;
    }

    public void setGasStationName(String gasStationName) {
        this.gasStationName = gasStationName;
    }

    public String getHeadquartersNo() {
        return headquartersNo;
    }

    public void setHeadquartersNo(String headquartersNo) {
        this.headquartersNo = headquartersNo;
    }

    public String getCardBalance() {
        return cardBalance;
    }

    public void setCardBalance(String cardBalance) {
        this.cardBalance = cardBalance;
    }

    public String getCardIntegral() {
        return cardIntegral;
    }

    public void setCardIntegral(String cardIntegral) {
        this.cardIntegral = cardIntegral;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }
}
