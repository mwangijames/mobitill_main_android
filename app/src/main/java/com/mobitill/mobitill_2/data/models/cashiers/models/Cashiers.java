package com.mobitill.mobitill_2.data.models.cashiers.models;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Cashiers {

    @SerializedName("data")
    @Expose
    private List<Cashier> data = new ArrayList<Cashier>();

    /**
     *
     * @return
     * The data
     */
    public List<Cashier> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<Cashier> data) {
        this.data = data;
    }

}