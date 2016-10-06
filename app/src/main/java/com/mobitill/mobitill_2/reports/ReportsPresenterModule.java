package com.mobitill.mobitill_2.reports;

import android.support.annotation.Nullable;

import dagger.Module;
import dagger.Provides;

/**
 * Created by james on 8/31/2016.
 */
@Module
public class ReportsPresenterModule {

    private final ReportsContract.View mView;
    private final String mAppId;

    public ReportsPresenterModule(ReportsContract.View view,
                                  @Nullable String appId)
    {
        mView = view;
        mAppId = appId;
    }

    @Provides
    ReportsContract.View provideReportsContractView(){
        return mView;
    }

    @Provides
    @Nullable
    String provideAppId(){
        return mAppId;
    }
}
