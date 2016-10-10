package com.mobitill.mobitill_2.data.models.clients.models.delete;

/**
 * Created by james on 10/6/2016.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class ClientDeleteParams {

    @SerializedName("itemid")
    @Expose
    private String itemid;
    @SerializedName("appid")
    @Expose
    private String appid;

    /**
     *
     * @return
     * The itemid
     */
    public String getItemid() {
        return itemid;
    }

    /**
     *
     * @param itemid
     * The itemid
     */
    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    /**
     *
     * @return
     * The appid
     */
    public String getAppid() {
        return appid;
    }

    /**
     *
     * @param appid
     * The appid
     */
    public void setAppid(String appid) {
        this.appid = appid;
    }

}