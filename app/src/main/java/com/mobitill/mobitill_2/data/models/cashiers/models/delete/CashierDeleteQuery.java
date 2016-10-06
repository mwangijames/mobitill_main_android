package com.mobitill.mobitill_2.data.models.cashiers.models.delete;

/**
 * Created by james on 10/6/2016.
 */

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class CashierDeleteQuery {

    @SerializedName("params")
    @Expose
    private CashierDeleteParams params;

    /**
     *
     * @return
     * The params
     */
    public CashierDeleteParams getParams() {
        return params;
    }

    /**
     *
     * @param params
     * The params
     */
    public void setParams(CashierDeleteParams params) {
        this.params = params;
    }
}
