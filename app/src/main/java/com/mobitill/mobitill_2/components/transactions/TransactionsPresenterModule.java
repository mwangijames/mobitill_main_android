package com.mobitill.mobitill_2.components.transactions;

import android.support.annotation.Nullable;

import dagger.Module;
import dagger.Provides;

/**
 * Created by andronicus on 5/5/2017.
 */

@Module
public class TransactionsPresenterModule {

    private final TransactionsContract.View mView;

    public TransactionsPresenterModule(
            TransactionsContract.View view
               ){
        mView = view;
    }
    @Provides
    TransactionsContract.View provideTransactionsView(){
        return mView;
    }
}
