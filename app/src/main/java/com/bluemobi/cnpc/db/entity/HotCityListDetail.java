package com.bluemobi.cnpc.db.entity;

import org.litepal.crud.DataSupport;

/**
 * Created by liufy on 2015/8/24.
 */
public class HotCityListDetail extends DataSupport {
    private String sid;//主键,
    private String pid;//父ID,
    private String divisionName;//行政区划名称全称,

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }
}
