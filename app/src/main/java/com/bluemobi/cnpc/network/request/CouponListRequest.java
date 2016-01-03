package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.app.CnpcApplication;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.BonusListResponse;
import com.bluemobi.cnpc.network.response.CouponListResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangzhijun on 2015/9/13.
 */
public class CouponListRequest extends CnpcHttpJsonRequest<CouponListResponse> {

    private static final String APIPATH = "mobi/customercoupon/findByPage";

    private String currentnum;//	string	是	每页条数
    private String currentpage;//	string	是	当前页数 从0开始 0为第1页
    private String pageTime;//	string	是	分页查询时间 第一页不传 查询第一页数据时返回pageTime
    private String type;//	string	优惠卷类型 1有效 2已用 3失效

    public CouponListRequest(Response.Listener<CouponListResponse> listener, Response.ErrorListener errorListener) {
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
        map.put("type", type);
        map.put("userId", CnpcApplication.getInstance().getmData().userId);
        return map;
    }

    @Override
    public Class<CouponListResponse> getResponseClass() {
        return CouponListResponse.class;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
