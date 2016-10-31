package com.mobitill.mobitill_2.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import static android.content.Context.ACCOUNT_SERVICE;

/**
 * Created by james on 10/28/2016.
 */

public class SyncUtils {

    // Constants
    // The authority for the sync adapter's content provider
    public static final String AUTHORITY = "com.mobitill.mobitill_2.provider";
    // An account type, in the form of a domain name
    public static final String ACCOUNT_TYPE = "com.mobitill.mobitill_2.provider";
    // The account name
    public static final String ACCOUNT = "mobitill_account";
    private static final String TAG = SyncUtils.class.getSimpleName();
    private static final long SYNC_FREQUENCY = 60 * 60;  // 1 hour (in seconds)
    // Instance fields

    /**
     * Create a new dummy account for the sync adapter
     *
     * @param context The application context
     */
    public static Account CreateSyncAccount(Context context) {
        Log.i(TAG, "CreateSyncAccount");
        // Create the account type and default account
        Account newAccount = AuthenticatorService.GetAccount();
        // Get an instance of the Android account manager

        AccountManager accountManager =
                (AccountManager) context.getSystemService(
                        Context.ACCOUNT_SERVICE);
        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
        if (accountManager.addAccountExplicitly(newAccount, null, null)) {
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call context.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */
            Log.i(TAG, "CreateSyncAccount: " + "Success");

            // Inform the system that this account is eligible for auto sync when the network is up
            ContentResolver.setSyncAutomatically(newAccount, AUTHORITY, true);
            // Recommend a schedule for automatic synchronization. The system may modify this based
            // on other scheduled syncs and network utilization.
            ContentResolver.addPeriodicSync(
                    newAccount, AUTHORITY, new Bundle(), SYNC_FREQUENCY);

            return newAccount;
        } else {
            /*
             * The account exists or some other error occurred. Log this, report it,
             * or handle it internally.
             */
            Log.i(TAG, "CreateSyncAccount: " + "Fail");
        }

        return newAccount;
    }

    /**
     * Helper method to trigger an immediate sync ("refresh").
     *
     * <p>This should only be used when we need to preempt the normal sync schedule. Typically, this
     * means the user has pressed the "refresh" button.
     *
     * Note that SYNC_EXTRAS_MANUAL will cause an immediate sync, without any optimization to
     * preserve battery life. If you know new data is available (perhaps via a GCM notification),
     * but the user is not actively waiting for that data, you should omit this flag; this will give
     * the OS additional freedom in scheduling your sync request.
     */
    public static void TriggerRefresh() {
        // Pass the settings flags by inserting them in a bundle
        Bundle settingsBundle = new Bundle();
        settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        Log.i(TAG, "TriggerRefresh");
        /*
         * Request the sync for the default account, authority, and
         * manual sync settings
         */
        ContentResolver.requestSync(AuthenticatorService.GetAccount(), AUTHORITY, settingsBundle);                                     // Extras
    }
}
