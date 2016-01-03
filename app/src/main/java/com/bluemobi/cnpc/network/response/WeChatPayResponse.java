package com.bluemobi.cnpc.network.response;

import com.bluemobi.cnpc.network.CnpcHttpResponse;
import com.bluemobi.cnpc.network.model.WeChatPayBean;

/**
 * Created by gaoyn on 2015/10/9.
 */
public class WeChatPayResponse extends CnpcHttpResponse{

    private WeChatPayBean data;

    public WeChatPayBean getData() {
        return data;
    }

    public void setData(WeChatPayBean data) {
        this.data = data;
    }
}
