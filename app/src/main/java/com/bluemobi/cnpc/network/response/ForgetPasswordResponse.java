package com.bluemobi.cnpc.network.response;

import com.bluemobi.cnpc.network.CnpcHttpResponse;

/**
 * Created by gaoyn on 2015/8/20.
 */
public class ForgetPasswordResponse extends CnpcHttpResponse {

    public ForgetPasswordBean data;

    public static class ForgetPasswordBean
    {
        public String value;
    }

}
