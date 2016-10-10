package com.mobitill.mobitill_2.fleetaddedit;

import android.support.annotation.Nullable;

import com.mobitill.mobitill_2.clientsaddedit.ClientAddEditPresenter;
import com.mobitill.mobitill_2.data.models.fleet.FleetRepository;

import javax.inject.Inject;

/**
 * Created by james on 10/10/2016.
 */

public class FleetAddEditPresenter implements FleetAddEditContract.Presenter {

    private static final String TAG = FleetAddEditPresenter.class.getSimpleName();
    private final FleetAddEditContract.View mView;
    private final FleetRepository mFleetRepository;
    @Nullable String mAppId;

    @Inject
    FleetAddEditPresenter(FleetAddEditContract.View view,
                          FleetRepository fleetRepository,
                          @Nullable String appId){
        mView = view;
        mFleetRepository = fleetRepository;
        mAppId = appId;
    }

    @Override
    public void saveFleetItem(String appId, String plateno, String fleetno) {

    }

    @Override
    public void start() {

    }
}
