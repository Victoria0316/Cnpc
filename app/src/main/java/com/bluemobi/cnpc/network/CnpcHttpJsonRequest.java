package com.bluemobi.cnpc.network;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.bluemobi.cnpc.app.CnpcApplication;
import com.bluemobi.cnpc.base.utils.Logger;
import com.bluemobi.cnpc.network.exception.CustomResponseError;
import com.bluemobi.cnpc.network.exception.TokenInvalid;
import com.bluemobi.cnpc.network.util.DefaultTranslateErrorToCn;
import com.bluemobi.cnpc.network.util.TranslateErrorToCn;
import com.bluemobi.cnpc.util.Constants;
import com.bluemobi.cnpc.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public abstract class CnpcHttpJsonRequest<T extends CnpcHttpResponse> extends
        JsonRequest<T> implements CnpcHttpBase<T>, CnpcHttpError {

    private static final String PROTOCOL_CHARSET = "utf-8";

    private final Gson mGson;

    private final Response.Listener<T> mListener;

    private String mRequestBody;

    private static final int TOKENINVALID = 5;

    private static final int RESPONSE_SUCCESS = 0;

    private boolean showToast = true;

    private boolean handleCustomErr = true;

    protected RetryPolicy retryPolicy;

    private TranslateErrorToCn translateErrorToCn;

    private NetWorkResponseListener netWorkResponseListener;


    public CnpcHttpJsonRequest(String partUrl, Response.Listener<T> listener,
                               Response.ErrorListener errorListener) {
        this(Method.POST, Constants.SERVER_URL + partUrl, null, listener,
                errorListener, false);
    }

    public CnpcHttpJsonRequest(String partUrl, Response.Listener<T> listener,
                               Response.ErrorListener errorListener, boolean noRetryPolicy) {
        this(Method.POST, Constants.SERVER_URL + partUrl, null, listener,
                errorListener, noRetryPolicy);
    }

    public CnpcHttpJsonRequest(int method, String partUrl,
                               Response.Listener<T> listener, Response.ErrorListener errorListener) {
        this(method, Constants.SERVER_URL + partUrl, null, listener,
                errorListener, false);
    }

    public CnpcHttpJsonRequest(int method, String partUrl,
                               Response.Listener<T> listener, Response.ErrorListener errorListener, boolean noRetryPolicy) {
        this(method, Constants.SERVER_URL + partUrl, null, listener,
                errorListener, noRetryPolicy);
    }
    /**
     *
     * @param method
     * @param url
     * @param requestBody
     * @param listener
     * @param errorListener
     * @param noRetryPolicy
     */
    public CnpcHttpJsonRequest(int method, String url, String requestBody,
                               Response.Listener<T> listener, Response.ErrorListener errorListener, boolean noRetryPolicy) {
        super(method, url , null, listener,
                errorListener);
        if(noRetryPolicy){
            retryPolicy = new DefaultRetryPolicy(mCurrentTimeoutMs, 0, mMaxNumRetries);
        }else{
            retryPolicy = new DefaultRetryPolicy(mCurrentTimeoutMs, 1, mMaxNumRetries);
        }
        setRetryPolicy(retryPolicy);
        translateErrorToCn = new DefaultTranslateErrorToCn();
        this.mListener = listener;
        mGson = new Gson();
    }

    public String requestUrl() {
        return Constants.SERVER_URL + GetApiPath();
    }

    abstract public String GetApiPath();


    abstract public Map<String, String> GetParameters();


    abstract public Class<T> getResponseClass();

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return GetParameters();
    }


    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }


    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
           /* long endTime = new Date().getTime();
            Logger.d("XtbdHttpRequestTime", GetApiPath()
                    + "-->"
                    + (endTime - XtbdApplication.requestTime.get(GetApiPath())
                    .longValue()) + "ms");*/
            // Logger.d("XtbdHttpRequest",requestUrl() + "?" +
            // WebUtils.buildQuery(GetParameters()));
            Logger.d("CnpcHttpJsonRequest", requestUrl() + "?"
                    + new String(getBody(), "utf-8"));
            String json = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));

            Logger.dl("CnpcHttpJsonRequest", StringUtils.unicodeToChinese(json));

			/*//todo remove later
			if(this instanceof BiddingDetailRequest){
				return Response.error(new TimeoutError());
				try {s

					JSONObject obj = new JSONObject(json);
					obj.put("status", "3");
					return doAnalyse(obj.toString(), response);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}*/
            return doAnalyse(json, response);
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

    private Response<T> doAnalyse(String json, NetworkResponse response) {
        JSONObject obj = null;
        try {
            obj = new JSONObject(json);
            if (obj.has("status") && TOKENINVALID == obj.getInt("status")) {// �û���֤ʧЧ
                return Response.error(new TokenInvalid());
            } else if (handleCustomErr && obj.has("status")
                    && RESPONSE_SUCCESS != obj.getInt("status")) {
                return Response.error(new CustomResponseError(
                        obj.has("msg") ? obj.getString("msg") : "服务器繁忙", showToast,obj.getInt("status")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
        return Response.success(mGson.fromJson(json, getResponseClass()),
                HttpHeaderParser.parseCacheHeaders(response));
    }

    protected Map<String, Object> getExtParams(){
        return null;
    }

    @Override
    public byte[] getBody() {
        try {
            Map<String, String> map = GetParameters();
            if (CnpcApplication.getInstance().getmData() != null) {
                if (map != null) {
                    if (StringUtils.isNotEmpty(CnpcApplication.getInstance().getmData().ssid)) {
                        map.put("ssid", CnpcApplication.getInstance().getmData().ssid);
                        map.put("checkUserId", CnpcApplication.getInstance().getmData().userId);
                    }
                }
            }else {
                map.put("pass", "n");
            }
            map.put("pageTime",String.valueOf(CnpcApplication.getInstance().getPageTime()));
            JSONObject obj = new JSONObject(map);
            com.google.gson.JsonObject gsonObj = new com.google.gson.JsonObject();
            Map<String, Object> extMap = getExtParams();
            if(extMap == null){
                extMap = new HashMap<String, Object>();
            }
            for (String key : map.keySet()) {
                System.out.println("key= "+ key + " and value= " + map.get(key));
                extMap.put(key, map.get(key));
            }
            Gson gson = new Gson();
            mRequestBody = gson.toJson(extMap);
            return mRequestBody == null ? null : mRequestBody
                    .getBytes(PROTOCOL_CHARSET);
        } catch (UnsupportedEncodingException uee) {
            VolleyLog
                    .wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                            mRequestBody, PROTOCOL_CHARSET);
            return null;
        }
    }

    @Override
    public String translateErrorToCn(int errorCode) {
        if(translateErrorToCn != null){
            translateErrorToCn = new DefaultTranslateErrorToCn();
        }
        return translateErrorToCn.translateErrorToCn(errorCode);
    }

    public boolean isShowToast() {
        return showToast;
    }

    public void setShowToast(boolean showToast) {
        this.showToast = showToast;
    }

    public boolean isHandleCustomErr() {
        return handleCustomErr;
    }

    public void setHandleCustomErr(boolean handleCustomErr) {
        this.handleCustomErr = handleCustomErr;
    }

    public TranslateErrorToCn getTranslateErrorToCn() {
        return translateErrorToCn;
    }
    public void setTranslateErrorToCn(TranslateErrorToCn translateErrorToCn) {
        this.translateErrorToCn = translateErrorToCn;
    }

    public NetWorkResponseListener getNetWorkResponseListener() {
        return netWorkResponseListener;
    }

    public void setNetWorkResponseListener(NetWorkResponseListener netWorkResponseListener) {
        this.netWorkResponseListener = netWorkResponseListener;
    }

}