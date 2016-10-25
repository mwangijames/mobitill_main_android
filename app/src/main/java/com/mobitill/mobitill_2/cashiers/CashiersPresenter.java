package com.mobitill.mobitill_2.cashiers;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mobitill.mobitill_2.data.models.cashiers.CashiersDataSource;
import com.mobitill.mobitill_2.data.models.cashiers.CashiersRepository;
import com.mobitill.mobitill_2.data.models.cashiers.models.Cashier;
import com.mobitill.mobitill_2.data.models.cashiers.models.delete.CashierDeleteParams;
import com.mobitill.mobitill_2.data.models.cashiers.models.delete.CashierDeleteQuery;
import com.mobitill.mobitill_2.data.models.cashiers.models.delete.CashierDeleteResponse;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by james on 9/22/2016.
 */

public class CashiersPresenter implements CashiersContract.Presenter {

    private final static String TAG = CashiersPresenter.class.getSimpleName();

    private final CashiersContract.View mView;
    private final CashiersRepository mCashiersRepository;
    private final CashierDeleteQuery mCashierDeleteQuery;
    private final CashierDeleteParams mCashierDeleteParams;
    @Nullable String mAppId;

    @Inject
    CashiersPresenter(CashiersContract.View view,
                      @Nullable String appId,
                      CashiersRepository cashiersRepository,
                      CashierDeleteQuery cashierDeleteQuery,
                      CashierDeleteParams cashierDeleteParams){
        mView = view;
        mAppId = appId;
        mCashiersRepository = cashiersRepository;
        mCashierDeleteQuery = cashierDeleteQuery;
        mCashierDeleteParams = cashierDeleteParams;

    }

    /**
     * Method injection is used here to safely reference {@code this} after the object is created.
     * For more information, see Java Concurrency in Practice.
     */
    @Inject
    void setupListeners() {
        mView.setPresenter(this);
    }

    @Override
    public void fetchCashiers(String appId) {
        mCashiersRepository.getCashiers(appId, new CashiersDataSource.LoadCashiersCallback() {
            @Override
            public void onCashiersLoaded(List<Cashier> cashierList) {
                mView.showLoadingIndicator(false);
                mView.showCashiers(cashierList);
                mView.showNoCashiers(false);
                if(cashierList.isEmpty()){
                    mView.showNoCashiers(true);
                }
                for (Cashier cashier: cashierList) {
                   // Log.i(TAG, "onCashiersLoaded: " + cashier.getId());
                }
            }

            @Override
            public void onDataNotAvailable() {
                mView.showNoCashiers(true);
                mView.showLoadingIndicator(false);
                // Log.i(TAG, "onDataNotAvailable: " + "Data not available");
            }
        });
    }

    @Override
    public void addNewCashier(String appId) {
        mView.showAddCashier(appId);
    }

    // declare boolean to be used inside class
    @Override
    public void deleteCashier(String appId, final Cashier cashier) {

        if(mCashierDeleteParams!=null && cashier != null){
            mCashierDeleteParams.setAppid(appId);
            mCashierDeleteParams.setItemid(cashier.getId());
        }

        if(appId == null){
            // do something here for now just log a message
            Log.i(TAG, "deleteCashier: Failed No Application Id");
            return;
        }

        if(mCashierDeleteQuery!=null){
            mCashierDeleteQuery.setParams(mCashierDeleteParams);
            mCashiersRepository.deleteCashier(mCashierDeleteQuery, new CashiersDataSource.DeleteCashierCallBack() {
                @Override
                public void onCashierDeleted(CashierDeleteResponse cashierDeleteResponse) {
                    mView.showCashierDeleted(cashier);
                }

                @Override
                public void onCashierNotDeleted() {
                    mView.showCashierDeleteFailed(cashier.getName());
                }
            });
        }

    }

    @Override
    public void openCashierDetails(Cashier requestedCashier) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Log.i(TAG, "openCashierDetails: " + gson.toString());
        mView.showCashierDetailUi(gson.toJson(requestedCashier));
    }

    @Override
    public void start() {
        getCashiers();
    }

    private void getCashiers(){
        if(null == mAppId || mAppId.isEmpty()){
            mView.showNoCashiers(true);
            return;
        }

        mView.showLoadingIndicator(true);
        fetchCashiers(mAppId);
    }
}
