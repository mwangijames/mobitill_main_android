package com.mobitill.mobitill_2.reports;

import android.support.annotation.Nullable;

import com.mobitill.mobitill_2.menu.MenuAppSettings;

import dagger.Module;
import dagger.Provides;

/**
 * Created by james on 8/31/2016.
 */
@Module
public class ReportsPresenterModule {

    private final ReportsContract.View mView;
    private final String mAppId;
    private final MenuAppSettings mMenuAppSettings;

    public ReportsPresenterModule(ReportsContract.View view,
                                  @Nullable String appId,
                                  MenuAppSettings menuAppSettings)
    {
        mView = view;
        mAppId = appId;
        mMenuAppSettings = menuAppSettings;
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

    @Provides
    MenuAppSettings provideMenuAppSettings(){
        return mMenuAppSettings;
    }
}
