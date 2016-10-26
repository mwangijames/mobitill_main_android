package com.mobitill.mobitill_2.data.models.clients;

import android.support.annotation.NonNull;

import com.mobitill.mobitill_2.data.Remote;
import com.mobitill.mobitill_2.data.models.clients.models.Client;
import com.mobitill.mobitill_2.data.models.clients.models.create.ClientCreateQuery;
import com.mobitill.mobitill_2.data.models.clients.models.create.ClientCreateResponse;
import com.mobitill.mobitill_2.data.models.clients.models.create.ClientEditQuery;
import com.mobitill.mobitill_2.data.models.clients.models.delete.ClientDeleteQuery;
import com.mobitill.mobitill_2.data.models.clients.models.delete.ClientDeleteResponse;

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

    @Override
    public void deleteClient(ClientDeleteQuery clientDeleteQuery, @NonNull final DeleteClientCallBack callBack) {
        mClientsRemoteDataSource.deleteClient(clientDeleteQuery, new DeleteClientCallBack() {
            @Override
            public void onClientDeleted(ClientDeleteResponse clientDeleteResponse) {
                callBack.onClientDeleted(clientDeleteResponse);
            }

            @Override
            public void onClientNotDeleted() {
                callBack.onClientNotDeleted();
            }
        });
    }

    @Override
    public void editClient(ClientEditQuery clientEditQuery, @NonNull final EditClientCallBack callBack) {
        mClientsRemoteDataSource.editClient(clientEditQuery, new EditClientCallBack() {
            @Override
            public void onClientEdited(ClientCreateResponse clientCreateResponse) {
                callBack.onClientEdited(clientCreateResponse);
            }

            @Override
            public void onClientNotEdited() {
                callBack.onClientNotEdited();
            }
        });
    }
}
