package com.mobitill.mobitill_2.data.models.clients;

import android.support.annotation.NonNull;

import com.mobitill.mobitill_2.data.Remote;
import com.mobitill.mobitill_2.data.models.clients.models.Client;
import com.mobitill.mobitill_2.data.models.clients.models.create.ClientCreateQuery;
import com.mobitill.mobitill_2.data.models.clients.models.create.ClientCreateResponse;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by james on 9/19/2016.
 */
public class ClientsRepository implements ClientsDataSource {


    private final ClientsDataSource mClientsRemoteDataSource;

    @Inject
    ClientsRepository(@Remote ClientsDataSource clientsRemoteDataSource){
        mClientsRemoteDataSource = clientsRemoteDataSource;
    }

    @Override
    public void getClients(String appId, @NonNull final LoadClientsCallBack callBack) {
        mClientsRemoteDataSource.getClients(appId, new LoadClientsCallBack() {
            @Override
            public void onClientsLoaded(List<Client> clientList) {
                callBack.onClientsLoaded(clientList);
            }

            @Override
            public void onClientsNotLoaded() {
                callBack.onClientsNotLoaded();
            }
        });
    }

    @Override
    public void createClient(final ClientCreateQuery clientCreateQuery, @NonNull final CreateClientCallBack callBack) {
        mClientsRemoteDataSource.createClient(clientCreateQuery, new CreateClientCallBack() {
            @Override
            public void onClientCreated(ClientCreateResponse clientCreateResponse) {
                callBack.onClientCreated(clientCreateResponse);
            }

            @Override
            public void onClientNotCreated() {
                callBack.onClientNotCreated();
            }
        });
    }
}
