package com.bluemobi.cnpc.network.model;

import java.io.Serializable;

/**
 * Created by wangzhijun on 2015/9/13.
 */
public class CouponItem implements Serializable{
    private String id;//private String 红包id",
    private String couponStartTime;//private String 有效开始时间",
    private String couponEndTime;//private String 有效结束时间",
    private String couponValid;//private String 有效天数（0无限制）",
    private String minOrderAmount;//private String 最小订单金额",
    private String oilLimit;//private String 是否限油品(0.无限制，1限油品，2限非油品）",
    private String deptCode;//private String 参与组织代码（加油站）",
    private String deptName;//private String 参与组织名称（加油站）",
    private String couponDiscount;//private String 折扣金额，折扣率，积分值，现金值，运费",
    private String couponName;//private String 优惠券名称",
    private String barcode;//barCode
    private String couponId;//barCode

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCouponStartTime() {
        return couponStartTime;
    }

    public void setCouponStartTime(String couponStartTime) {
        this.couponStartTime = couponStartTime;
    }

    public String getCouponEndTime() {
        return couponEndTime;
    }

    public void setCouponEndTime(String couponEndTime) {
        this.couponEndTime = couponEndTime;
    }

    public String getCouponValid() {
        return couponValid;
    }

    public void setCouponValid(String couponValid) {
        this.couponValid = couponValid;
    }

    public String getMinOrderAmount() {
        return minOrderAmount;
    }

    public void setMinOrderAmount(String minOrderAmount) {
        this.minOrderAmount = minOrderAmount;
    }

    public String getOilLimit() {
        return oilLimit;
    }

    public void setOilLimit(String oilLimit) {
        this.oilLimit = oilLimit;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getCouponDiscount() {
        return couponDiscount;
    }

    public void setCouponDiscount(String couponDiscount) {
        this.couponDiscount = couponDiscount;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }
}
