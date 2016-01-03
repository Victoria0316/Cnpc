package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.NearnestGasStationResponse;
import com.bluemobi.cnpc.network.response.UploadHeadResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liufy on 2015/8/24.
 * P10_1 个人信息-上传头像
 */
public class UploadHeadRequest extends CnpcHttpJsonRequest<UploadHeadResponse> {

    private static final String APIPATH = "mobi/customerinfo/uploadHead";
    private String userId;
    private String app	;//	调用app [android，ios]
    private String appKey	;//	app唯一标识[ebb，wnwj，cnpc]
    private String picArray	;//	base64图片字节流
    private String picType	;//	图片类型[jpg，jpeg，png]



    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getPicArray() {
        return picArray;
    }

    public void setPicArray(String picArray) {
        this.picArray = picArray;
    }

    public String getPicType() {
        return picType;
    }

    public void setPicType(String picType) {
        this.picType = picType;
    }

    public UploadHeadRequest(Response.Listener<UploadHeadResponse> listener,
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
        map.put("userId", userId);
        map.put("app", "android");
        map.put("appKey", "cnpc");
        map.put("picArray", picArray);
        map.put("picType", picType);
        return map;
    }

    @Override
    public Class<UploadHeadResponse> getResponseClass() {

        return UploadHeadResponse.class;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
