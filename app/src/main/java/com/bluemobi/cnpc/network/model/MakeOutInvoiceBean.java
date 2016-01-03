package com.bluemobi.cnpc.network.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gaoyn on 2015/9/9.
 */
public class MakeOutInvoiceBean implements Serializable {
    private String currentpage;  //当前页数
    private String pageTime;  //分页查询时间
    private String totalnum;  //总条数
    private String totalpage;  //总页数

    private List<MakeOutInvoiceInfo> info;

    public String getCurrentpage() {
        int i = Integer.parseInt(currentpage)-1;
        return String.valueOf(i);
    }

    public void setCurrentpage(String currentpage) {
        this.currentpage = currentpage;
    }

    public String getPageTime() {
        return pageTime;
    }

    public void setPageTime(String pageTime) {
        this.pageTime = pageTime;
    }

    public String getTotalnum() {
        return totalnum;
    }

    public void setTotalnum(String totalnum) {
        this.totalnum = totalnum;
    }

    public String getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(String totalpage) {
        this.totalpage = totalpage;
    }

    public List<MakeOutInvoiceInfo> getInfo() {
        return info;
    }

    public void setInfo(List<MakeOutInvoiceInfo> info) {
        this.info = info;
    }
}
