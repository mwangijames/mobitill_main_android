package com.mobitill.mobitill_2.fleet;

import com.mobitill.mobitill_2.BasePresenter;
import com.mobitill.mobitill_2.BaseView;
import com.mobitill.mobitill_2.data.models.fleet.models.Fleet;
import com.mobitill.mobitill_2.data.models.fleet.models.FleetItem;

import java.util.List;

/**
 * Created by james on 9/27/2016.
 */

public interface FleetContract {

    interface View extends BaseView<Presenter>{
        void showLoadingIndicator(boolean show);
        void showNoFleet(boolean show);
        void showNoNetwork(boolean show);
        void showFleetItemDeleted(FleetItem fleetItem);
        void showFleetItemDeletedFailed(String fleetno);
        void hideTitle();
        void showTitle();
        void showFleet(List<FleetItem> fleetItems);
        boolean isActive();
    }

    interface Presenter extends BasePresenter{
        void fetchFleet(String appId);
        void deleteFleetItem(String appId, FleetItem fleetItem);
    }

}
