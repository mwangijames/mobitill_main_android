package com.mobitill.mobitill_2.data.models.fleet.models.create;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class FleetCreateResponse {

    @SerializedName("data")
    @Expose
    private FleetCreateResponseData data;

    /**
     *
     * @return
     * The data
     */
    public FleetCreateResponseData getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(FleetCreateResponseData data) {
        this.data = data;
    }

}