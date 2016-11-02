package com.mobitill.mobitill_2.data.models.generic.remote;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.mobitill.mobitill_2.Constants;
import com.mobitill.mobitill_2.data.endpoints.GenericEndPoints;
import com.mobitill.mobitill_2.data.models.generic.GenericDataSource;
import com.mobitill.mobitill_2.data.models.generic.Payload;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by james on 11/1/2016.
 */

public class GenericDataRemoteDataSource implements GenericDataSource{

    private final Retrofit mRetrofit;

    @Inject
    public GenericDataRemoteDataSource(Retrofit retrofit){
        mRetrofit = retrofit;
    }

    @Override
    public void getData(Payload payload, @NonNull final LoadDataCallBack callBack) {
        GenericEndPoints genericEndPoints = mRetrofit.create(GenericEndPoints.class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), payload.getPayload());
        Call<String> call = genericEndPoints.fetch(payload.getModel(), payload.getAction(), body);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    callBack.onDataLoaded(response.body());
                } else {
                    callBack.onDataNotLoaded();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                callBack.onDataNotLoaded();
            }
        });
    }
}
