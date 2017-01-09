package com.mobitill.mobitill_2.apps;

import android.support.annotation.NonNull;

import com.mobitill.mobitill_2.BasePresenter;
import com.mobitill.mobitill_2.BaseView;
import com.mobitill.mobitill_2.data.models.apps.models.Datum;
import com.mobitill.mobitill_2.data.models.apps.models.RealmApp;
import com.mobitill.mobitill_2.menu.MenuAppSettings;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by DI on 8/10/2016.
 */
public class AppsContract {
    interface View extends BaseView<Presenter> {
        void setLoadingIndicator(boolean active);
        void showRemoteApps(List<Datum> apps);
        void showLocalApps(List<RealmApp> apps);
        void showAppDetails(String appId, MenuAppSettings menuAppSettings);
        void showLoadingAppsError();
        void showNoApps(boolean show);
        void showNoNetwork(boolean show);
        void onResponse(int code);
        void showTransactions(int transactions);
        void showTotal(double total);
        void showLoadingIndicator(boolean show);
        boolean isActive();
    }

    interface Presenter extends BasePresenter {
        void openAppDetails(@NonNull Datum requestedApp);
        void openAppDetails(@NonNull RealmApp requestedApp);
        void fetchApps(boolean forceUpdate);
        void fetchReport(List<Long> range, String appId);
        public void getTotal(List<HashMap<String, String>> report);
        String getFormattedDate(Date date);
        void performSync();
    }

}
