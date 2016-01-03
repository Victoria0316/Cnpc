package com.bluemobi.cnpc.network.model;

import java.io.Serializable;

/**
 * Created by gaoyn on 2015/9/9.
 */
public class MakeOutInvoiceInfo implements Serializable {

    private String id;  //加油卡id
    private String receiptType;  //发票类型1个人2公司
    private String companyName;  //发票名头

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReceiptType() {
        return receiptType;
    }

    public void setReceiptType(String receiptType) {
        this.receiptType = receiptType;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
