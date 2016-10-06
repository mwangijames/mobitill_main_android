package com.mobitill.mobitill_2.data.models.fleet.models;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Fleet {

    @SerializedName("data")
    @Expose
    private List<FleetItem> data = new ArrayList<FleetItem>();

    /**
     *
     * @return
     * The data
     */
    public List<FleetItem> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<FleetItem> data) {
        this.data = data;
    }

}