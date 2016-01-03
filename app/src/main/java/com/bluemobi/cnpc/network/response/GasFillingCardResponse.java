package com.bluemobi.cnpc.network.response;

import com.bluemobi.cnpc.network.CnpcHttpResponse;
import com.bluemobi.cnpc.network.model.GasFillingCardBean;

/**
 * Created by gaoyn on 2015/9/1.
 */
public class GasFillingCardResponse extends CnpcHttpResponse {

    private GasFillingCardBean data;

    public GasFillingCardBean getData() {
        return data;
    }

    public void setData(GasFillingCardBean data) {
        this.data = data;
    }
}
