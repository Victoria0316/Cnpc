package com.bluemobi.cnpc.network.response;

import com.bluemobi.cnpc.network.CnpcHttpResponse;

/**
 * Created by liufy on 2015/8/19.
 * P2_1注册_用户注册
 */
public class RegisterResponse extends CnpcHttpResponse {

    public RegisterData data;

    public static class RegisterData
    {
        public String id;// "用户ID"
    }


}
