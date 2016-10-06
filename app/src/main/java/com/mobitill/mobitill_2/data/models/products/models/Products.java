package com.mobitill.mobitill_2.data.models.products.models;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Products {

    @SerializedName("data")
    @Expose
    private List<Product> data = new ArrayList<Product>();

    /**
     *
     * @return
     * The data
     */
    public List<Product> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<Product> data) {
        this.data = data;
    }

}