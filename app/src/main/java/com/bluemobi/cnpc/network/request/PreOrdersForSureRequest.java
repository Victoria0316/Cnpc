package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.PreOrdersForSureResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyn on 2015/8/26.
 *
 * 查询预购活品信息--P5_2预购升数确定
 */
public class PreOrdersForSureRequest extends CnpcHttpJsonRequest<PreOrdersForSureResponse> {

    private static final String APIPATH = "mobi/productinfo/getOilInfo";

    private String brandid;//油品信息ID
    private String type;//固定值“02”

    public PreOrdersForSureRequest(Response.Listener<PreOrdersForSureResponse> listener, Response.ErrorListener errorListener) {
        super(Method.POST, APIPATH, listener, errorListener);
    }

    @Override
    public String GetApiPath() {
        return APIPATH;
    }

    @Override
    public Map<String, String> GetParameters() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("brandid", brandid);
        map.put("type", "02");
        return map;
    }

    @Override
    public Class<PreOrdersForSureResponse> getResponseClass() {
        return PreOrdersForSureResponse.class;
    }

    public String getBrandid() {
        return brandid;
    }

    public void setBrandid(String brandid) {
        this.brandid = brandid;
    }
}
