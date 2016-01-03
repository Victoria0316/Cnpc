package com.bluemobi.cnpc.network.response;

import com.bluemobi.cnpc.network.CnpcHttpResponse;
import com.bluemobi.cnpc.network.model.OilInBean;

/**
 * Created by gaoyn on 2015/8/29.
 */
public class OilInResponse extends CnpcHttpResponse{

    private OilInBean data;

    public OilInBean getData() {
        return data;
    }

    public void setData(OilInBean data) {
        this.data = data;
    }
}
