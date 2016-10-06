package com.mobitill.mobitill_2.data.models.cashiers.models;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class CashiersQuery {

    @SerializedName("params")
    @Expose
    private CashiersParams params;

    /**
     *
     * @return
     * The params
     */
    public CashiersParams getParams() {
        return params;
    }

    /**
     *
     * @param params
     * The params
     */
    public void setParams(CashiersParams params) {
        this.params = params;
    }

}