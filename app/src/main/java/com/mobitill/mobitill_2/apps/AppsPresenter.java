package com.mobitill.mobitill_2.apps;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.mobitill.mobitill_2.Constants;
import com.mobitill.mobitill_2.data.models.apps.AppsDataSource;
import com.mobitill.mobitill_2.data.models.apps.AppsRepository;
import com.mobitill.mobitill_2.data.models.apps.models.Datum;
import com.mobitill.mobitill_2.data.models.apps.models.RealmApp;
import com.mobitill.mobitill_2.data.models.generic.Actions;
import com.mobitill.mobitill_2.data.models.generic.GenericDataSource;
import com.mobitill.mobitill_2.data.models.generic.GenericRepository;
import com.mobitill.mobitill_2.data.models.generic.Payload;
import com.mobitill.mobitill_2.menu.MenuAppSettings;
import com.mobitill.mobitill_2.utils.SettingsHelper;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
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
    private final Payload mPayload;
    private final Actions mActions;
    private final SettingsHelper mSettingsHelper;


    private boolean mfirstLoad = true;

    @Inject
    public AppsPresenter(AppsContract.View appsView, AppsRepository appsRepository,
                         SharedPreferences sharedPreferences, Constants constants,
                         GenericRepository genericRepository, Payload payload,
                         Actions actions, SettingsHelper settingsHelper) {
        mAppsView = appsView;
        mAppsRepository = appsRepository;
        mSharedPreferences = sharedPreferences;
        mConstants = constants;
        mGenericRepository = genericRepository;
        mPayload = payload;
        mActions = actions;
        mSettingsHelper = settingsHelper;
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
    public void fetchReport(List<Long> range, String appId) {
        if(mPayload != null){
            mPayload.setAction(mActions.QUERY);
            mPayload.setModel("transactions");
            mPayload.setPayload(mSettingsHelper.getReportsPayload(appId, range, new HashMap<String, String>()));
            mPayload.setDemo(false);
            mPayload.setAppid(appId);
            if(mPayload.isEmpty()){
                Log.i(TAG, "fetchReport: some payload fields are empty");
            } else {
                if(mGenericRepository!=null){
                    mGenericRepository.postData(mPayload, new GenericDataSource.LoadDataCallBack() {
                        @Override
                        public void onDataLoaded(String data) {
                            List<HashMap<String, String>> report = mSettingsHelper.getList(data);
                            //// show the transactions here
                            mAppsView.showTransactions(report.size());
                            // calculate the total
                            getTotal(report);
                        }

                        @Override
                        public void onDataNotLoaded() {

                        }
                    });
                }
            }
        }
    }

    @Override
    public String getFormattedDate(Date date) {
        return null;
    }

    @Override
    public void getTotal(List<HashMap<String, String>> report) {
        new CalculateTotal().execute(report);
    }

    private class CalculateTotal extends AsyncTask<List<HashMap<String, String>>, Void, Double> {

        @Override
        protected Double doInBackground(List<HashMap<String, String>>... params) {
            List<HashMap<String, String>> reports = params[0];
            Double total = 0d;
            for(HashMap<String, String> item: reports){
                for(HashMap.Entry<String, String> entry: item.entrySet()){
                    if(entry.getKey().equalsIgnoreCase("total")){
                        double value = Double.parseDouble(entry.getValue());
                        total = total + value;
                    }
                }
            }
            return total;
        }

        @Override
        protected void onPostExecute(Double aDouble) {
            if(aDouble!=null){
                NumberFormat formatter = NumberFormat.getCurrencyInstance();
                DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) formatter).getDecimalFormatSymbols();
                decimalFormatSymbols.setCurrencySymbol("");
                ((DecimalFormat) formatter).setDecimalFormatSymbols(decimalFormatSymbols);
                String totalString = formatter.format(aDouble);
                // display the total
                //mAppsView.showTotal(String.v);
                mAppsView.showTotal(aDouble);
            }

        }


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
                //loop through each application
                for(RealmApp app: apps){
                    // fetch report for each application
                    List<Long> dates = new ArrayList<>();
                    Calendar date = new GregorianCalendar();
                    // reset hour, minutes, seconds and millis to get midnight
                    date.set(Calendar.HOUR_OF_DAY, 0);
                    date.set(Calendar.MINUTE, 0);
                    date.set(Calendar.SECOND, 0);
                    date.set(Calendar.MILLISECOND, 0);

                    dates.add( date.getTime().getTime());
                    dates.add(new Date().getTime());

                    fetchReport(dates, app.getAppid());
                }
            }

            @Override
            public void onRemoteAppsLoaded(List<Datum> apps) {
                mAppsView.showRemoteApps(apps);
                mAppsView.showLoadingIndicator(false);
                mAppsView.showNoApps(false);
                // loop through each application
                for(Datum app: apps){
                    // fetch report for each application
                    List<Long> dates = new ArrayList<>();
                    Calendar date = new GregorianCalendar();
                    // reset hour, minutes, seconds and millis to get midnight
                    date.set(Calendar.HOUR_OF_DAY, 0);
                    date.set(Calendar.MINUTE, 0);
                    date.set(Calendar.SECOND, 0);
                    date.set(Calendar.MILLISECOND, 0);

                    dates.add( date.getTime().getTime());
                    dates.add(new Date().getTime());

                    fetchReport(dates, app.getAppid());
                }
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
