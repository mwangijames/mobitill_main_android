package com.mobitill.mobitill_2.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.mobitill.mobitill_2.MobitillApplication;
import com.mobitill.mobitill_2.R;
import com.mobitill.mobitill_2.data.models.apps.AppsDataSource;
import com.mobitill.mobitill_2.data.models.apps.AppsRepository;
import com.mobitill.mobitill_2.data.models.apps.models.Datum;
import com.mobitill.mobitill_2.data.models.apps.models.RealmApp;
import com.mobitill.mobitill_2.data.models.generic.Actions;
import com.mobitill.mobitill_2.data.models.generic.GenericDataSource;
import com.mobitill.mobitill_2.data.models.generic.GenericRepository;
import com.mobitill.mobitill_2.data.models.generic.Payload;
import com.mobitill.mobitill_2.utils.SettingsHelper;

import java.util.List;

import javax.inject.Inject;

public class MobitillSyncAdapter extends AbstractThreadedSyncAdapter {
    public final String TAG = MobitillSyncAdapter.class.getSimpleName();
    // Interval at which to sync with the weather, in milliseconds.
    // 60 seconds (1 minute)  180 = 3 hours
    //public static final int SYNC_INTERVAL = 30; // use this for testing
    public static final int SYNC_INTERVAL = 60 * 180;
    public static final int SYNC_FLEXTIME = SYNC_INTERVAL/3;

    @Inject
    public  AppsRepository mAppsRepository;

    @Inject
    public GenericRepository mGenericRepository;

    @Inject
    SettingsHelper mSettingsHelper;


    
    public MobitillSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);

        DaggerMobitillSyncComponent.builder()
                .baseComponent(((MobitillApplication) context.getApplicationContext()).getBaseComponent())
                .build()
                .inject(this);
    }



    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        Log.d(TAG, "onPerformSync Called.");
        syncApps();
    }

    private void syncApps() {
        mAppsRepository.refreshApps(new AppsDataSource.LoadAppsCallback() {
            @Override
            public void onLocalAppsLoaded(List<RealmApp> apps) {
                Log.i(TAG, "onLocalAppsLoaded");
            }

            @Override
            public void onRemoteAppsLoaded(List<Datum> apps) {
                Log.i(TAG, "onRemoteAppsLoaded");
                for (Datum app: apps) {
                    syncModels(app.getApp().getSettings(), app.getAppid());
                }

            }

            @Override
            public void onDataNotAvailable() {
                Log.i(TAG, "onDataNotAvailable");
            }
        });
    }

    private void syncModels(String settings, String appId) {
        mGenericRepository.deleteAll();
        Actions actions = new Actions();
        //Log.i(TAG, "syncModels: " + appId + " : " + settings);
        List<String> models = mSettingsHelper.getModels(settings);
        if(models!=null && !models.isEmpty()){
            for(final String model: models){
                Log.i(TAG, "syncModels: " + model);
                Payload payload = new Payload();
                payload.setDemo(mSettingsHelper.isDemo(settings));
                payload.setAppid(appId);
                payload.setAction(actions.FETCH);
                payload.setModel(model);
                payload.setPayload(mSettingsHelper.getFetchPayload(actions.FETCH, appId));
                if(!payload.isEmpty()){
                    mGenericRepository.refreshData(payload, new GenericDataSource.LoadDataCallBack() {
                        @Override
                        public void onDataLoaded(String data) {
                            Log.i(TAG, "onDataLoaded: " + model);
                        }

                        @Override
                        public void onDataNotLoaded() {
                            Log.i(TAG, "onDataNotLoaded: " + model);
                        }
                    });
                } else {
                    Log.i(TAG, "syncModels: " + "payload missing some fields");
                }
            }
        }
    }

    /**
     * Helper method to have the sync adapter sync immediately
     * @param context The context used to access the account service
     */
    public static void syncImmediately(Context context) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(getSyncAccount(context),
                context.getString(R.string.content_authority), bundle);
    }

    /**
     * Helper method to get the fake account to be used with SyncAdapter, or make a new one
     * if the fake account doesn't exist yet.  If we make a new account, we call the
     * onAccountCreated method so we can initialize things.
     *
     * @param context The context used to access the account service
     * @return a fake account.
     */
    public static Account getSyncAccount(Context context) {
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        // Create the account type and default account
        Account newAccount = new Account(
                context.getString(R.string.app_name), context.getString(R.string.sync_account_type));

        // If the password doesn't exist, the account doesn't exist
        if ( null == accountManager.getPassword(newAccount) ) {

        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
            if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
                return null;
            }
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call ContentResolver.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */

            onAccountCreated(newAccount, context);
        }
        return newAccount;
    }

    /**
     * Helper method to schedule the sync adapter periodic execution
     */
    public static void configurePeriodicSync(Context context, int syncInterval, int flexTime) {
        Account account = getSyncAccount(context);
        String authority = context.getString(R.string.content_authority);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // we can enable inexact timers in our periodic sync
            SyncRequest request = new SyncRequest.Builder().
                    syncPeriodic(syncInterval, flexTime).
                    setSyncAdapter(account, authority).
                    setExtras(new Bundle()).build();
            ContentResolver.requestSync(request);
        } else {
            ContentResolver.addPeriodicSync(account,
                    authority, new Bundle(), syncInterval);
        }
    }

    private static void onAccountCreated(Account newAccount, Context context) {
        /*
         * Since we've created an account
         */
        MobitillSyncAdapter.configurePeriodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME);

        /*
         * Without calling setSyncAutomatically, our periodic sync will not be enabled.
         */
        ContentResolver.setSyncAutomatically(newAccount, context.getString(R.string.content_authority), true);

        /*
         * Finally, let's do a sync to get things started
         */
        syncImmediately(context);
    }

    public static void initializeSyncAdapter(Context context) {
        getSyncAccount(context);
    }
}
