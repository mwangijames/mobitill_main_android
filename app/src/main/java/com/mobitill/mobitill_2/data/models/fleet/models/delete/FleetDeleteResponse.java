package com.mobitill.mobitill_2.data.models.fleet.models.delete;

/**
 * Created by james on 10/6/2016.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class FleetDeleteResponse {

    @SerializedName("data")
    @Expose
    private FleetDeleteResponseData data;

    /**
     *
     * @return
     * The data
     */
    public FleetDeleteResponseData getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(FleetDeleteResponseData data) {
        this.data = data;
    }

}