package com.bluemobi.cnpc.network.response;

import com.bluemobi.cnpc.network.CnpcHttpResponse;

import java.util.ArrayList;

/**
 * Created by liufy on 2015/8/24.
 */
public class CarAllListResponse extends CnpcHttpResponse {

    public ArrayList<AllCarDto> data = new ArrayList<AllCarDto>();

    public static class AllCarDto
    {
        public String brandName;// 品牌名称,
        public String frontLetter;// 品牌名称首字母,
        public String id;// 品牌id,
        public String isSale;// 是否在售(0是1否),
        public String level;// 品牌级别(1;//汽车品牌，2;//出售车型),
        public String parentBrandCode;// 父品牌id
    }





}
