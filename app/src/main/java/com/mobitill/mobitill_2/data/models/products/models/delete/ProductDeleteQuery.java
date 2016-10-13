package com.mobitill.mobitill_2.data.models.products.models.delete;

/**
 * Created by james on 10/6/2016.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class ProductDeleteQuery {

    @SerializedName("params")
    @Expose
    private ProductDeleteParams params;

    /**
     *
     * @return
     * The params
     */
    public ProductDeleteParams getParams() {
        return params;
    }

    /**
     *
     * @param params
     * The params
     */
    public void setParams(ProductDeleteParams params) {
        this.params = params;
    }
}
