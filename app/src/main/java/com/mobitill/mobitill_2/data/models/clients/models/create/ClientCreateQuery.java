package com.mobitill.mobitill_2.data.models.clients.models.create;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class ClientCreateQuery {

    @SerializedName("params")
    @Expose
    private ClientCreateParams params;

    /**
     *
     * @return
     * The params
     */
    public ClientCreateParams getParams() {
        return params;
    }

    /**
     *
     * @param params
     * The params
     */
    public void setParams(ClientCreateParams params) {
        this.params = params;
    }

}