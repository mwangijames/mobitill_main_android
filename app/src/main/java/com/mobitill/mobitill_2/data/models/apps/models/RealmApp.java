package com.mobitill.mobitill_2.data.models.apps.models;

import com.orm.SugarRecord;

/**
 * Created by james on 8/24/2016.
 */
public class RealmApp extends SugarRecord {
    private String appid;
    private String name;
    private String orgid;
    private String settings;

    public RealmApp(){};

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

    public String getSettings() {
        return settings;
    }

    public void setSettings(String settings) {
        this.settings = settings;
    }
}
