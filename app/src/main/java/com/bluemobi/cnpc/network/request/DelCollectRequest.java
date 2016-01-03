package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.PraiseResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liufy on 2015/8/19.
 * P3首页 接口用途： 	查询最近的加油站信息
 */
public class DelCollectRequest extends CnpcHttpJsonRequest<PraiseResponse> {

    private static final String APIPATH = "mobi/deptinfo/delcollect";


    private String id	;//	id	string	是	收藏信息主键id

    public DelCollectRequest(Response.Listener<PraiseResponse> listener,
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
        map.put("id", id);
        return map;
    }

    @Override
    public Class getResponseClass() {
        return PraiseResponse.class;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
