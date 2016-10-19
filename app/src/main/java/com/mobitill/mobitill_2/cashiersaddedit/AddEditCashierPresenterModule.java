package com.mobitill.mobitill_2.cashiersaddedit;

import android.support.annotation.Nullable;

import com.mobitill.mobitill_2.cashiers.CashiersPresenter;
import com.mobitill.mobitill_2.cashiers.CashiersPresenterModule;
import com.mobitill.mobitill_2.cashiersdetail.AppId;
import com.mobitill.mobitill_2.cashiersdetail.CashierGson;
import com.mobitill.mobitill_2.data.models.apps.models.App;
import com.mobitill.mobitill_2.data.models.cashiers.models.Cashier;

import dagger.Module;
import dagger.Provides;

/**
 * Created by james on 9/27/2016.
 */
@Module
public class AddEditCashierPresenterModule {
    private final AddEditCashierContract.View mView;
    private final AppId mAppId;
    private CashierGson mCashierGson;

    public AddEditCashierPresenterModule(AddEditCashierContract.View view,
                                         AppId appId,
                                         CashierGson cashierGson
                                         ){
        mView =view;
        mAppId = appId;
        mCashierGson = cashierGson;
    }

    @Provides
    AddEditCashierContract.View provideAddEditCashierView(){
        return mView;
    }

    @Provides
    AppId provideAppId(){
        return mAppId;
    }

    @Provides
    @Nullable
    CashierGson provideCashierGson(){
        return mCashierGson;
    }
}
