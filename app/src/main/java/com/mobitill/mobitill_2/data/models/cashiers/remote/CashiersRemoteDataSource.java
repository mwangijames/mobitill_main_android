package com.mobitill.mobitill_2.data.models.cashiers.remote;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.mobitill.mobitill_2.Constants;
import com.mobitill.mobitill_2.data.endpoints.CashiersEndPoints;
import com.mobitill.mobitill_2.data.models.cashiers.CashiersDataSource;
import com.mobitill.mobitill_2.data.models.cashiers.models.Cashiers;
import com.mobitill.mobitill_2.data.models.cashiers.models.CashiersFetch;
import com.mobitill.mobitill_2.data.models.cashiers.models.CashiersParams;
import com.mobitill.mobitill_2.data.models.cashiers.models.CashiersQuery;
import com.mobitill.mobitill_2.data.models.cashiers.models.create.CashierCreateQuery;
import com.mobitill.mobitill_2.data.models.cashiers.models.create.CashierCreateResponse;
import com.mobitill.mobitill_2.data.models.cashiers.models.create.CashierCreateResponseData;
import com.mobitill.mobitill_2.data.models.cashiers.models.delete.CashierDeleteQuery;
import com.mobitill.mobitill_2.data.models.cashiers.models.delete.CashierDeleteResponse;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by james on 9/16/2016.
 */
public class CashiersRemoteDataSource implements CashiersDataSource {

    private final Retrofit mRetrofit;
    private final SharedPreferences mSharedPreferences;
    private final Constants mConstants;
    private final CashiersParams mCashiersParams;
    private final CashiersFetch mCashiersFetch;
    private final CashiersQuery mCashiersQuery;

    @Inject
    public CashiersRemoteDataSource(Retrofit retrofit, SharedPreferences sharedPreferences,
                                    Constants constants, CashiersQuery cashiersQuery,
                                    CashiersParams cashiersParams, CashiersFetch cashiersFetch){
        mRetrofit = retrofit;
        mSharedPreferences = sharedPreferences;
        mConstants = constants;
        mCashiersQuery = cashiersQuery;
        mCashiersParams = cashiersParams;
        mCashiersFetch = cashiersFetch;
    }

    @Override
    public void getCashiers(String appId, @NonNull LoadCashiersCallback callback) {
        CashiersEndPoints cashiersEndPoints = mRetrofit.create(CashiersEndPoints.class);
        if(mCashiersParams != null && mCashiersFetch != null && mCashiersQuery != null){
            mCashiersFetch.setAppid(appId);
            mCashiersParams.setFetch(mCashiersFetch);
            mCashiersQuery.setParams(mCashiersParams);
            getRemoteCashiers(cashiersEndPoints, mCashiersQuery, callback);
        }
    }

    @Override
    public void createCashier(CashierCreateQuery cashierCreateQuery, @NonNull final CreateCashiersCallBack callBack) {
        CashiersEndPoints cashiersEndPoints = mRetrofit.create(CashiersEndPoints.class);
        if(cashierCreateQuery != null){
            Call<CashierCreateResponse> call = cashiersEndPoints.insertCashier(cashierCreateQuery);
            call.enqueue(new Callback<CashierCreateResponse>() {
                @Override
                public void onResponse(Call<CashierCreateResponse> call, Response<CashierCreateResponse> response) {
                    if(response.isSuccessful()){
                        callBack.onCashiersCreated(response.body());
                    } else {
                        callBack.onCashierCreateFail();
                    }
                }

                @Override
                public void onFailure(Call<CashierCreateResponse> call, Throwable t) {
                    callBack.onCashierCreateFail();
                }
            });
        }
    }

    @Override
    public void deleteCashier(CashierDeleteQuery cashierDeleteQuery, @NonNull final DeleteCashierCallBack callBack) {
        CashiersEndPoints cashiersEndPoints = mRetrofit.create(CashiersEndPoints.class);
        if(cashierDeleteQuery!=null){
            Call<CashierDeleteResponse> call = cashiersEndPoints.deleteCashier(cashierDeleteQuery);
            call.enqueue(new Callback<CashierDeleteResponse>() {
                @Override
                public void onResponse(Call<CashierDeleteResponse> call, Response<CashierDeleteResponse> response) {
                    if(response.isSuccessful()){
                        callBack.onCashierDeleted(response.body());
                    } else {
                        callBack.onCashierNotDeleted();
                    }
                }

                @Override
                public void onFailure(Call<CashierDeleteResponse> call, Throwable t) {
                    callBack.onCashierNotDeleted();
                }
            });
        }
    }

    private void getRemoteCashiers(CashiersEndPoints cashiersEndPoints,
                                   CashiersQuery cashiersQuery,
                                   final LoadCashiersCallback callback){
        Call<Cashiers> call = cashiersEndPoints.fetchCashiers(cashiersQuery);

        call.enqueue(new Callback<Cashiers>() {
            @Override
            public void onResponse(Call<Cashiers> call, Response<Cashiers> response) {
                if(response.isSuccessful()){
                    callback.onCashiersLoaded(response.body().getData());
                } else {
                    callback.onDataNotAvailable();
                }
            }

            @Override
            public void onFailure(Call<Cashiers> call, Throwable t) {
                callback.onDataNotAvailable();
            }
        });
    }

}
