package com.mobitill.mobitill_2.data.models.apps;

import android.support.annotation.NonNull;

import com.mobitill.mobitill_2.data.models.apps.models.Datum;
import com.mobitill.mobitill_2.data.models.apps.models.RealmApp;

import java.util.List;

/**
 * Main entry point for accessing app data
 */
public interface AppsDataSource {

    interface LoadAppsCallback{
        void onLocalAppsLoaded(List<RealmApp> apps);
        void onRemoteAppsLoaded(List<Datum> apps);
        void onDataNotAvailable();
    }

    interface GetAppCallback{
        void onAppLoaded(RealmApp app);
        void onDataNotAvailable();
    }

    interface SaveAppCallBack{
        void onAppSaved(Datum app);
        void onAppNotSaved();
    }

    interface DeleteAppsCallBack{
        void onAppsDeleted();
        void onAppsDeleteFailed();
    }

    void getApps(@NonNull LoadAppsCallback callback);

    void getApp(@NonNull String appId, @NonNull GetAppCallback callback);

    void saveApp(@NonNull Datum app);

    void refreshApps();

    void deleteApps();

}
