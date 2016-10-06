package com.mobitill.mobitill_2.data.models.reports;

import android.content.Context;
import android.content.SharedPreferences;

import com.mobitill.mobitill_2.Constants;
import com.mobitill.mobitill_2.data.Local;
import com.mobitill.mobitill_2.data.Remote;
import com.mobitill.mobitill_2.data.models.apps.RealmController;
import com.mobitill.mobitill_2.data.models.apps.local.AppsLocalDataSource;
import com.mobitill.mobitill_2.data.models.reports.local.RealmReportController;
import com.mobitill.mobitill_2.data.models.reports.local.ReportsLocalDataSource;
import com.mobitill.mobitill_2.data.models.reports.models.Fetch;
import com.mobitill.mobitill_2.data.models.reports.models.Params;
import com.mobitill.mobitill_2.data.models.reports.models.Query;
import com.mobitill.mobitill_2.data.models.reports.models.Reports;
import com.mobitill.mobitill_2.data.models.reports.remote.ReportsRemoteDataSource;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import retrofit2.Retrofit;

/**
 * Created by james on 9/2/2016.
 */
@Module
public class ReportsRepositoryModule {

    @Provides
    @Local
    ReportsDataSource provideReportsLocalDataSource(Context context, RealmReportController realmReportController){
        return new ReportsLocalDataSource();
    }

    @Provides
    @Remote
    ReportsDataSource provideReportsRemoteDataSource(Retrofit retrofit, SharedPreferences sharedPreferences,
                                                     Constants constants, Query query, Params params, Fetch fetch){
            return new ReportsRemoteDataSource(retrofit, sharedPreferences, constants, query, params, fetch);
    }

    @Provides
    RealmReportController provideRealmReportController(Realm realm){
            return new RealmReportController();
    }
}
