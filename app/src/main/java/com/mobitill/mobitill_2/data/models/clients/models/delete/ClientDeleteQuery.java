package com.mobitill.mobitill_2.data.models.clients.models.delete;

/**
 * Created by james on 10/6/2016.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class ClientDeleteQuery {

    @SerializedName("params")
    @Expose
    private ClientDeleteParams params;

    /**
     *
     * @return
     * The params
     */
    public ClientDeleteParams getParams() {
        return params;
    }

    /**
     *
     * @param params
     * The params
     */
    public void setParams(ClientDeleteParams params) {
        this.params = params;
    }
}
