package com.mobitill.mobitill_2.data.models.fleet;

import android.support.annotation.NonNull;

import com.mobitill.mobitill_2.data.Remote;
import com.mobitill.mobitill_2.data.models.fleet.models.FleetItem;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by james on 9/19/2016.
 */
public class FleetRepository implements FleetDataSource {

    private final FleetDataSource mFleetRemoteDataSource;

    @Inject
    FleetRepository(@Remote FleetDataSource fleetRemoteDataSource){
        mFleetRemoteDataSource = fleetRemoteDataSource;
    }


    @Override
    public void getFleet(String appId, @NonNull final LoadFleetCallBack callBack) {
        mFleetRemoteDataSource.getFleet(appId, new LoadFleetCallBack() {
            @Override
            public void onFleetLoaded(List<FleetItem> fleetItemList) {
                callBack.onFleetLoaded(fleetItemList);
            }

            @Override
            public void onFleetNotLoaded() {
                callBack.onFleetNotLoaded();
            }

        });
    }
}
