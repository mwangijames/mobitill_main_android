package com.mobitill.mobitill_2.cashiersdetail;

import android.support.annotation.Nullable;

import com.mobitill.mobitill_2.data.models.cashiers.models.Cashier;

import dagger.Module;
import dagger.Provides;

/**
 * Created by james on 10/13/2016.
 */
@Module
public class CashierDetailPresenterModule {
    private final CashierDetailContract.View mView;
    private final AppId mAppId;
    private CashierGson mCashierGson;

    public CashierDetailPresenterModule(CashierDetailContract.View view,
                                        AppId appId,
                                        CashierGson cashierGson){
        mView = view;
        mAppId = appId;
        mCashierGson = cashierGson;
    }

    @Provides
    CashierDetailContract.View provideCashierDetailContract(){
        return mView;
    }

    @Provides
    AppId provideAppId(){
        return mAppId;
    }

    @Provides
    CashierGson provideCashierGsonString(){
        return mCashierGson;
    }
}
