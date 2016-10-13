package com.mobitill.mobitill_2.fleetaddedit;

import android.support.annotation.Nullable;

import com.mobitill.mobitill_2.clientsaddedit.ClientAddEditPresenter;
import com.mobitill.mobitill_2.data.models.fleet.FleetDataSource;
import com.mobitill.mobitill_2.data.models.fleet.FleetRepository;
import com.mobitill.mobitill_2.data.models.fleet.models.create.FleetCreateParams;
import com.mobitill.mobitill_2.data.models.fleet.models.create.FleetCreateQuery;
import com.mobitill.mobitill_2.data.models.fleet.models.create.FleetCreateResponse;

import javax.inject.Inject;

/**
 * Created by james on 10/10/2016.
 */

public class FleetAddEditPresenter implements FleetAddEditContract.Presenter {

    private static final String TAG = FleetAddEditPresenter.class.getSimpleName();
    private final FleetAddEditContract.View mView;
    private final FleetRepository mFleetRepository;
    private FleetCreateParams mFleetCreateParams;
    private FleetCreateQuery mFleetCreateQuery;
    @Nullable String mAppId;

    @Inject
    FleetAddEditPresenter(FleetAddEditContract.View view,
                          FleetRepository fleetRepository,
                          FleetCreateParams fleetCreateParams,
                          FleetCreateQuery fleetCreateQuery,
                          @Nullable String appId){
        mView = view;
        mFleetRepository = fleetRepository;
        mAppId = appId;
        mFleetCreateParams = fleetCreateParams;
        mFleetCreateQuery = fleetCreateQuery;
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
    public void saveFleetItem(String appId, String plateno, String fleetno) {
        if(appId == null || appId.isEmpty()){
            return;
        }

        if(mFleetCreateParams != null){
            mFleetCreateParams.setAppid(appId);
            mFleetCreateParams.setPlateno(plateno);
            mFleetCreateParams.setFleetno(fleetno);

            if(mFleetCreateParams.isEmpty()){
                mView.showNoFields();
            } else {
                if(mFleetCreateQuery!=null){
                    mFleetCreateQuery.setParams(mFleetCreateParams);
                    mFleetRepository.createFleet(mFleetCreateQuery, new FleetDataSource.CreateFleetCallBack() {
                        @Override
                        public void onFleetCreated(FleetCreateResponse fleetCreateResponse) {
                            mView.showFleetItemCreated(fleetCreateResponse.getData());
                            mView.showFleetList();
                        }

                        @Override
                        public void onFleetNotCreated() {
                            mView.showFleetCreateFailed();
                        }
                    });
                }
            }
        }
    }

    @Override
    public void start() {

    }
}
