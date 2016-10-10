package com.mobitill.mobitill_2.data.models.clients.models.delete;

/**
 * Created by james on 10/6/2016.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class ClientDeleteResponse {

    @SerializedName("data")
    @Expose
    private ClientDeleteResponseData data;

    /**
     *
     * @return
     * The data
     */
    public ClientDeleteResponseData getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(ClientDeleteResponseData data) {
        this.data = data;
    }

}