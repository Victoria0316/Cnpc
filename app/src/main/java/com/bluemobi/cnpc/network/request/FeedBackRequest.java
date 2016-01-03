package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.FeedBackResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyn on 2015/9/9.
 */
public class FeedBackRequest extends CnpcHttpJsonRequest<FeedBackResponse>{

    private static final String APIPATH = "mobi/feedback/save";

    private String userId ; //用户登录时的ID
    private String title ; //标题
    private String content ; //内容

    public FeedBackRequest(Response.Listener<FeedBackResponse> listener, Response.ErrorListener errorListener) {
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
        map.put("title", title);
        map.put("content", content);
        return map;
    }

    @Override
    public Class<FeedBackResponse> getResponseClass() {
        return FeedBackResponse.class;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
