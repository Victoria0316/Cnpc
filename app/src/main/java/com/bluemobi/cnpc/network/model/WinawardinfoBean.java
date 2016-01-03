package com.bluemobi.cnpc.network.model;

import java.io.Serializable;

/**
 * Created by gaozq on 2015/9/15.
 */
public class WinawardinfoBean implements Serializable{

  private String wtype;     //1红包2优惠卷",
      private String discount;     //金额",
      private String awardName;     //名称",
      private String startTime;     //开始时间",
      private String endTime;     //结束时间",
      private String limit;     //限制type1(0无限制,1有限制)type2(0无限制,1限油品,2限非油品)",
      private String standard;     //type2使用标准(0无标准,1限充值优惠)",
      private String minOrderAmount;     //最小订单金额",
      private String validDays;     //有效天数(0为永久有效)",
      private String deptName;     //type2合作加油站名称",


    public String getAwardName() {
        return awardName;
    }

    public void setAwardName(String awardName) {
        this.awardName = awardName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getMinOrderAmount() {
        return minOrderAmount;
    }

    public void setMinOrderAmount(String minOrderAmount) {
        this.minOrderAmount = minOrderAmount;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getValidDays() {
        return validDays;
    }

    public void setValidDays(String validDays) {
        this.validDays = validDays;
    }

    public String getWtype() {
        return wtype;
    }

    public void setWtype(String wtype) {
        this.wtype = wtype;
    }
}
