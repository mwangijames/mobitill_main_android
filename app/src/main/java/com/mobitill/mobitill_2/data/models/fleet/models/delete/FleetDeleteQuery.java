package com.mobitill.mobitill_2.data.models.fleet.models.delete;

/**
 * Created by james on 10/6/2016.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class FleetDeleteQuery {

    @SerializedName("params")
    @Expose
    private FleetDeleteParams params;

    /**
     *
     * @return
     * The params
     */
    public FleetDeleteParams getParams() {
        return params;
    }

    /**
     *
     * @param params
     * The params
     */
    public void setParams(FleetDeleteParams params) {
        this.params = params;
    }
}
