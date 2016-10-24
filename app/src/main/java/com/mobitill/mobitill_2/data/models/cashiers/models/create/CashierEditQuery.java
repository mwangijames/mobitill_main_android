package com.mobitill.mobitill_2.data.models.cashiers.models.create;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class CashierEditQuery {

    @SerializedName("params")
    @Expose
    private CashierEditParams params;

    /**
     *
     * @return
     * The params
     */
    public CashierEditParams getParams() {
        return params;
    }

    /**
     *
     * @param params
     * The params
     */
    public void setParams(CashierEditParams params) {
        this.params = params;
    }

}