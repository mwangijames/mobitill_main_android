package com.mobitill.mobitill_2.fleet;

import android.support.annotation.Nullable;

import com.mobitill.mobitill_2.data.models.fleet.FleetDataSource;
import com.mobitill.mobitill_2.data.models.fleet.FleetRepository;
import com.mobitill.mobitill_2.data.models.fleet.models.FleetItem;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by james on 9/27/2016.
 */

public class FleetPresenter implements FleetContract.Presenter {

    private static final String TAG = FleetPresenter.class.getSimpleName();

    private final FleetContract.View mView;
    private final FleetRepository mFleetRepository;
    @Nullable String mAppId;

    @Inject
    FleetPresenter(FleetContract.View view,
                   @Nullable String appId,
                   FleetRepository fleetRepository){
        mView = view;
        mFleetRepository = fleetRepository;
        mAppId = appId;
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
    public void fetchFleet(String appId) {
        mFleetRepository.getFleet(appId, new FleetDataSource.LoadFleetCallBack() {
            @Override
            public void onFleetLoaded(List<FleetItem> fleetItemList) {
                mView.showLoadingIndicator(false);
                if(fleetItemList.isEmpty()){
                    mView.showNoFleet(true);
                } else {
                    mView.showNoFleet(false);
                    mView.showFleet(fleetItemList);
                }

            }
            @Override
            public void onFleetNotLoaded() {
                mView.showLoadingIndicator(false);
                mView.showNoFleet(true);
            }
        });
    }



    @Override
    public void start() {
        if(null == mAppId || mAppId.isEmpty()){
            return;
        }

        mView.showLoadingIndicator(true);
        fetchFleet(mAppId);
    }
}
