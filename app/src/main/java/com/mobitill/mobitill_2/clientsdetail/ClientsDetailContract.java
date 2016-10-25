package com.mobitill.mobitill_2.clientsdetail;

import com.mobitill.mobitill_2.BasePresenter;
import com.mobitill.mobitill_2.BaseView;
import com.mobitill.mobitill_2.data.models.clients.models.Client;

/**
 * Created by james on 10/25/2016.
 */

public interface ClientsDetailContract {
    interface View extends BaseView<Presenter>{
        void showNoNetwork(boolean show);
        void showMissingClient();
        void showClient(Client client);
        void showEditClient(ClientsJson clientsJson);
        boolean isActive();
    }

    interface Presenter extends BasePresenter{
        Client getClientFromGson();
        void editClient();
        void deleteClient();
    }
}
