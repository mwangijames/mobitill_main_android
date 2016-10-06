package com.mobitill.mobitill_2.data.models.fleet.models;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class FleetQuery {

    @SerializedName("params")
    @Expose
    private FleetParams params;

    /**
     *
     * @return
     * The params
     */
    public FleetParams getParams() {
        return params;
    }

    /**
     *
     * @param params
     * The params
     */
    public void setParams(FleetParams params) {
        this.params = params;
    }

}