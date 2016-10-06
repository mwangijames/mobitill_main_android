package com.mobitill.mobitill_2.data.models.apps.models;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Params {

    @SerializedName("userid")
    @Expose
    private String userid;

    /**
     *
     * @return
     * The userid
     */
    public String getUserid() {
        return userid;
    }

    /**
     *
     * @param userid
     * The userid
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

}
