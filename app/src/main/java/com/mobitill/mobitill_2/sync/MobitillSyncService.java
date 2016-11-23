package com.mobitill.mobitill_2.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MobitillSyncService extends Service {
    private static final Object sSyncAdapterLock = new Object();
    private static MobitillSyncAdapter sSunshineSyncAdapter = null;

    @Override
    public void onCreate() {
        Log.d("MobitillSyncService", "onCreate - MobitillSyncService");
        synchronized (sSyncAdapterLock) {
            if (sSunshineSyncAdapter == null) {
                sSunshineSyncAdapter = new MobitillSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sSunshineSyncAdapter.getSyncAdapterBinder();
    }
}