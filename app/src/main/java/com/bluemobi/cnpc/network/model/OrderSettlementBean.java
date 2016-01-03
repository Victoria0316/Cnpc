package com.bluemobi.cnpc.network.model;

import java.io.Serializable;

/**
 * Created by gaoyn on 2015/8/24.
 */
public class OrderSettlementBean implements Serializable{

    private String id; //用户的顾客ID
    private String userId; //用户登录的ID
    private String changeBalance; //零钱包余额
    private String oilMoneyBalance;// 油钱包余额
    private OrderSettlementInfo customerBonus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getChangeBalance() {
        return changeBalance;
    }

    public void setChangeBalance(String changeBalance) {
        this.changeBalance = changeBalance;
    }

    public String getOilMoneyBalance() {
        return oilMoneyBalance;
    }

    public void setOilMoneyBalance(String oilMoneyBalance) {
        this.oilMoneyBalance = oilMoneyBalance;
    }

    public OrderSettlementInfo getCustomerBonus() {
        return customerBonus;
    }

    public void setCustomerBonus(OrderSettlementInfo customerBonus) {
        this.customerBonus = customerBonus;
    }
}
