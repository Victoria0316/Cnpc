package com.bluemobi.cnpc.network.response;

import com.bluemobi.cnpc.network.CnpcHttpResponse;

import java.util.ArrayList;

/**
 * Created by liufy on 2015/8/25.
 */
public class NearnestGasStationResponse extends CnpcHttpResponse
{

    public NearnestGasStationData data;

    public static class NearnestGasStationData
    {
        private String currentpage;// 当前处在的页数索引
        public String pageTime;// 分页查询时间
        public String totalnum;//所有数据的总条数
        public String totalpage;//所有数据的总页数

        public String getCurrentpage() {
            return currentpage;
        }

        public ArrayList<NearnestGasStationInfo> info = new ArrayList<NearnestGasStationInfo>();

    }

    public static class NearnestGasStationInfo
    {
        public String deptAddress;// "加油站地址",
        public String deptCellphone;// "联系电话",
        public String cooperationState;// "合作状态（0，非合作，1合作）",
        public String deptName;// "加油站名称",
        public String detail;// "细节",
        public String distance;// "距离(米)",
        public String id;// "加油站ID",
        public String deptLatitude;// "纬度值",
        public String deptLongitude;// "经度值",
        public String uid;// "poi的唯一标示"
        public String streetId;//"街道ID"
        public ArrayList<NearnestGasStationProductInfo> productInfos = new ArrayList<NearnestGasStationProductInfo>();
    }

    public static class NearnestGasStationProductInfo
    {
        public String customerPrice;//油品单价",
        public String id;//油品ID"
        public String productName;//油品名称"
    }


}
