package com.mobitill.mobitill_2.data.models.reports.models;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Fetch {

    @SerializedName("range")
    @Expose
    private List<Long> range = new ArrayList<Long>();
    @SerializedName("productid")
    @Expose
    private String productid;
    @SerializedName("cashier")
    @Expose
    private String cashier;
    @SerializedName("appid")
    @Expose
    private String appid;

    /**
     * @return The range
     */
    public List<Long> getRange() {
        return range;
    }

    /**
     * @param range The range
     */
    public void setRange(List<Long> range) {
        this.range = range;
    }

    /**
     * @return The productid
     */
    public String getProductid() {
        return productid;
    }

    /**
     * @param productid The productid
     */
    public void setProductid(String productid) {
        this.productid = productid;
    }

    /**
     * @return The cashier
     */
    public String getCashier() {
        return cashier;
    }

    /**
     * @param cashier The cashier
     */
    public void setCashier(String cashier) {
        this.cashier = cashier;
    }

    /**
     * @return The appid
     */
    public String getAppid() {
        return appid;
    }

    /**
     * @param appid The appid
     */
    public void setAppid(String appid) {
        this.appid = appid;
    }
}