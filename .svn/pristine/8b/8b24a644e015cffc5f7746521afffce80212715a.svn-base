package com.bluemobi.cnpc.network.response;

import com.bluemobi.cnpc.network.CnpcHttpResponse;

import java.util.ArrayList;

/**
 * Created by liufy on 2015/8/25.
 */
public class ListGasStationDetailResponse extends CnpcHttpResponse {

    public GasStationListDetail data;

    public static class GasStationListDetail {
        public String id;//加油站主键id,
        public String deptName;//加油站名称,
        public String collectionNum;//收藏数,
        public String praiseNum;//点赞数,
        public String deptContent;//加油站介绍,
        public String gCoverImgPath;//加油站详情封面图片,
        public String articleContent;//促销信息,
        public String collectStatus;//收藏状态0已收藏1未收藏,
        public String collectid;//收藏信息主键id,
        public String praiseStatus;//赞状态0已赞1未赞,
        public String praiseid;//赞信息主键id,

        public ArrayList<GasStationDetailInfo> productInfos = new ArrayList<GasStationDetailInfo>();

    }

    public static class GasStationDetailInfo {
        public String id;//油品主键id
        public String productName;//油品名称
        public String customerPrice;//销售价格
        public String vipCustomerPrice;//vip销售价格
    }


}
