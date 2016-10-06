package com.mobitill.mobitill_2.data.models.apps.models;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class App {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("orgid")
    @Expose
    private String orgid;

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The orgid
     */
    public String getOrgid() {
        return orgid;
    }

    /**
     *
     * @param orgid
     * The orgid
     */
    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }

}
