package com.mobitill.mobitill_2.apps;

import dagger.Module;
import dagger.Provides;

/**
 * Created by DI on 8/10/2016.
 */
@Module
public class AppsPresenterModule {
    private final AppsContract.View mView;

    public AppsPresenterModule(AppsContract.View view) {
        mView = view;
    }

    @Provides
    AppsContract.View providesAppsContractView(){
        return mView;
    }
}
