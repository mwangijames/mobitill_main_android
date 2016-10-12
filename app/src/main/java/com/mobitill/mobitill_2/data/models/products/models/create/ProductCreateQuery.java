package com.mobitill.mobitill_2.data.models.products.models.create;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class ProductCreateQuery {

    @SerializedName("params")
    @Expose
    private ProductCreateParams params;

    /**
     *
     * @return
     * The params
     */
    public ProductCreateParams getParams() {
        return params;
    }

    /**
     *
     * @param params
     * The params
     */
    public void setParams(ProductCreateParams params) {
        this.params = params;
    }

}