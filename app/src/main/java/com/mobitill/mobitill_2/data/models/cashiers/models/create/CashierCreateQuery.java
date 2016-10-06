package com.mobitill.mobitill_2.data.models.cashiers.models.create;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class CashierCreateQuery {

    @SerializedName("params")
    @Expose
    private CashierCreateParams params;

    /**
     *
     * @return
     * The params
     */
    public CashierCreateParams getParams() {
        return params;
    }

    /**
     *
     * @param params
     * The params
     */
    public void setParams(CashierCreateParams params) {
        this.params = params;
    }

}