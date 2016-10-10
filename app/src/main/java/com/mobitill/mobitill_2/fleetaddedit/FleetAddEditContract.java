package com.mobitill.mobitill_2.fleetaddedit;

import com.mobitill.mobitill_2.BasePresenter;
import com.mobitill.mobitill_2.BaseView;
import com.mobitill.mobitill_2.clientsaddedit.ClientAddEditContract;

/**
 * Created by james on 10/10/2016.
 */

public interface FleetAddEditContract {
    interface  View extends BaseView<Presenter>{
        void showNoNetwork(boolean show);
        void showTitle();
        void showFleetList();
        void showFleetCreateFailed();
        void showNoFields();
        boolean isActive();
    }

    interface Presenter extends BasePresenter{
        void saveFleetItem(String appId, String plateno, String fleetno);
    }
}
