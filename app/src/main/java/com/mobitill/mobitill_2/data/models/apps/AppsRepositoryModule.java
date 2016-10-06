package com.mobitill.mobitill_2.data.models.apps;

import android.content.Context;
import android.content.SharedPreferences;

import com.mobitill.mobitill_2.Constants;
import com.mobitill.mobitill_2.data.Local;
import com.mobitill.mobitill_2.data.Remote;
import com.mobitill.mobitill_2.data.models.apps.local.AppsLocalDataSource;
import com.mobitill.mobitill_2.data.models.apps.models.Body;
import com.mobitill.mobitill_2.data.models.apps.models.Params;
import com.mobitill.mobitill_2.data.models.apps.remote.AppsRemoteDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import retrofit2.Retrofit;

/**
 * Created by james on 8/23/2016.
 */
@Module
public class AppsRepositoryModule {
    @Provides
    @Local
    AppsDataSource provideAppsLocalDataSource(Context context, RealmController realmController){
        return new AppsLocalDataSource(context, realmController);
    }

    @Provides
    @Remote
    AppsDataSource provideAppsRemoteDataSource(Retrofit retrofit, Params params,
                                               Body body, SharedPreferences sharedPreferences,
                                               Constants constants){
        return new AppsRemoteDataSource(retrofit, params, body, sharedPreferences, constants);
    }

    @Provides
    RealmController provideRealmController(Realm realm){
        return new RealmController(realm);
    }
}
