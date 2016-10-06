package com.mobitill.mobitill_2.data.models.fleet;

import android.support.annotation.NonNull;

import com.mobitill.mobitill_2.data.models.fleet.models.FleetItem;

import java.util.List;

/**
 * Created by james on 9/19/2016.
 */
public interface FleetDataSource {
    interface LoadFleetCallBack{
        void onFleetLoaded(List<FleetItem> fleetItemList);
        void onFleetNotLoaded();
    }

    void getFleet(String appId, @NonNull LoadFleetCallBack callBack);
}
