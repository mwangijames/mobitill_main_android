package com.mobitill.mobitill_2.data.models.clients;

import android.support.annotation.NonNull;

import com.mobitill.mobitill_2.data.models.clients.models.Client;
import com.mobitill.mobitill_2.data.models.clients.models.create.ClientCreateQuery;
import com.mobitill.mobitill_2.data.models.clients.models.create.ClientCreateResponse;
import com.mobitill.mobitill_2.data.models.clients.models.create.ClientEditQuery;
import com.mobitill.mobitill_2.data.models.clients.models.delete.ClientDeleteQuery;
import com.mobitill.mobitill_2.data.models.clients.models.delete.ClientDeleteResponse;

import java.util.List;

/**
 * Created by james on 9/19/2016.
 */
public interface ClientsDataSource {
    interface LoadClientsCallBack{
        void onClientsLoaded(List<Client> clientList);
        void onClientsNotLoaded();
    }

    interface CreateClientCallBack{
        void onClientCreated(ClientCreateResponse clientCreateResponse);
        void onClientNotCreated();
    }

    interface EditClientCallBack{
        void onClientEdited(ClientCreateResponse clientCreateResponse);
        void onClientNotEdited();
    }

    interface DeleteClientCallBack{
        void onClientDeleted(ClientDeleteResponse clientDeleteResponse);
        void onClientNotDeleted();
    }

    void getClients(String appId, @NonNull LoadClientsCallBack callBack);

    void createClient(ClientCreateQuery clientCreateQuery, @NonNull CreateClientCallBack callBack);

    void deleteClient(ClientDeleteQuery clientDeleteQuery, @NonNull DeleteClientCallBack callBack);

    void editClient(ClientEditQuery clientEditQuery, @NonNull EditClientCallBack callBack);
}
