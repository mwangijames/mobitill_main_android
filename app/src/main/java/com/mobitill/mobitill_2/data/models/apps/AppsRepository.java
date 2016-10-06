package com.mobitill.mobitill_2.data.models.apps;

import android.support.annotation.NonNull;

import com.mobitill.mobitill_2.data.Local;
import com.mobitill.mobitill_2.data.Remote;
import com.mobitill.mobitill_2.data.models.apps.models.Datum;
import com.mobitill.mobitill_2.data.models.apps.models.RealmApp;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.inject.Inject;

/**
 * Created by james on 8/23/2016.
 */
public class AppsRepository implements AppsDataSource{


    private final AppsDataSource mAppsRemoteDataSource;
    private final AppsDataSource mAppsLocalDataSource;

    @Inject
    AppsRepository(@Remote AppsDataSource appsRemoteDataSourse,
                   @Local AppsDataSource appsLocalDataSource){
        mAppsRemoteDataSource = appsRemoteDataSourse;
        mAppsLocalDataSource = appsLocalDataSource;
    }


    /**
     * Get tasks from local data source (Realm) or remote data source,
     * whichever is available first
     *
     * */
    @Override
    public void getApps(@NonNull final LoadAppsCallback callback) {
        checkNotNull(callback);

        // query the local storage if available, if not, query the network
        mAppsLocalDataSource.getApps(new LoadAppsCallback() {
            @Override
            public void onLocalAppsLoaded(List<RealmApp> apps) {
                callback.onLocalAppsLoaded(apps);
            }

            @Override
            public void onRemoteAppsLoaded(List<Datum> apps) {
                //callback.onRemoteAppsLoaded(apps);
            }

            @Override
            public void onDataNotAvailable() {
                getTasksFromRemoteDataSource(callback);
            }
        });

    }

    @Override
    public void getApp(@NonNull String appId, @NonNull GetAppCallback callback) {

    }

    @Override
    public void saveApp(@NonNull Datum app) {

    }

    @Override
    public void refreshApps() {

    }

    @Override
    public void deleteApps() {

    }

    public void getTasksFromRemoteDataSource(@NonNull final LoadAppsCallback callback){
        mAppsRemoteDataSource.getApps(new LoadAppsCallback() {
            @Override
            public void onLocalAppsLoaded(List<RealmApp> apps) {
                callback.onLocalAppsLoaded(apps);
            }

            @Override
            public void onRemoteAppsLoaded(List<Datum> apps) {
                refreshLocalDataSource(apps);
                callback.onRemoteAppsLoaded(apps);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    private void refreshLocalDataSource(List<Datum> apps){
        mAppsLocalDataSource.deleteApps();
        for(Datum app: apps){
            mAppsLocalDataSource.saveApp(app);
        }
    }

}
