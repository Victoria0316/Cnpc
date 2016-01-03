package com.bluemobi.cnpc.network.response;

import com.android.volley.Request;
import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpResponse;

import java.util.ArrayList;

/**
 * Created by liufy on 2015/8/24.
 */
public class CityListResponse extends CnpcHttpResponse {


    public CityListData data;

    public static class CityListData
    {
        public String cityStatus;//城市状态0不需要更新1需要更新,
        public String cityVersion;//城市版本
        public String hotCityStatus;//热门城市状态0不需要更新1需要更新,
        public String hotCityVersion;//热门城市版本
        public ArrayList<CityListDto>  divisionInfoDTOs = new ArrayList<CityListDto>();
        public ArrayList<HotCityInfoDTO>  hotCityInfoDTOs  = new ArrayList<HotCityInfoDTO>();
    }




    public static class CityListDto
    {
        public String divisionJp;// 行政区划字母码,
        public String divisionName;// 行政区划名称全称,
        public String divisionNameJp;// 名称简称,
        public String divisionType;// 区划类型：0国家1省份2地市3区县,
        public String id;// 主键,
        public String pid;// 父ID
    }


    public static class HotCityInfoDTO
    {
        public String id;//主键,
        public String pid;//父ID,
        public String  divisionName;//行政区划名称全称,
    }


}
