package com.mobitill.mobitill_2.cashiersaddedit;

import android.support.annotation.Nullable;

import com.mobitill.mobitill_2.data.models.cashiers.CashiersDataSource;
import com.mobitill.mobitill_2.data.models.cashiers.CashiersRepository;
import com.mobitill.mobitill_2.data.models.cashiers.models.Cashier;
import com.mobitill.mobitill_2.data.models.cashiers.models.create.CashierCreateParams;
import com.mobitill.mobitill_2.data.models.cashiers.models.create.CashierCreateQuery;
import com.mobitill.mobitill_2.data.models.cashiers.models.create.CashierCreateResponse;

import javax.inject.Inject;

/**
 * Created by james on 9/27/2016.
 */

public class AddEditCashierPresenter implements AddEditCashierContract.Presenter {

    private static final String TAG = AddEditCashierPresenter.class.getSimpleName();
    private final AddEditCashierContract.View mView;
    private final CashiersRepository mCashiersRepository;
    private CashierCreateQuery mCashierCreateQuery;
    private CashierCreateParams mCashierCreateParams;
    @Nullable String mAppId;

    @Inject
    AddEditCashierPresenter(AddEditCashierContract.View view,
                            CashiersRepository cashiersRepository,
                            @Nullable String appId,
                            CashierCreateQuery cashierCreateQuery,
                            CashierCreateParams cashierCreateParams){
        mView = view;
        mAppId = appId;
        mCashiersRepository = cashiersRepository;
        mCashierCreateQuery = cashierCreateQuery;
        mCashierCreateParams = cashierCreateParams;
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
    public void saveCashier(String appId, String name, String username, String password) {
        mCashierCreateParams.setAppid(appId);
        mCashierCreateParams.setName(name);
        mCashierCreateParams.setUsername(username);
        mCashierCreateParams.setPassword(password);

        if(appId == null){
            mView.showNoApplicationId();
        }

        if(mCashierCreateParams.isEmpty()){
            mView.showNoFields();
        } else {
            mCashierCreateQuery.setParams(mCashierCreateParams);
            mCashiersRepository.createCashier(mCashierCreateQuery, new CashiersDataSource.CreateCashiersCallBack() {
                @Override
                public void onCashiersCreated(CashierCreateResponse cashierCreateResponse) {
                    mView.showCashiersList();
                }

                @Override
                public void onCashierCreateFail() {
                    mView.showCashierCreateFailed();
                }
            });
        }
    }

    @Override
    public void populateCashier() {

    }

    @Override
    public void start() {

    }

}
