package com.mobitill.mobitill_2.data.models.generic.remote;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.mobitill.mobitill_2.Constants;
import com.mobitill.mobitill_2.data.endpoints.GenericEndPoints;
import com.mobitill.mobitill_2.data.models.generic.GenericDataSource;
import com.mobitill.mobitill_2.data.models.generic.Payload;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by james on 11/1/2016.
 */

public class GenericDataRemoteDataSource implements GenericDataSource{
    private static final String TAG = GenericDataRemoteDataSource.class.getSimpleName();

    private final Retrofit mRetrofit;

    @Inject
    public GenericDataRemoteDataSource(Retrofit retrofit){
        mRetrofit = retrofit;
    }

    @Override
    public void postData(Payload payload, @NonNull final LoadDataCallBack callBack) {
        GenericEndPoints genericEndPoints = mRetrofit.create(GenericEndPoints.class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), payload.getPayload());
        Call<ResponseBody> call;
        if(payload.getDemo()){
            call = genericEndPoints.fetch(payload.getModel(), payload.getAction(), "demo", body);
        } else {
            call = genericEndPoints.fetch(payload.getModel(), payload.getAction(), "", body);
        }

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        callBack.onDataLoaded(response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    callBack.onDataNotLoaded();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBack.onDataNotLoaded();
            }
        });
    }
}
