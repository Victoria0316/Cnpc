package com.bluemobi.cnpc.network.response;

import com.bluemobi.cnpc.network.CnpcHttpResponse;
import com.bluemobi.cnpc.network.model.PayPreferentialBean;

/**
 * Created by gaoyn on 2015/8/20.
 */
public class PayPreferentialResponse extends CnpcHttpResponse{

    private PayPreferentialBean data;

    public PayPreferentialBean getData() {
        return data;
    }

    public void setData(PayPreferentialBean data) {
        this.data = data;
    }
}
