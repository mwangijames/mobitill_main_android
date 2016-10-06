package com.mobitill.mobitill_2.cashiersaddedit;

import android.support.annotation.Nullable;

import com.mobitill.mobitill_2.cashiers.CashiersPresenter;
import com.mobitill.mobitill_2.cashiers.CashiersPresenterModule;

import dagger.Module;
import dagger.Provides;

/**
 * Created by james on 9/27/2016.
 */
@Module
public class AddEditCashierPresenterModule {
    // TODO: 9/27/2016 start here
    private final AddEditCashierContract.View mView;
    private final String mAppId;

    public AddEditCashierPresenterModule(AddEditCashierContract.View view,
                                         @Nullable  String appId){
        mView =view;
        mAppId = appId;
    }

    @Provides
    AddEditCashierContract.View provideAddEditCashierView(){
        return mView;
    }

    @Provides
    @Nullable
    String provideAppId(){
        return mAppId;
    }
}
