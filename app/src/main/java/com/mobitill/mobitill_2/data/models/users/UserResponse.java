package com.mobitill.mobitill_2.data.models.users;

/**
 * Created by DI on 8/9/2016.
 */
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class UserResponse {

    @SerializedName("data")
    @Expose
    private UserData data;

    /**
     *
     * @return
     * The data
     */
    public UserData getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(UserData data) {
        this.data = data;
    }
}

