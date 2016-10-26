package com.mobitill.mobitill_2.clientsaddedit;

import com.mobitill.mobitill_2.BasePresenter;
import com.mobitill.mobitill_2.BaseView;
import com.mobitill.mobitill_2.clientsdetail.ClientsAppId;
import com.mobitill.mobitill_2.data.models.clients.models.create.ClientCreateResponseData;

/**
 * Created by james on 10/7/2016.
 */

public interface ClientAddEditContract {

    interface View extends BaseView<Presenter>{
        void showNoNetwork(boolean show);
        void showTitle();
        void showClientsList();
        void showClientCreatedFailed();
        void showClientCreated(ClientCreateResponseData clientCreateResponseData);
        void showNoApplicationId();
        void showNoFields();
        void setEmail(String email);
        void setName(String name);
        void setPhone(String phone);
        boolean isActive();
    }

    interface Presenter extends BasePresenter{
        void saveClient(ClientsAppId appId, String email, String name, String phone);
    }

}
