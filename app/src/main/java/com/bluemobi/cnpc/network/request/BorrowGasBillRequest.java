package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.BorrowGasBillResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyn on 2015/8/31.
 *
 *  P10_3预购油/P10_4借用油-根据条件查询油品订单
 */
public class BorrowGasBillRequest extends CnpcHttpJsonRequest<BorrowGasBillResponse>{

    private static final String APIPATH = "mobi/oilfuturesorder/findOilOrdersByType";

    private String userId ;//	用户登录时的ID
    private String type ;//初始化类型 1预购油 2借用油
    private String rYear ; //	有效年份
    private String oilId ; //	油品id

    public BorrowGasBillRequest(Response.Listener<BorrowGasBillResponse> listener, Response.ErrorListener errorListener) {
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
        map.put("type", type);
        map.put("rYear", rYear == null? null:rYear);
        map.put("oilId", oilId == null? null:oilId);
        return map;
    }

    @Override
    public Class<BorrowGasBillResponse> getResponseClass() {
        return BorrowGasBillResponse.class;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getrYear() {
        return rYear;
    }

    public void setrYear(String rYear) {
        this.rYear = rYear;
    }

    public String getOilId() {
        return oilId;
    }

    public void setOilId(String oilId) {
        this.oilId = oilId;
    }
}
