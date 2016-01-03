package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.app.CnpcApplication;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.BonusListResponse;
import com.bluemobi.cnpc.network.response.CollectionListResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangzhijun on 2015/9/13.
 */
public class CollectionListRequest extends CnpcHttpJsonRequest<CollectionListResponse> {

    private static final String APIPATH = "mobi/collectioninfo/findByPage";

    private String currentnum;//	string	是	每页条数
    private String currentpage;//	string	是	当前页数 从0开始 0为第1页
    private String pageTime;//	string	是	分页查询时间 第一页不传 查询第一页数据时返回pageTime

    public CollectionListRequest(Response.Listener<CollectionListResponse> listener, Response.ErrorListener errorListener) {
        super(Method.POST, APIPATH, listener, errorListener);
    }

    @Override
    public String GetApiPath() {
        return APIPATH;
    }

    @Override
    public Map<String, String> GetParameters() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("currentnum", currentnum);
        map.put("currentpage", currentpage);
//        map.put("pageTime", String.valueOf(System.currentTimeMillis()));
        map.put("userId", CnpcApplication.getInstance().getmData().userId);
        return map;
    }

    @Override
    public Class<CollectionListResponse> getResponseClass() {
        return CollectionListResponse.class;
    }

    public String getCurrentnum() {
        return currentnum;
    }

    public void setCurrentnum(String currentnum) {
        this.currentnum = currentnum;
    }

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

}
