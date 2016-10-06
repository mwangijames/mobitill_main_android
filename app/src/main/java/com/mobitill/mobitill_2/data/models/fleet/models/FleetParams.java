package com.mobitill.mobitill_2.data.models.fleet.models;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class FleetParams {

    @SerializedName("fetch")
    @Expose
    private FleetFetch fetch;

    /**
     *
     * @return
     * The fetch
     */
    public FleetFetch getFetch() {
        return fetch;
    }

    /**
     *
     * @param fetch
     * The fetch
     */
    public void setFetch(FleetFetch fetch) {
        this.fetch = fetch;
    }

}
