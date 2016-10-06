package com.mobitill.mobitill_2.data.models.reports.models;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Query {

    @SerializedName("params")
    @Expose
    private Params params;

    /**
     *
     * @return
     * The params
     */
    public Params getParams() {
        return params;
    }

    /**
     *
     * @param params
     * The params
     */
    public void setParams(Params params) {
        this.params = params;
    }

}
