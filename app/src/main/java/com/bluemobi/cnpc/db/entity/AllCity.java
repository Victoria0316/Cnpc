package com.bluemobi.cnpc.db.entity;

import java.io.Serializable;

/**
 * Created by wangzhijun on 2015/7/22.
 */
public class AllCity extends SoftModel {
    private String name;   //显示的数据

    private String addressid;



    public String getAddressid() {
        return addressid;
    }

    public void setAddressid(String addressid) {
        this.addressid = addressid;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }


}
