package com.mobitill.mobitill_2.clients;

import com.mobitill.mobitill_2.BasePresenter;
import com.mobitill.mobitill_2.BaseView;
import com.mobitill.mobitill_2.clientsdetail.ClientsAppId;
import com.mobitill.mobitill_2.clientsdetail.ClientsJson;
import com.mobitill.mobitill_2.data.models.clients.models.Client;
import com.mobitill.mobitill_2.data.models.clients.models.Clients;

import java.util.List;

/**
 * Created by james on 9/26/2016.
 */

public interface ClientsContract {

    interface View extends BaseView<Presenter> {
        void showLoadingIndicator(boolean show);
        void showNoClients(boolean show);
        void showNoNetwork(boolean show);
        void showAddClient(ClientsAppId appId);
        void showClientDeleted(Client client);
        void showClientDeleteFailed(String name);
        void showClientDetailView(ClientsAppId appId, ClientsJson clientsJson);
        void hideTitle();
        void showTitle();
        void showClients(List<Client> clients);
        boolean isActive();
    }

    interface Presenter extends BasePresenter{
        void fetchClients(String appId);
        void addNewClient(String appId);
        void deleteClient(String appId, Client client);
        void showClientDetailView(Client requestedClient);
    }
}
