package com.bluemobi.cnpc.network;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;

import com.bluemobi.cnpc.base.utils.Logger;
import com.bluemobi.cnpc.util.Constants;
import com.bluemobi.cnpc.util.StringUtils;
import com.bluemobi.cnpc.util.WebUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.Map;


public abstract class CnpcHttpRequest<T extends CnpcHttpResponse> extends
        Request<T> implements CnpcHttpBase<T>
{

    private final Gson mGson;

    private final Listener<T> mListener;

    private String redrctUrl;

    /**
     *
     * @param method
     *   int DEPRECATED_GET_OR_POST = -1;
     *   int GET = 0;
     *   int POST = 1;
     *   int PUT = 2;
     *   int DELETE = 3;
     *   int HEAD = 4;
     *   int OPTIONS = 5;
     *   int TRACE = 6;
     *   int PATCH = 7;
     * @param partUrl
     * @param listener
     * @param errorListener
     */
    public CnpcHttpRequest(int method, String partUrl, Listener<T> listener,
                           ErrorListener errorListener)
    {
        super(method, Constants.SERVER_URL + partUrl, errorListener);
        this.mListener = listener;
        mGson = new Gson();
    }

    public CnpcHttpRequest(int method, String serverUrl, String partUrl, Listener<T> listener,
                           ErrorListener errorListener)
    {
        super(method, serverUrl + partUrl, errorListener);
        this.mListener = listener;
        mGson = new Gson();
    }

    public String requestUrl()
    {
        return Constants.SERVER_URL + GetApiPath();
    }

    /**
     * part url
     *
     * @return
     */
    abstract public String GetApiPath();


    abstract public Map<String, String> GetParameters();


    abstract public Class<T> getResponseClass();


    @Override
    protected Map<String, String> getParams() throws AuthFailureError
    {
        return GetParameters();
    }

    @Override
    public byte[] getBody() throws AuthFailureError
    {
        return super.getBody();
    }


    @Override
    protected void deliverResponse(T response)
    {
        mListener.onResponse(response);
    }


    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response)
    {
        try
        {
            Logger.d("YbbHttpRequest",
                    requestUrl() + "?" + WebUtils.buildQuery(GetParameters()));
            String json = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            Logger.d("YbbHttpRequest", StringUtils.unicodeToChinese(json));
            return Response.success(mGson.fromJson(json, getResponseClass()),
                    HttpHeaderParser.parseCacheHeaders(response));
        }
        catch (UnsupportedEncodingException e)
        {
            return Response.error(new ParseError(e));
        }
        catch (JsonSyntaxException e)
        {
            return Response.error(new ParseError(e));
        }
    }
}
