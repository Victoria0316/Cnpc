package com.bluemobi.cnpc.network.model;

import java.util.ArrayList;

/**
 * Created by wangzhijun on 2015/9/13.
 */
public class CollectionListModel {
    private String currentpage;
    private String pageTime;//": "分页查询时间",
    private String totalnum;//": "总条数",
    private String totalpage;//": "总页数",
    private ArrayList<CollectionItem> info;

    public String getCurrentpage() {
        return currentpage;
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

    public ArrayList<CollectionItem> getInfo() {
        return info;
    }

    public void setInfo(ArrayList<CollectionItem> info) {
        this.info = info;
    }
}
