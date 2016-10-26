package com.mobitill.mobitill_2.data.models.clients.models.create;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by james on 10/26/2016.
 */

public class ClientEditQuery {
    @SerializedName("params")
    @Expose
    private ClientCreateResponseData params;

    /**
     *
     * @return
     * The params
     */
    public ClientCreateResponseData getParams() {
        return params;
    }

    /**
     *
     * @param params
     * The params
     */
    public void setParams(ClientCreateResponseData params) {
        this.params = params;
}
}
