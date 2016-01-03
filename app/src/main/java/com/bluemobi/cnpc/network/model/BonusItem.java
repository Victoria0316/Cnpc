package com.bluemobi.cnpc.network.model;

import java.io.Serializable;

/**
 * Created by wangzhijun on 2015/9/13.
 */
public class BonusItem implements Serializable{
    private String id;//private String 红包id",
    private String bonusStartTime;//private String 有效开始时间",
    private String bonusEndTime;//private String 有效结束时间",
    private String bonusStandard;//private String 使用标准0：无标准1：限充值优惠",
    private String bonusDiscount;//private String 折扣金额，折扣率，积分值，现金值，运费",
    private String bonusValid;//private String 有效天数（0无限制）",
    private String minOrderAmount;//private String 最小订单金额",
    private String oilLimit;//private String 是否限油品(0.无限制，1有限制）",
    private String bonusName;//private String 红包名称",
    private String barcode;//private String 生成二维码条形码所需字段",


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBonusStartTime() {
        return bonusStartTime;
    }

    public void setBonusStartTime(String bonusStartTime) {
        this.bonusStartTime = bonusStartTime;
    }

    public String getBonusEndTime() {
        return bonusEndTime;
    }

    public void setBonusEndTime(String bonusEndTime) {
        this.bonusEndTime = bonusEndTime;
    }

    public String getBonusStandard() {
        return bonusStandard;
    }

    public void setBonusStandard(String bonusStandard) {
        this.bonusStandard = bonusStandard;
    }

    public String getBonusDiscount() {
        return bonusDiscount;
    }

    public void setBonusDiscount(String bonusDiscount) {
        this.bonusDiscount = bonusDiscount;
    }

    public String getBonusValid() {
        return bonusValid;
    }

    public void setBonusValid(String bonusValid) {
        this.bonusValid = bonusValid;
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

    public String getBonusName() {
        return bonusName;
    }

    public void setBonusName(String bonusName) {
        this.bonusName = bonusName;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
}
