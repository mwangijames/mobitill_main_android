package com.mobitill.mobitill_2.data.models.cashiers.models;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class CashiersParams {

    @SerializedName("fetch")
    @Expose
    private CashiersFetch fetch;

    /**
     *
     * @return
     * The fetch
     */
    public CashiersFetch getFetch() {
        return fetch;
    }

    /**
     *
     * @param fetch
     * The fetch
     */
    public void setFetch(CashiersFetch fetch) {
        this.fetch = fetch;
    }

}