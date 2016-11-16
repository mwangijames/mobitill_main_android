package com.mobitill.mobitill_2.data.models.apps;

import com.mobitill.mobitill_2.data.models.apps.models.Datum;
import com.mobitill.mobitill_2.data.models.apps.models.RealmApp;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by james on 8/23/2016.
 */
@Singleton
public class RealmController {
    private static final String TAG = RealmController.class.getSimpleName();
    private final Realm mRealm;


    @Inject
    public RealmController(Realm realm){
        mRealm = realm;
    }


    public Realm getRealm(){
        return mRealm;
    }

    // clear all objects from RealmApp.class
    public void clearAll(){
//        mRealm.beginTransaction();
//        mRealm.delete(RealmApp.class);
//        mRealm.commitTransaction();
    }

    // find all objects in RealmApp.class
    public RealmResults<RealmApp> getApps(){
//        Log.i(TAG, "getApps: " + mRealm.where(RealmApp.class).findAll().size());
        RealmQuery realmQuery = mRealm.where(RealmApp.class);
        RealmResults<RealmApp> realmResults = realmQuery.findAll();
        for(RealmApp realmApp: realmResults){
           // Log.i(TAG, "getApps: " + realmApp.getName());
        }
        return realmResults;
    }

    // query a single item with the given id
    public RealmApp getApp(String id){
        return mRealm.where(RealmApp.class).equalTo("appid", id).findFirst();
    }

    // save a RealmApp.class object
    public void saveApp(final Datum app){
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmApp realmApp = realm.createObject(RealmApp.class);
                realmApp.setAppid(app.getAppid());
                realmApp.setName(app.getApp().getName());
                realmApp.setOrgid(app.getApp().getOrgid());
                realmApp.setSettings(app.getApp().getSettings());

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                //Log.i(TAG, "onSuccess: " + app.getApp().getName() + " saved");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                //Log.i(TAG, "onError: " + error.getMessage());
            }
        });
    }

    // check if RealmApp.class is empty
    public boolean hasApps(){
        return !mRealm.where(RealmApp.class).findAll().isEmpty();
    }


    public void closeRealm(){
        if(!mRealm.isClosed()){
            mRealm.close();
        }
    }
}