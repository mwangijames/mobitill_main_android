package com.mobitill.mobitill_2.data.models.apps.models;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

@Generated("org.jsonschema2pojo")
public class Datum {

    @SerializedName("app")
    @Expose
    private App app;
    @SerializedName("appid")
    @Expose
    private String appid;

    /**
     *
     * @return
     * The app
     */
    public App getApp() {
        return app;
    }

    /**
     *
     * @param app
     * The app
     */
    public void setApp(App app) {
        this.app = app;
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