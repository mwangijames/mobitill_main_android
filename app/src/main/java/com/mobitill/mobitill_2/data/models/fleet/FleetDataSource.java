package com.mobitill.mobitill_2.data.models.fleet;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mobitill.mobitill_2.data.models.fleet.models.FleetItem;
import com.mobitill.mobitill_2.data.models.fleet.models.create.FleetCreateQuery;
import com.mobitill.mobitill_2.data.models.fleet.models.create.FleetCreateResponse;
import com.mobitill.mobitill_2.data.models.fleet.models.delete.FleetDeleteQuery;
import com.mobitill.mobitill_2.data.models.fleet.models.delete.FleetDeleteResponse;

import java.util.List;

/**
 * Created by james on 9/19/2016.
 */
public interface FleetDataSource {
    interface LoadFleetCallBack{
        void onFleetLoaded(List<FleetItem> fleetItemList);
        void onFleetNotLoaded();
    }

    interface CreateFleetCallBack{
        void onFleetCreated(FleetCreateResponse fleetCreateResponse);
        void onFleetNotCreated();
    }

    interface DeleteFleetCallBack{
        void onFleetDeleted(FleetDeleteResponse fleetDeleteResponse);
        void onFleetNotDeleted();
    }

    void getFleet(String appId, @NonNull LoadFleetCallBack callBack);
    void createFleet(FleetCreateQuery fleetCreateQuery, @NonNull CreateFleetCallBack callBack);
    void deleteFleet(FleetDeleteQuery fleetDeleteQuery, @NonNull DeleteFleetCallBack callBack);

}
