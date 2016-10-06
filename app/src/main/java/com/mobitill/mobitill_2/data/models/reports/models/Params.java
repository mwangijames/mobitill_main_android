package com.mobitill.mobitill_2.data.models.reports.models;


import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Params {

    @SerializedName("fetch")
    @Expose
    private Fetch fetch;

    /**
     *
     * @return
     * The fetch
     */
    public Fetch getFetch() {
        return fetch;
    }

    /**
     *
     * @param fetch
     * The fetch
     */
    public void setFetch(Fetch fetch) {
        this.fetch = fetch;
    }

}
