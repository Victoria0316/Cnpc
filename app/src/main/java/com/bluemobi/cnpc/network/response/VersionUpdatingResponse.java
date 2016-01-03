package com.bluemobi.cnpc.network.response;

import com.bluemobi.cnpc.network.CnpcHttpResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liufy on 2015/8/19.
 * P2登录
 */
public class VersionUpdatingResponse extends CnpcHttpResponse {


    public VersionUpdating data;


    public VersionUpdating getData() {
        return data;
    }

    public void setData(VersionUpdating data) {
        this.data = data;
    }

    public static class VersionUpdating
    {
        public String type;//"string"
        public String value;//"http://www.baidu.com"
    }
}
