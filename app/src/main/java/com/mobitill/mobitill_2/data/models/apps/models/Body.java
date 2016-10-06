package com.mobitill.mobitill_2.data.models.apps.models;

/**
 * Created by DI on 8/18/2016.
 */
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Body {

    @SerializedName("params")
    @Expose
    private Params params;

    /**
     *
     * @return
     * The params
     */
    public Params getParams() {
        return params;
    }

    /**
     *
     * @param params
     * The params
     */
    public void setParams(Params params) {
        this.params = params;
    }

}
