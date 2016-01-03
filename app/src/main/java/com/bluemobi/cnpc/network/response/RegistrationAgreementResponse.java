package com.bluemobi.cnpc.network.response;

import com.bluemobi.cnpc.network.CnpcHttpResponse;

/**
 * Created by gaoyn on 2015/8/20.
 */
public class RegistrationAgreementResponse extends CnpcHttpResponse{

    public RegistrationAgreementBean data;

    public RegistrationAgreementBean getData() {
        return data;
    }

    public void setData(RegistrationAgreementBean data) {
        this.data = data;
    }





    public class RegistrationAgreementBean {
        private String introduceInfo;

        public String getIntroduceInfo() {
            return introduceInfo;
        }

        public void setIntroduceInfo(String introduceInfo) {
            this.introduceInfo = introduceInfo;
        }
    }


}
