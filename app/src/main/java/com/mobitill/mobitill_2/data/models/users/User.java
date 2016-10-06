package com.mobitill.mobitill_2.data.models.users;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class User {

    @SerializedName("params")
    @Expose
    private UserParams params;

    @SerializedName("data")
    @Expose
    private UserData userData;

    /**
     *
     * @return
     * The params
     */
    public UserParams getParams() {
        return params;
    }

    /**
     *
     * @param params
     * The params
     */
    public void setParams(UserParams params) {
        this.params = params;
    }


    /**
     *
     * @return
     * The params
     */
    public UserData getUserData() {
        return userData;
    }

    /**
     *
     * @param params
     * The params
     */
    public void UserData(UserData userData) {
        this.userData = userData;
    }


}
