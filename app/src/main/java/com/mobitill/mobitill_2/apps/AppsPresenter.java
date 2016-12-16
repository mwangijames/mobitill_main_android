package com.mobitill.mobitill_2.apps;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.mobitill.mobitill_2.Constants;
import com.mobitill.mobitill_2.data.models.generic.GenericRepository;
import com.mobitill.mobitill_2.menu.MenuAppSettings;
import com.mobitill.mobitill_2.data.models.apps.AppsDataSource;
import com.mobitill.mobitill_2.data.models.apps.AppsRepository;
import com.mobitill.mobitill_2.data.models.apps.models.Datum;
import com.mobitill.mobitill_2.data.models.apps.models.RealmApp;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by DI on 8/10/2016.
 */
public final class AppsPresenter implements AppsContract.Presenter{

    private static final String TAG = AppsPresenter.class.getSimpleName();
    private final AppsRepository mAppsRepository;
    private final AppsContract.View mAppsView;
    private final SharedPreferences mSharedPreferences;
    private final GenericRepository mGenericRepository;
    private final Constants mConstants;


    private boolean mfirstLoad = true;

    @Inject
    public AppsPresenter(AppsContract.View appsView, AppsRepository appsRepository,
                         SharedPreferences sharedPreferences, Constants constants,
                         GenericRepository genericRepository) {
        mAppsView = appsView;
        mAppsRepository = appsRepository;
        mSharedPreferences = sharedPreferences;
        mConstants = constants;
        mGenericRepository = genericRepository;
    }

    @Override
    public void start() {

    }

    @Override
    public void openAppDetails(@NonNull Datum requestedApp) {
        mSharedPreferences.edit()
                .putString(mConstants.APPID, requestedApp.getAppid())
                .putString(mConstants.APPNAME, requestedApp.getApp().getName())
                .putString(mConstants.APPSETTINGS, requestedApp.getApp().getSettings())
                .apply();
        MenuAppSettings menuAppSettings = new MenuAppSettings();
        menuAppSettings.setSettings(requestedApp.getApp().getSettings());
        mAppsView.showAppDetails(requestedApp.getAppid(), menuAppSettings);
    }

    @Override
    public void openAppDetails(@NonNull RealmApp requestedApp) {
        mSharedPreferences.edit()
                .putString(mConstants.APPID, requestedApp.getAppid())
                .putString(mConstants.APPNAME, requestedApp.getName())
                .putString(mConstants.APPSETTINGS, requestedApp.getSettings())
                .apply();
        MenuAppSettings menuAppSettings = new MenuAppSettings();
        menuAppSettings.setSettings(requestedApp.getSettings());
        mAppsView.showAppDetails(requestedApp.getAppid(), menuAppSettings);
    }

    @Inject
    void setUpListeners(){
        mAppsView.setPresenter(this);
    }

    @Override
    public void fetchApps(boolean forceUpdate) {
        loadApps(forceUpdate || mfirstLoad, true);
        mfirstLoad = false;

    }

    @Override
    public String getFormattedDate(Date date) {
        return null;
    }

    @Override
    public void performSync() {
        //mScheduleAppSync.run();
    }

    private void loadApps(boolean forceUpdate, final boolean showLoadingUI){
        if(forceUpdate){
            //mAppsRepository.refreshApps();
        }
        mAppsView.showLoadingIndicator(true);

        mAppsRepository.getApps(new AppsDataSource.LoadAppsCallback() {
            @Override
            public void onLocalAppsLoaded(List<RealmApp> apps) {
                mAppsView.showLocalApps(apps);
                mAppsView.showLoadingIndicator(false);
                mAppsView.showNoApps(false);
            }

            @Override
            public void onRemoteAppsLoaded(List<Datum> apps) {
                mAppsView.showRemoteApps(apps);
                mAppsView.showLoadingIndicator(false);
                mAppsView.showNoApps(false);
                // TODO: 12/15/2016 resume from here, calculate total and transactions for each application 
            }

            @Override
            public void onDataNotAvailable() {
                Log.i(TAG, "onDataNotAvailable: " + "No data retrieved");
                mAppsView.showLoadingIndicator(false);
                mAppsView.showNoApps(true);
            }
        });
    }
}
