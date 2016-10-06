package com.mobitill.mobitill_2.data.models.reports.remote;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.mobitill.mobitill_2.Constants;
import com.mobitill.mobitill_2.data.endpoints.ReportsEndPoints;
import com.mobitill.mobitill_2.data.models.apps.models.Apps;
import com.mobitill.mobitill_2.data.models.reports.ReportsDataSource;
import com.mobitill.mobitill_2.data.models.reports.models.Fetch;
import com.mobitill.mobitill_2.data.models.reports.models.Params;
import com.mobitill.mobitill_2.data.models.reports.models.Query;
import com.mobitill.mobitill_2.data.models.reports.models.ReportItem;
import com.mobitill.mobitill_2.data.models.reports.models.Reports;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.realm.annotations.PrimaryKey;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by james on 9/2/2016.
 */
@Singleton
public class ReportsRemoteDataSource implements ReportsDataSource{

    private static final String TAG = ReportsRemoteDataSource.class.getSimpleName();

    private final Retrofit mRetrofit;
    private final SharedPreferences mSharedPreferences;
    private final Constants mConstants;
    private Query mQuery;
    private Params mParams;
    private Fetch mFetch;

    @Inject
    public ReportsRemoteDataSource(Retrofit retrofit, SharedPreferences sharedPreferences,
                                   Constants constants, Query query, Params params, Fetch fetch){
        mRetrofit = retrofit;
        mSharedPreferences = sharedPreferences;
        mConstants = constants;
        mQuery = query;
        mParams = params;
        mFetch = fetch;
    }



    @Override
    public void getReports(List<Long> dateRange, String cashier, String productId, String appId, @NonNull LoadReportsCallback callback) {
        ReportsEndPoints reportsEndPoints  = mRetrofit.create(ReportsEndPoints.class);
        if(mParams!=null && mQuery!= null && mFetch!=null){

            mFetch.setAppid(appId);
            mFetch.setRange(dateRange);

            if(!productId.equalsIgnoreCase("")){
                mFetch.setProductid(productId);
            } else {
                mFetch.setProductid(null);
            }

            if(!cashier.equalsIgnoreCase("")){
                mFetch.setCashier(cashier);
            } else {
                mFetch.setCashier(null);
            }

            mParams.setFetch(mFetch);
            mQuery.setParams(mParams);
            getRemoteReports(reportsEndPoints, mQuery, callback);
        }
    }

    @Override
    public void saveReports(@NonNull List<ReportItem> reportItemList) {

    }

    @Override
    public void deleteAllReports() {

    }

    @Override
    public void refreshReports() {

    }

    private void getRemoteReports(ReportsEndPoints reportsEndPoints, Query query,
                                  final LoadReportsCallback callback){
        Call<Reports> call = reportsEndPoints.fetchReports(query);

        call.enqueue(new Callback<Reports>() {
            @Override
            public void onResponse(Call<Reports> call, Response<Reports> response) {
                if(response.isSuccessful()){
                    callback.onRemoteDataLoaded(response.body().getData());
                } else {
                    callback.onDataNotAvailable();
                }
            }

            @Override
            public void onFailure(Call<Reports> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
                callback.onDataNotAvailable();
            }
        });
    }
}
