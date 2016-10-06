package com.mobitill.mobitill_2.data.models.apps.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by james on 8/24/2016.
 */
public class RealmApp extends RealmObject {
    @PrimaryKey
    private String appid;
    private String name;
    private String orgid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrgid() {
        return orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }
}
