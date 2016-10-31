package com.mobitill.mobitill_2.fleetdetail;

import com.mobitill.mobitill_2.BasePresenter;
import com.mobitill.mobitill_2.BaseView;
import com.mobitill.mobitill_2.data.models.fleet.models.FleetItem;

/**
 * Created by james on 10/27/2016.
 */

public interface FleetDetailContract {

    interface View extends BaseView<Presenter>{
        void showNoNetwork(Boolean show);
        void showNoFleetItem();
        void showFleetItem(FleetItem fleetItem);
        void showEditFleetItem(FleetJson fleetJson);
        boolean isActive();
    }

    interface Presenter extends BasePresenter{
        FleetItem getFleetItemFromJson();
        void editFleetItem();
        void deleteFleetItem();
    }

}
