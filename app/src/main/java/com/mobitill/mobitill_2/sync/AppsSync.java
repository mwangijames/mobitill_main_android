package com.mobitill.mobitill_2.sync;

import android.content.Context;

import com.mobitill.mobitill_2.data.models.apps.AppsDataSource;
import com.mobitill.mobitill_2.data.models.apps.AppsRepository;
import com.mobitill.mobitill_2.data.models.apps.models.Datum;
import com.mobitill.mobitill_2.data.models.apps.models.RealmApp;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by james on 8/29/2016.
 */
public class AppsSync {

    private static final String TAG = AppsSync.class.getSimpleName();
    private final AppsRepository mAppsRepository;
    private final Context mContext;

    private List<Datum> mRemoteApps = new ArrayList<>();
    private List<RealmApp> mLocalApps = new ArrayList<>();

    @Inject
    public AppsSync(AppsRepository appsRepository, Context context){
        mAppsRepository = appsRepository;
        mContext = context;
    }


    public void sync(){
        mAppsRepository.getApps(new AppsDataSource.LoadAppsCallback() {
            @Override
            public void onLocalAppsLoaded(List<RealmApp> apps) {
                mLocalApps = apps;
            }

            @Override
            public void onRemoteAppsLoaded(List<Datum> apps) {

            }

            @Override
            public void onDataNotAvailable() {

            }
        });

        mAppsRepository.getTasksFromRemoteDataSource(new AppsDataSource.LoadAppsCallback() {
            @Override
            public void onLocalAppsLoaded(List<RealmApp> apps) {

            }

            @Override
            public void onRemoteAppsLoaded(List<Datum> apps) {
                mRemoteApps = apps;
                runSync(mLocalApps, mRemoteApps);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    private void runSync(List<RealmApp> localApps, List<Datum> remoteApps){
        //String text = "Local: " + Integer.toString(localApps.size()) + " " + "Remote: " + Integer.toString(remoteApps.size());
        //Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
        //Log.i(TAG, "runSync: " + text);
    }
}
