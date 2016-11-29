package com.mobitill.mobitill_2.data.models.apps.remote;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.mobitill.mobitill_2.Constants;
import com.mobitill.mobitill_2.data.endpoints.AppsEndpoints;
import com.mobitill.mobitill_2.data.models.apps.AppsDataSource;
import com.mobitill.mobitill_2.data.models.apps.models.Apps;
import com.mobitill.mobitill_2.data.models.apps.models.Body;
import com.mobitill.mobitill_2.data.models.apps.models.Datum;
import com.mobitill.mobitill_2.data.models.apps.models.Params;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by james on 8/23/2016.
 */
@Singleton
public class AppsRemoteDataSource implements AppsDataSource {

    private static final String TAG = AppsRemoteDataSource.class.getSimpleName();

    private final Retrofit mRetrofit;
    private final Params mParams;
    private final Body mBody;
    private final SharedPreferences mSharedPreferences;
    private final Constants mConstants;

    @Inject
    public AppsRemoteDataSource(Retrofit retrofit, Params params,
                                Body body, SharedPreferences sharedPreferences,
                                Constants constants){
        mRetrofit = retrofit;
        mBody = body;
        mParams = params;
        mSharedPreferences = sharedPreferences;
        mConstants = constants;
    }

    @Override
    public void getApps(@NonNull LoadAppsCallback callback) {
        AppsEndpoints appsEndpoints = mRetrofit.create(AppsEndpoints.class);
        if(mParams != null && mBody != null){
            mParams.setUserid(mSharedPreferences.getString(mConstants.USERID,
                    null));
            mBody.setParams(mParams);
            getApplications(appsEndpoints, mBody, callback);
        }
    }

    @Override
    public void getApp(@NonNull String appId, @NonNull GetAppCallback callback) {

    }

    @Override
    public void saveApp(@NonNull Datum app) {

    }

    @Override
    public void refreshApps(@NonNull LoadAppsCallback callback) {

    }


    @Override
    public void deleteApps() {

    }

    private void getApplications(AppsEndpoints appsEndpoints, final Body body,
                                 final LoadAppsCallback callback) {
        Call<Apps> call = appsEndpoints.fetchApps(body);

        call.enqueue(new Callback<Apps>() {
            @Override
            public void onResponse(Call<Apps> call, Response<Apps> response) {
                if(response.isSuccessful()){
                    //Log.i(TAG, "onResponse: " + new Gson().toJson(response));
                    List<Datum> datumList = response.body().getData();
                    if(!datumList.isEmpty()){
                        callback.onRemoteAppsLoaded(datumList);
                    }
                } else {
                    callback.onDataNotAvailable();
                }
            }

            @Override
            public void onFailure(Call<Apps> call, Throwable t) {
                callback.onDataNotAvailable();
            }
        });
    }



}
