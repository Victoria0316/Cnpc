package com.bluemobi.cnpc.network.response;

import com.bluemobi.cnpc.network.CnpcHttpResponse;

/**
 * Created by gaoyn on 2015/8/20.
 */
public class OilWebViewDetailResponse extends CnpcHttpResponse{

    public OilWebViewDetailBean data;

    public OilWebViewDetailBean getData() {
        return data;
    }

    public void setData(OilWebViewDetailBean data) {
        this.data = data;
    }





    public class OilWebViewDetailBean {
        private String introduceInfo;

        public String getIntroduceInfo() {
            return introduceInfo;
        }

        public void setIntroduceInfo(String introduceInfo) {
            this.introduceInfo = introduceInfo;
        }
    }


}
