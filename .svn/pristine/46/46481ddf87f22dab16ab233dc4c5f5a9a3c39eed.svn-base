package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.GasFillingCardResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyn on 2015/9/1.
 *
 * P10_8加油卡/P10_8_1编辑-分页查询已绑定的油卡
 */
public class GasFillingCardRequest extends CnpcHttpJsonRequest<GasFillingCardResponse>{

    private static final String APIPATH = "mobi/fuelcardinfo/findByPage";

    private String userId ; //用户id
    private String currentnum ; //每页条数
    private String currentpage; // 当前页数 从0开始 0为第1页


    public GasFillingCardRequest(Response.Listener<GasFillingCardResponse> listener, Response.ErrorListener errorListener) {
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
    public Class<GasFillingCardResponse> getResponseClass() {
        return GasFillingCardResponse.class;
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
