package com.mobitill.mobitill_2.data.models.fleet;

import android.content.SharedPreferences;

import com.mobitill.mobitill_2.Constants;
import com.mobitill.mobitill_2.data.Remote;
import com.mobitill.mobitill_2.data.models.fleet.models.FleetFetch;
import com.mobitill.mobitill_2.data.models.fleet.models.FleetParams;
import com.mobitill.mobitill_2.data.models.fleet.models.FleetQuery;
import com.mobitill.mobitill_2.data.models.fleet.remote.FleetRemoteDataSource;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by james on 9/19/2016.
 */
@Module
public class FleetRepositoryModule {
    @Provides
    @Remote
    FleetDataSource provideFleetRemoteDataSource(Retrofit retrofit, SharedPreferences sharedPreferences,
                                                 Constants constants, FleetQuery fleetQuery,
                                                 FleetParams fleetParams, FleetFetch fleetFetch){
        return new FleetRemoteDataSource(retrofit, sharedPreferences, constants, fleetQuery,
                                            fleetParams, fleetFetch);
    }

}
