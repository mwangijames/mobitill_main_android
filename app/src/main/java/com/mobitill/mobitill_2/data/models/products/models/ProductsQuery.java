package com.mobitill.mobitill_2.data.models.products.models;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class ProductsQuery {

    @SerializedName("params")
    @Expose
    private ProductsParams params;

    /**
     *
     * @return
     * The params
     */
    public ProductsParams getParams() {
        return params;
    }

    /**
     *
     * @param params
     * The params
     */
    public void setParams(ProductsParams params) {
        this.params = params;
    }

}
