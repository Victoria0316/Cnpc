package com.bluemobi.cnpc.network.response;

import com.bluemobi.cnpc.network.CnpcHttpResponse;

/**
 * Created by liufy on 2015/8/19.
 * P2登录
 */
public class LoginResponse extends CnpcHttpResponse {


    public LoginData data;

    public static class LoginData
    {
        public String userId;// 用户ID,
        public String userName;// 用户登录名/手机号,
        public String nickName;// 用户昵称,
        public String headPicUrl;// 头像地址,
        public String ssid;// 登陆唯一标识,
        public String activateStatus;// 用户状态：0未激活，1已激活,
    }
}
