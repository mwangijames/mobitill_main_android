package com.mobitill.mobitill_2.fleetaddedit;

import android.support.annotation.Nullable;

import dagger.Module;
import dagger.Provides;

/**
 * Created by james on 10/10/2016.
 */
@Module
public class FleetAddEditPresenterModule {
    private final FleetAddEditContract.View mView;
    private final String mAppId;

    public FleetAddEditPresenterModule(FleetAddEditContract.View view, String appId){
        mView = view;
        mAppId = appId;
    }

    @Provides
    FleetAddEditContract.View provideFleetAddEditView(){
        return mView;
    }

    @Provides
    @Nullable
    String provideAppId(){
        return mAppId;
    }

}
