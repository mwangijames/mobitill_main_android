package com.mobitill.mobitill_2.products;

import android.support.annotation.Nullable;

import dagger.Module;
import dagger.Provides;

/**
 * Created by james on 9/19/2016.
 */
@Module
public class ProductsPresenterModule{

    private final ProductsContract.View mView;
    private final String mAppId;

    public ProductsPresenterModule(ProductsContract.View view,
                                   @Nullable String appId){
        mView = view;
        mAppId = appId;
    }

    @Provides
    ProductsContract.View provideProductsContract(){
        return mView;
    }

    @Provides
    @Nullable
    String provideAppId(){
        return mAppId;
    }
}
