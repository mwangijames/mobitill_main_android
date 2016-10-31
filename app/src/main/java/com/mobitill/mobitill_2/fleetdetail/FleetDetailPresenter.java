package com.mobitill.mobitill_2.fleetdetail;

import android.support.annotation.Nullable;

import com.mobitill.mobitill_2.data.models.fleet.models.FleetItem;

import javax.inject.Inject;

/**
 * Created by james on 10/27/2016.
 */

public class FleetDetailPresenter implements FleetDetailContract.Presenter {

    private final FleetDetailContract.View mView;
    private final FleetAppId mAppId;
    @Nullable private FleetJson mFleetJson;

    @Inject
    FleetDetailPresenter(FleetDetailContract.View view,
                         FleetAppId appId,
                         @Nullable FleetJson fleetJson){
        mView = view;
        mAppId = appId;
        mFleetJson = fleetJson;
    }


    /**
     * Method injection is used here to safely reference {@code this} after the object is created.
     * For more information, see Java Concurrency in Practice.
     */
    @Inject
    void setupListeners() {
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public FleetItem getFleetItemFromJson() {
        return null;
    }

    @Override
    public void editFleetItem() {

    }

    @Override
    public void deleteFleetItem() {

    }
}
