package com.mobitill.mobitill_2.data.models.clients;

import android.support.annotation.NonNull;

import com.mobitill.mobitill_2.data.models.clients.models.Client;
import com.mobitill.mobitill_2.data.models.clients.models.create.ClientCreateQuery;
import com.mobitill.mobitill_2.data.models.clients.models.create.ClientCreateResponse;

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

    void getClients(String appId, @NonNull LoadClientsCallBack callBack);

    void createClient(ClientCreateQuery clientCreateQuery, @NonNull CreateClientCallBack callBack);


}
