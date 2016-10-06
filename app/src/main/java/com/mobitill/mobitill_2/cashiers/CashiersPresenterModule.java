package com.mobitill.mobitill_2.cashiers;

import android.support.annotation.Nullable;

import com.mobitill.mobitill_2.products.ProductsContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by james on 9/22/2016.
 */
@Module
public class CashiersPresenterModule {
    private final CashiersContract.View mView;
    private final String mAppId;

    public CashiersPresenterModule(CashiersContract.View view,
                                   @Nullable String appId){
        mView = view;
        mAppId = appId;
    }

    @Provides
    CashiersContract.View provideCashiersContractView(){
        return mView;
    }

    @Provides
    @Nullable
    String provideAppId(){
        return mAppId;
    }
}
