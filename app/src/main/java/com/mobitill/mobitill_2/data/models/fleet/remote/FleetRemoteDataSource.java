package com.mobitill.mobitill_2.data.models.fleet.remote;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.mobitill.mobitill_2.Constants;
import com.mobitill.mobitill_2.data.endpoints.FleetEndPoints;
import com.mobitill.mobitill_2.data.models.fleet.FleetDataSource;
import com.mobitill.mobitill_2.data.models.fleet.models.Fleet;
import com.mobitill.mobitill_2.data.models.fleet.models.FleetFetch;
import com.mobitill.mobitill_2.data.models.fleet.models.FleetItem;
import com.mobitill.mobitill_2.data.models.fleet.models.FleetParams;
import com.mobitill.mobitill_2.data.models.fleet.models.FleetQuery;
import com.mobitill.mobitill_2.data.models.fleet.models.create.FleetCreateQuery;
import com.mobitill.mobitill_2.data.models.fleet.models.create.FleetCreateResponse;

import javax.inject.Inject;

import dagger.Provides;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by james on 9/19/2016.
 */
public class FleetRemoteDataSource implements FleetDataSource {

    private final Retrofit mRetrofit;
    private final SharedPreferences mSharedPreferences;
    private final Constants mConstants;
    private final FleetParams mFleetParams;
    private final FleetQuery mFleetQuery;
    private final FleetFetch mFleetFetch;

    @Inject
    public FleetRemoteDataSource(Retrofit retrofit, SharedPreferences sharedPreferences,
                                 Constants constants, FleetQuery fleetQuery,
                                 FleetParams fleetParams, FleetFetch fleetFetch){
        mRetrofit = retrofit;
        mSharedPreferences = sharedPreferences;
        mFleetQuery = fleetQuery;
        mConstants = constants;
        mFleetParams = fleetParams;
        mFleetFetch = fleetFetch;
    }

    @Override
    public void getFleet(String appId, @NonNull LoadFleetCallBack callBack) {
        FleetEndPoints fleetEndPoints = mRetrofit.create(FleetEndPoints.class);
        if(mFleetFetch != null && mFleetQuery != null && mFleetQuery != null){
            mFleetFetch.setAppid(appId);
            mFleetParams.setFetch(mFleetFetch);
            mFleetQuery.setParams(mFleetParams);
            getRemoteFleet(fleetEndPoints, mFleetQuery, callBack);
        }
    }

    @Override
    public void createFleet(FleetCreateQuery fleetCreateQuery, @NonNull final CreateFleetCallBack callBack) {
        FleetEndPoints fleetEndPoints = mRetrofit.create(FleetEndPoints.class);
        if(fleetCreateQuery!=null){
            Call<FleetCreateResponse> call = fleetEndPoints.createFleetItem(fleetCreateQuery);
            call.enqueue(new Callback<FleetCreateResponse>() {
                @Override
                public void onResponse(Call<FleetCreateResponse> call, Response<FleetCreateResponse> response) {
                    if(response.isSuccessful()){
                        callBack.onFleetCreated(response.body());
                    } else {
                        callBack.onFleetNotCreated();
                    }
                }

                @Override
                public void onFailure(Call<FleetCreateResponse> call, Throwable t) {
                    callBack.onFleetNotCreated();
                }
            });
        }
    }

    private void getRemoteFleet(FleetEndPoints fleetEndPoints, FleetQuery fleetQuery,
                                final LoadFleetCallBack loadFleetCallBack){
        Call<Fleet> call = fleetEndPoints.fetchFleet(fleetQuery);
        call.enqueue(new Callback<Fleet>() {
            @Override
            public void onResponse(Call<Fleet> call, Response<Fleet> response) {
                if(response.isSuccessful()){
                    loadFleetCallBack.onFleetLoaded(response.body().getData());
                } else {
                    loadFleetCallBack.onFleetNotLoaded();
                }
            }

            @Override
            public void onFailure(Call<Fleet> call, Throwable t) {
                loadFleetCallBack.onFleetNotLoaded();
            }
        });
    }
}
