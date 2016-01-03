package com.bluemobi.cnpc.network;


import com.bluemobi.cnpc.network.util.DefaultTranslateErrorToCn;
import com.bluemobi.cnpc.network.util.TranslateErrorToCn;

public abstract class CnpcHttpResponse implements CnpcHttpError {
    private int status;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private String msg;

    private TranslateErrorToCn translateErrorToCn;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getContent() {
        return translateErrorToCn(status);
    }

    public void setContent(String content) {
        this.msg = content;
    }

    @Override
    public String translateErrorToCn(int errorCode) {
        if(translateErrorToCn == null){
            translateErrorToCn = new DefaultTranslateErrorToCn();
        }
        return translateErrorToCn.translateErrorToCn(errorCode);
    }
}

