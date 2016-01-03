package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.ProductSelectionResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyn on 2015/8/26.
 *  	P5油品选择
 */
public class ProductSelectionRequest extends CnpcHttpJsonRequest<ProductSelectionResponse> {

    private static final String APIPATH = "mobi/productinfo/getSunyardOil";

    private String currentnum ;//	每页条数
    private String currentpage ; //当前页数，页数从0开始


    public ProductSelectionRequest(Response.Listener<ProductSelectionResponse> listener, Response.ErrorListener errorListener) {
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
    public Class<ProductSelectionResponse> getResponseClass() {
        return ProductSelectionResponse.class;
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
