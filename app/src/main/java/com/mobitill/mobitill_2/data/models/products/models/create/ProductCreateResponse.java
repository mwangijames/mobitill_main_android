package com.mobitill.mobitill_2.data.models.products.models.create;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class ProductCreateResponse {

    @SerializedName("data")
    @Expose
    private ProductCreateResponseData data;

    /**
     *
     * @return
     * The data
     */
    public ProductCreateResponseData getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(ProductCreateResponseData data) {
        this.data = data;
    }

}