package com.mobitill.mobitill_2.data.models.fleet;

import android.support.annotation.NonNull;

import com.mobitill.mobitill_2.data.Remote;
import com.mobitill.mobitill_2.data.models.fleet.models.FleetItem;
import com.mobitill.mobitill_2.data.models.fleet.models.create.FleetCreateQuery;
import com.mobitill.mobitill_2.data.models.fleet.models.create.FleetCreateResponse;
import com.mobitill.mobitill_2.data.models.fleet.models.delete.FleetDeleteQuery;
import com.mobitill.mobitill_2.data.models.fleet.models.delete.FleetDeleteResponse;

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

    @Override
    public void createFleet(FleetCreateQuery fleetCreateQuery, @NonNull final CreateFleetCallBack callBack) {
        mFleetRemoteDataSource.createFleet(fleetCreateQuery, new CreateFleetCallBack() {
            @Override
            public void onFleetCreated(FleetCreateResponse fleetCreateResponse) {
                callBack.onFleetCreated(fleetCreateResponse);
            }

            @Override
            public void onFleetNotCreated() {
                callBack.onFleetNotCreated();
            }
        });
    }

    @Override
    public void deleteFleet(FleetDeleteQuery fleetDeleteQuery, @NonNull final DeleteFleetCallBack callBack) {
        mFleetRemoteDataSource.deleteFleet(fleetDeleteQuery, new DeleteFleetCallBack() {
            @Override
            public void onFleetDeleted(FleetDeleteResponse fleetDeleteResponse) {
                callBack.onFleetDeleted(fleetDeleteResponse);
            }

            @Override
            public void onFleetNotDeleted() {
                callBack.onFleetNotDeleted();
            }
        });
    }
}
