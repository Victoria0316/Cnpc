package com.bluemobi.cnpc.network.response;

import com.bluemobi.cnpc.network.CnpcHttpResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liufy on 2015/8/19.
 * P2_1注册_用户注册
 */
public class SunyardOilResponse extends CnpcHttpResponse
{
    public ArrayList<SunyardOilDto> data = new ArrayList<SunyardOilDto>();

    public static class SunyardOilDto
    {
        public String id;//"油品ID",
        public String productName;//"油品名称"
    }
}
