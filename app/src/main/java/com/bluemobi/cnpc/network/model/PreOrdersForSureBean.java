package com.bluemobi.cnpc.network.model;

import java.io.Serializable;

/**
 * Created by gaoyn on 2015/8/26.
 */
public class PreOrdersForSureBean implements Serializable {

    private String productName;//油品名称
    private String customerPrice;//当前价格
    private String futuresMinPurchases;//最小预购量
    private String futuresMinIncremental;//递增最小量
    private String futuresBuyProcedure;//预购手续费

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCustomerPrice() {
        return customerPrice;
    }

    public void setCustomerPrice(String customerPrice) {
        this.customerPrice = customerPrice;
    }

    public String getFuturesMinPurchases() {
        return futuresMinPurchases;
    }

    public void setFuturesMinPurchases(String futuresMinPurchases) {
        this.futuresMinPurchases = futuresMinPurchases;
    }

    public String getFuturesMinIncremental() {
        return futuresMinIncremental;
    }

    public void setFuturesMinIncremental(String futuresMinIncremental) {
        this.futuresMinIncremental = futuresMinIncremental;
    }

    public String getFuturesBuyProcedure() {
        return futuresBuyProcedure;
    }

    public void setFuturesBuyProcedure(String futuresBuyProcedure) {
        this.futuresBuyProcedure = futuresBuyProcedure;
    }
}
