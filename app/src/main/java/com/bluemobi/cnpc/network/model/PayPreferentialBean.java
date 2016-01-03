package com.bluemobi.cnpc.network.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gaoyn on 2015/8/20.
 */
public class PayPreferentialBean implements Serializable {

    private String totalnum; // 所有数据的总条数
    private String totalpage;// 所有数据的总页数
    private String currentpage;// 当前处在的页数索引
    private String pageTime;// 分页查询时间

    public String getCurrentpage() {
        int i = Integer.parseInt(currentpage)-1;
        return String.valueOf(i);
    }

    List<PayPreferentialInfo> info;

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


    public void setCurrentpage(String currentpage) {
        this.currentpage = currentpage;
    }

    public String getPageTime() {
        return pageTime;
    }

    public void setPageTime(String pageTime) {
        this.pageTime = pageTime;
    }

    public List<PayPreferentialInfo> getInfo() {
        return info;
    }

    public void setInfo(List<PayPreferentialInfo> info) {
        this.info = info;
    }
}
