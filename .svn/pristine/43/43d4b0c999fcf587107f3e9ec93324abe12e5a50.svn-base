package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.CityListResponse;
import com.bluemobi.cnpc.network.response.NearnestGasStationResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liufy on 2015/8/24.
 * 列出用户附近加油站信息列表--P8附近
 */
public class NearnestGasStationRequest extends CnpcHttpJsonRequest<NearnestGasStationResponse> {

    private static final String APIPATH = "mobi/gasstationinfo/nearestBaiduGasStationPage";

    private  String longitude	;//	是	坐标经度
    private  String latitude	;//	是	坐标纬度
    private  String currentnum	;//	是	每页条数
    private  String currentpage	;//	是	当前页数 页号从0开始
    private  String range;//	否	查询范围（米）默认是15000米
    private  String cityName ; // 	是 	地市名称
    private  String provinceName ; // 	是 	省份名称

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

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public NearnestGasStationRequest(Response.Listener<NearnestGasStationResponse> listener,
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
        map.put("range", range);
        map.put("cityName", cityName);
        map.put("provinceName", provinceName);
        return map;
    }

    @Override
    public Class<NearnestGasStationResponse> getResponseClass() {
        return NearnestGasStationResponse.class;
    }
}
