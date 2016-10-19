package com.mobitill.mobitill_2.cashiersdetail;

import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.mobitill.mobitill_2.cashiersaddedit.AddEditCashierPresenter;
import com.mobitill.mobitill_2.data.models.cashiers.CashiersRepository;
import com.mobitill.mobitill_2.data.models.cashiers.models.Cashier;

import javax.inject.Inject;

/**
 * Created by james on 10/13/2016.
 */

public class CashierDetailPresenter implements CashierDetailContract.Presenter{

    private static final String TAG = CashierDetailPresenter.class.getSimpleName();
    private final CashierDetailContract.View mView;
    private final CashiersRepository mCashiersRepository;
    private final AppId  mAppId;
    private CashierGson  mCashierGson;
//
    @Inject
    CashierDetailPresenter(CashierDetailContract.View view,
                           CashiersRepository cashiersRepository,
                           AppId appId,
                           CashierGson  cashierGson){
        mView = view;
        mCashiersRepository = cashiersRepository;
        mAppId = appId;
        mCashierGson = cashierGson;
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
    public void convertJson(String cashierGsonString) {
        Gson gson = new Gson();
        Cashier cashier = gson.fromJson(cashierGsonString, Cashier.class);
        if(cashier != null) {
            mView.showCashier(cashier);
        }
    }

    @Override
    public void editCashier() {
        if(mCashierGson!=null){
            if(!mCashierGson.getCashierGson().isEmpty()){
                mView.showEditCashier(mCashierGson.getCashierGson());
            } else {
                mView.showMissingCashier();
            }
        } else {
            mView.showMissingCashier();
        }
    }

    @Override
    public void deleteCashier() {

    }

    @Override
    public void start() {
        if(mAppId == null){
            return;
        }
        if(mCashierGson != null){
            convertJson(mCashierGson.getCashierGson());
        } else {
            mView.showMissingCashier();
        }
    }
}
