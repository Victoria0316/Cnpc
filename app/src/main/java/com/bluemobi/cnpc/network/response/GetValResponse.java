package com.bluemobi.cnpc.network.response;

import com.bluemobi.cnpc.network.CnpcHttpResponse;

/**
 * Created by liufy on 2015/8/19.
 * P2_1注册_ 获取验证码
 */
public class GetValResponse extends CnpcHttpResponse {

    public ValDateGet data;

    public static class ValDateGet
    {
          public String value;
    }
}
