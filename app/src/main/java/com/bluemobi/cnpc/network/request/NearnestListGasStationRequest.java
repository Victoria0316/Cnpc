package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.NearNotBdGasStationResponse;
import com.bluemobi.cnpc.network.response.NearnestGasStationResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liufy on 2015/8/24.
 * P6加油支付
 */
public class NearnestListGasStationRequest extends CnpcHttpJsonRequest<NearNotBdGasStationResponse> {

    private static final String APIPATH = "mobi/gasstationinfo/nearestGasStationPage";

    private  String longitude	;//	是	坐标经度
    private  String latitude	;//	是	坐标纬度
    private  String currentnum	;//	是	每页条数
    private  String currentpage	;//	是	当前页数 页号从0开始
    private  String pageTime ;//	否	分页查询时间
    private  String  stationName;//string	否	加油站名称

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCurrentnum() {
        return currentnum;
    }

    public void setCurrentnum(String currentnum) {
        this.currentnum = currentnum;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getCurrentpage() {
        return currentpage;
    }

    public void setCurrentpage(String currentpage) {
        this.currentpage = currentpage;
    }


    public NearnestListGasStationRequest(Response.Listener<NearNotBdGasStationResponse> listener,
                                         Response.ErrorListener errorListener)
    {
        super(Method.POST, APIPATH, listener, errorListener);
    }

    @Override
    public String GetApiPath() {
        return APIPATH;
    }

    @Override
    public Map<String, String> GetParameters() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("longitude", longitude);
        map.put("latitude", latitude);
        map.put("currentnum", currentnum);
        map.put("currentpage", currentpage);
        map.put("pageTime", pageTime);
        map.put("stationName", stationName);

        return map;
    }

    @Override
    public Class<NearNotBdGasStationResponse> getResponseClass() {
        return NearNotBdGasStationResponse.class;
    }

    public String getPageTime() {
        return pageTime;
    }

    public void setPageTime(String pageTime) {
        this.pageTime = pageTime;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }
}
