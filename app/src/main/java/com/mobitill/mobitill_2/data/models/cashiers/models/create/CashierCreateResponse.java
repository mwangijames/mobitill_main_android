package com.mobitill.mobitill_2.data.models.cashiers.models.create;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class CashierCreateResponse {

    @SerializedName("data")
    @Expose
    private CashierCreateResponseData data;

    /**
     *
     * @return
     * The data
     */
    public CashierCreateResponseData getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(CashierCreateResponseData data) {
        this.data = data;
    }

}