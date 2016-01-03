package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.app.CnpcApplication;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.CollectionDelResponse;
import com.bluemobi.cnpc.network.response.CollectionListResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangzhijun on 2015/9/13.
 */
public class CollectionDelRequest extends CnpcHttpJsonRequest<CollectionDelResponse> {

    private static final String APIPATH = "mobi/collectioninfo/del";

    private String id;//收藏信息主键id

    public CollectionDelRequest(Response.Listener<CollectionDelResponse> listener, Response.ErrorListener errorListener) {
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
//        map.put("userId", CnpcApplication.getInstance().getmData().userId);
        return map;
    }

    @Override
    public Class<CollectionDelResponse> getResponseClass() {
        return CollectionDelResponse.class;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
