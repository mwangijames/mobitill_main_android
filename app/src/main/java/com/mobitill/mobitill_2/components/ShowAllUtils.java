package com.mobitill.mobitill_2.components;

import java.io.Serializable;

/**
 * Created by james on 11/1/2016.
 */

public class ShowAllUtils implements Serializable{

    private String settings;
    private String appId;
    private String model;

    public String getSettings() {
        return settings;
    }

    public void setSettings(String settings) {
        this.settings = settings;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public boolean isEmpty(){
        return (appId == null || "".equals(appId)) ||
                (settings == null || "".equals(settings)) ||
                (model == null || "".equals(model));
    }
}
