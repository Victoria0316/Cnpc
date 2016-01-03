package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.PayPreferentialResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyn on 2015/8/20.
 */
public class PayPreferentialRequest extends CnpcHttpJsonRequest<PayPreferentialResponse>{

    private static final String APIPATH = "mobi/rechargepackagesinfo/getRechargePackages";

    private String currentnum ;//	每页条数
    private String currentpage ; //当前页数，页数从0开始
    private String pageTime; //分页查询时间

    public PayPreferentialRequest(Response.Listener<PayPreferentialResponse> listener, Response.ErrorListener errorListener) {
        super(Method.POST, APIPATH, listener, errorListener);
    }

    @Override
    public String GetApiPath() {
        return APIPATH;
    }

    @Override
    public Map<String, String> GetParameters() {
        Map<String,String> map = new HashMap<String,String>();
        map.put("currentnum",currentnum);
        map.put("currentpage",currentpage);
        return map;
    }

    @Override
    public Class<PayPreferentialResponse> getResponseClass() {
        return PayPreferentialResponse.class;
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
}
