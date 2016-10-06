package com.mobitill.mobitill_2.data.models.cashiers.models.delete;

/**
 * Created by james on 10/6/2016.
 */

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class CashierDeleteResponse {

    @SerializedName("data")
    @Expose
    private CashierDeleteResponseData data;

    /**
     *
     * @return
     * The data
     */
    public CashierDeleteResponseData getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(CashierDeleteResponseData data) {
        this.data = data;
    }

}