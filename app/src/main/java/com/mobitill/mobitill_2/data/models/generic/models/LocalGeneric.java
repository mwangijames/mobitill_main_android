package com.mobitill.mobitill_2.data.models.generic.models;

import com.orm.SugarRecord;

/**
 * Created by james on 11/30/2016.
 */

public class LocalGeneric extends SugarRecord {
    private String model;
    private String data;
    private String appid;

        public LocalGeneric(){
        
    }

    public String getModelName() {
        return model;
    }

    public void setModelName(String model) {
        this.model = model;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }
}
