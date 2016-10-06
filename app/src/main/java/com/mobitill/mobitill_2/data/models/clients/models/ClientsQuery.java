package com.mobitill.mobitill_2.data.models.clients.models;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class ClientsQuery {

    @SerializedName("params")
    @Expose
    private ClientsParams params;

    /**
     *
     * @return
     * The params
     */
    public ClientsParams getParams() {
        return params;
    }

    /**
     *
     * @param params
     * The params
     */
    public void setParams(ClientsParams params) {
        this.params = params;
    }

}
