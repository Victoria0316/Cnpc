package com.bluemobi.cnpc.db.entity;

import java.io.Serializable;

/**
 * Created by liufy on 2015/8/27.
 */
public class Position implements Serializable{

    private double longitude;

    private double latitude;

    private String deptName;

    private String cooper;

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getCooper() {
        return cooper;
    }

    public void setCooper(String cooper) {
        this.cooper = cooper;
    }
}
