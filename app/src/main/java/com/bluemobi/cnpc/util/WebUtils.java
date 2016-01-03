package com.bluemobi.cnpc.util;


import com.android.volley.Request;
import com.bluemobi.cnpc.app.VolleyManager;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class WebUtils
{
    public static String buildQuery(Map<String, String> params)
    {
        if (params == null || params.isEmpty())
        {
            return null;
        }
        StringBuilder query = new StringBuilder();
        Set<Entry<String, String>> entries = params.entrySet();
        boolean hasParam = false;

        for (Entry<String, String> entry : entries)
        {
            String name = entry.getKey();
            String value = entry.getValue();
            if (StringUtils.areNotEmpty(name, value))
            {
                if (hasParam)
                {
                    query.append("&");
                }
                else
                {
                    hasParam = true;
                }
                try
                {
                    query.append(name).append("=")
                            .append(URLEncoder.encode(value, "utf-8"));
                }
                catch (UnsupportedEncodingException e)
                {
                    e.printStackTrace();
                }
            }
        }

        return query.toString();
    }

    public static void doPost(Request<?> request)
    {
        VolleyManager.addToQueue(request);
    }
}
