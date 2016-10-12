package com.mobitill.mobitill_2.productsaddedit;

import android.support.annotation.Nullable;

import dagger.Module;
import dagger.Provides;

/**
 * Created by james on 10/12/2016.
 */
@Module
public class ProductAddEditPresenterModule {

    private final ProductAddEditContract.View mView;
    private final String mAppId;

    public ProductAddEditPresenterModule(ProductAddEditContract.View view,
                                         String appId){
        mView = view;
        mAppId = appId;
    }

    @Provides
    ProductAddEditContract.View provideProductAddEditView(){
        return mView;
    }

    @Provides
    @Nullable
    String provideAppId(){
        return mAppId;
    }

}

