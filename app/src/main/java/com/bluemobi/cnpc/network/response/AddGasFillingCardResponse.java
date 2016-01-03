package com.bluemobi.cnpc.network.response;

import com.bluemobi.cnpc.network.CnpcHttpResponse;
import com.bluemobi.cnpc.network.model.AddGasFillingCardBean;

import java.util.List;

/**
 * Created by gaoyn on 2015/9/1.
 */
public class AddGasFillingCardResponse extends CnpcHttpResponse {

    private List<AddGasFillingCardBean> data;

    public List<AddGasFillingCardBean> getData() {
        return data;
    }

    public void setData(List<AddGasFillingCardBean> data) {
        this.data = data;
    }
}
