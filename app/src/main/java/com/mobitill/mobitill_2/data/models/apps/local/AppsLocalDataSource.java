package com.mobitill.mobitill_2.data.models.apps.local;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.mobitill.mobitill_2.data.models.apps.AppsDataSource;
import com.mobitill.mobitill_2.data.models.apps.RealmController;
import com.mobitill.mobitill_2.data.models.apps.models.Datum;
import com.mobitill.mobitill_2.data.models.apps.models.RealmApp;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.realm.Realm;
import io.realm.RealmResults;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Created by james on 8/23/2016.
 */
@Singleton
public class AppsLocalDataSource implements AppsDataSource{

    private static final String TAG = AppsLocalDataSource.class.getSimpleName();

    private final RealmController mRealmController;

    @Inject
    public AppsLocalDataSource(@NonNull Context context, RealmController realmController){
        checkNotNull(context);
        mRealmController = realmController;
    }

    @Override
    public void getApps(@NonNull LoadAppsCallback callback) {
        RealmResults<RealmApp> realmApps = mRealmController.getApps();
        Log.i(TAG, "getApps: " + realmApps.size());
        if(realmApps.isEmpty()){
            Log.i(TAG, "getApps: " + "table is empty");
            // this will be called if the table is new or just empty
            callback.onDataNotAvailable();
        } else {
            callback.onLocalAppsLoaded(realmApps);
        }
    }

    @Override
    public void getApp(@NonNull String appId, @NonNull GetAppCallback callback) {

    }

    @Override
    public void saveApp(@NonNull Datum app) {
        mRealmController.saveApp(app);
    }

    @Override
    public void refreshApps() {

    }

    @Override
    public void deleteApps() {
        mRealmController.clearAll();
    }
}
