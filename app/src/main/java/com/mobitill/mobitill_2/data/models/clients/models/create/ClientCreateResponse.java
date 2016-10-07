package com.mobitill.mobitill_2.data.models.clients.models.create;

/**
 * Created by james on 10/7/2016.
 */

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class ClientCreateResponse {

    @SerializedName("data")
    @Expose
    private ClientCreateResponseData data;

    /**
     *
     * @return
     * The data
     */
    public ClientCreateResponseData getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(ClientCreateResponseData data) {
        this.data = data;
    }

}
