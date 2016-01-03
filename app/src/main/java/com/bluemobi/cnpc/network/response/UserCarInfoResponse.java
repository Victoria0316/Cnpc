package com.bluemobi.cnpc.network.response;

import com.bluemobi.cnpc.network.CnpcHttpResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liufy on 2015/8/30.
 */
public class UserCarInfoResponse extends CnpcHttpResponse {

    public List<UserCarInfoData> data = new ArrayList<UserCarInfoData>();

    public static class UserCarInfoData
    {
        public String carPlate;// 车牌号,
        public String id;// 主键id
    }

}
