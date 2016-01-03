package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.FoundDetailResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyn on 2015/8/25.
 *
 * 查看详情--P9_1 详情
 */
public class FoundDetailRequest extends CnpcHttpJsonRequest<FoundDetailResponse>{

    private static final String APIPATH = "mobi/articleinfo/DetailsArticleInfoPage";

    private String id; //主键id

    public FoundDetailRequest(Response.Listener<FoundDetailResponse> listener, Response.ErrorListener errorListener) {
        super(Method.POST, APIPATH, listener, errorListener);
    }

    @Override
    public String GetApiPath() {
        return APIPATH;
    }

    @Override
    public Map<String, String> GetParameters() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", id);
        return map;
    }

    @Override
    public Class<FoundDetailResponse> getResponseClass() {
        return FoundDetailResponse.class;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
