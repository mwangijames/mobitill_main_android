package com.mobitill.mobitill_2.data.models.apps;

import com.mobitill.mobitill_2.data.models.apps.models.Datum;
import com.mobitill.mobitill_2.data.models.apps.models.RealmApp;

import java.util.List;

import javax.inject.Singleton;

/**
 * Created by james on 8/23/2016.
 */
@Singleton
public class AppsController {

    private static final String TAG = AppsController.class.getSimpleName();


    public void saveApp(Datum app) {
        RealmApp localApp = new RealmApp();
        localApp.setSettings(app.getApp().getSettings());
        localApp.setAppid(app.getAppid());
        localApp.setName(app.getApp().getName());
        localApp.setOrgid(app.getApp().getOrgid());
        localApp.save();
    }

    public void clearAll() {
        RealmApp.deleteAll(RealmApp.class);
    }

    public List<RealmApp> getApps(){
        List<RealmApp> allApps =  RealmApp.listAll(RealmApp.class);
        return allApps;
    }

    public RealmApp getApp(){
        return null;
    }
}