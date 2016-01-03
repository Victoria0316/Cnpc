package com.bluemobi.cnpc.network.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gaoyn on 2015/8/26.
 */
public class ProductSelectionBean implements Serializable {

    private String totalnum; //所有数据的总条数
    private String totalpage;//所有数据的总页数
    private String currentpage;//当前处在的页数索引
    private List<oilInfoDTOInfo> info;

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

    public String getCurrentpage() {
        return currentpage;
    }

    public void setCurrentpage(String currentpage) {
        this.currentpage = currentpage;
    }

    public List<oilInfoDTOInfo> getInfo() {
        return info;
    }

    public void setInfo(List<oilInfoDTOInfo> info) {
        this.info = info;
    }
}
