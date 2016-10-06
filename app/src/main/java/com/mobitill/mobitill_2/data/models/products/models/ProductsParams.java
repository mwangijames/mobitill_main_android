package com.mobitill.mobitill_2.data.models.products.models;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class ProductsParams {

    @SerializedName("fetch")
    @Expose
    private ProductsFetch fetch;

    /**
     *
     * @return
     * The fetch
     */
    public ProductsFetch getFetch() {
        return fetch;
    }

    /**
     *
     * @param fetch
     * The fetch
     */
    public void setFetch(ProductsFetch fetch) {
        this.fetch = fetch;
    }

}