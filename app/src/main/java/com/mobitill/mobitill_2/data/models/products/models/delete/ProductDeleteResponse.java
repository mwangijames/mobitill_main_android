package com.mobitill.mobitill_2.data.models.products.models.delete;

/**
 * Created by james on 10/6/2016.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class ProductDeleteResponse {

    @SerializedName("data")
    @Expose
    private ProductDeleteResponseData data;

    /**
     *
     * @return
     * The data
     */
    public ProductDeleteResponseData getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(ProductDeleteResponseData data) {
        this.data = data;
    }

}