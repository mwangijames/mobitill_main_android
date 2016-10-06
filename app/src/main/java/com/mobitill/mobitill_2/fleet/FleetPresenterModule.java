package com.mobitill.mobitill_2.fleet;

import android.support.annotation.Nullable;

import dagger.Module;
import dagger.Provides;

/**
 * Created by james on 9/27/2016.
 */
@Module
public class FleetPresenterModule {
    private final FleetContract.View mView;
    private final String mAppId;

    public FleetPresenterModule(FleetContract.View view,
                                @Nullable String appId){
        mAppId = appId;
        mView = view;
    }

    @Provides
    FleetContract.View privetFleetView(){
        return mView;
    }

    @Provides
    @Nullable
    String provideAppId(){
        return mAppId;
    }

}
