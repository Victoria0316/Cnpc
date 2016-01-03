package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.MakeOutInvoiceResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyn on 2015/9/9.
 *
 * 分页查询发票信息--P10_15 开票信息 - 分页查询发票信息
 */
public class MakeOutInvoiceRequest extends CnpcHttpJsonRequest<MakeOutInvoiceResponse>{

    private static final String APIPATH = "mobi/receiptinfo/findByPage";

    private String userId ; //	用户id
    private String currentnum ; //	每页条数
    private String currentpage ; //	当前页数 从0开始 0为第1页

    public MakeOutInvoiceRequest(Response.Listener<MakeOutInvoiceResponse> listener, Response.ErrorListener errorListener) {
        super(Method.POST, APIPATH, listener, errorListener);
    }

    @Override
    public String GetApiPath() {
        return APIPATH;
    }

    @Override
    public Map<String, String> GetParameters() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userId);
        map.put("currentnum", currentnum);
        map.put("currentpage", currentpage);
        return map;
    }

    @Override
    public Class<MakeOutInvoiceResponse> getResponseClass() {
        return MakeOutInvoiceResponse.class;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
