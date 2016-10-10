package com.mobitill.mobitill_2.data.models.fleet.models.create;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class FleetCreateQuery {

    @SerializedName("params")
    @Expose
    private FleetCreateParams params;

    /**
     *
     * @return
     * The params
     */
    public FleetCreateParams getParams() {
        return params;
    }

    /**
     *
     * @param params
     * The params
     */
    public void setParams(FleetCreateParams params) {
        this.params = params;
    }

}