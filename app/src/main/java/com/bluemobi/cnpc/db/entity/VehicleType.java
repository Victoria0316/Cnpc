package com.bluemobi.cnpc.db.entity;

/**
 * Created by wangzhijun on 2015/7/22.
 */
public class VehicleType extends SoftModel{


    public String getFrontLetter() {
        return frontLetter;
    }

    public void setFrontLetter(String frontLetter) {
        this.frontLetter = frontLetter;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getIsSale() {
        return isSale;
    }

    public void setIsSale(String isSale) {
        this.isSale = isSale;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getParentBrandCode() {
        return parentBrandCode;
    }

    public void setParentBrandCode(String parentBrandCode) {
        this.parentBrandCode = parentBrandCode;
    }

    private String frontLetter;// 品牌名称首字母,
    private String isSale;// 是否在售(0是1否),

    private String brandName;// level 1 品牌名称  level2  车型名称
    private String carId;//level 1   品牌id, level2  车型ID
    private String level;// 品牌级别(1;//汽车品牌，2;//出售车型),
    private String parentBrandCode;// 父品牌id


}
