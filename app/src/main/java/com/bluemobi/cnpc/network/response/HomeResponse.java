package com.bluemobi.cnpc.network.response;

import android.widget.ImageView;

import com.bluemobi.cnpc.network.CnpcHttpResponse;

import java.util.ArrayList;

/**
 * Created by liufy on 2015/8/24.
 */
public class HomeResponse extends CnpcHttpResponse {

    public HomeData data;

    public static class HomeData {

        public String deptName;// 加油站名称,
        public String id;// 加油站主键ID,
        public ArrayList<OilInfo> oilInfos = new ArrayList<OilInfo>();//
        public ArrayList<String> imgs = new ArrayList<String>();
    }

    public static class OilInfo {
        public String customerPrice;// 7.15,
        public String id;// "油品ID",
        public String productName;// "油品名称"
    }

}
