package com.bluemobi.cnpc.network.model;

import com.bluemobi.cnpc.db.entity.SoftModel;

import java.io.Serializable;

/**
 * Created by gaozq on 2015/9/14.
 */
public class OrderListDTOsBean extends SoftModel implements Serializable {

//    "id": "主键",
//                        "tradeTime": "交易时间",
//                        "deptName": "加油站名称",
//                        "oilMoney": "购买金额",
//                        "oilName": "油品名称",

    private String id;
    private String tradeTime;
    private String deptName;
    private String oilMoney;
    private String oilName;

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOilMoney() {
        return oilMoney;
    }

    public void setOilMoney(String oilMoney) {
        this.oilMoney = oilMoney;
    }

    public String getOilName() {
        return oilName;
    }

    public void setOilName(String oilName) {
        this.oilName = oilName;
    }

    public String getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }
}
