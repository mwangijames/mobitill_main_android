package com.mobitill.mobitill_2.fleetdetail;

import android.support.annotation.Nullable;

import dagger.Module;
import dagger.Provides;

/**
 * Created by james on 10/27/2016.
 */
@Module
public class FleetDetailPresenterModule {
    private final FleetDetailContract.View mView;
    private final FleetAppId mAppId;
    private FleetJson mFleetJson;

    public FleetDetailPresenterModule(FleetDetailContract.View view,
                                      FleetAppId appId,
                                      @Nullable FleetJson fleetJson){
        mView = view;
        mAppId = appId;
        mFleetJson = fleetJson;
    }

    @Provides
    FleetDetailContract.View provideFleetDetailContract(){
        return mView;
    }

    @Provides
    FleetAppId provideAppId(){
        return mAppId;
    }

    @Provides
    @Nullable
    FleetJson provideFleetJson(){
        return mFleetJson;
    }
}
