package com.bluemobi.cnpc.network.response;

import com.bluemobi.cnpc.network.CnpcHttpResponse;
import com.bluemobi.cnpc.network.model.SettlementBean;
import com.bluemobi.cnpc.network.model.WeChatPayBean;

/**
 * Created by gaoyn on 2015/8/24.
 */
public class SettlementResponse extends CnpcHttpResponse{

    private WeChatPayBean data;

    public WeChatPayBean getData() {
        return data;
    }

    public void setData(WeChatPayBean data) {
        this.data = data;
    }
}
