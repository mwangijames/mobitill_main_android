package com.mobitill.mobitill_2.clients;

import android.support.annotation.Nullable;
import android.util.Log;

import com.mobitill.mobitill_2.data.models.clients.ClientsDataSource;
import com.mobitill.mobitill_2.data.models.clients.ClientsRepository;
import com.mobitill.mobitill_2.data.models.clients.models.Client;
import com.mobitill.mobitill_2.data.models.clients.models.delete.ClientDeleteParams;
import com.mobitill.mobitill_2.data.models.clients.models.delete.ClientDeleteQuery;
import com.mobitill.mobitill_2.data.models.clients.models.delete.ClientDeleteResponse;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by james on 9/26/2016.
 */

public class ClientsPresenter implements ClientsContract.Presenter {

    private static final String TAG = ClientsPresenter.class.getSimpleName();

    private final ClientsContract.View mView;
    private final ClientsRepository mClientsRepository;
    private final ClientDeleteQuery mClientDeleteQuery;
    private final ClientDeleteParams mClientDeleteParams;
    @Nullable String mAppId;

    @Inject
    ClientsPresenter(ClientsContract.View view,
                      @Nullable String appId,
                      ClientsRepository clientsRepository,
                      ClientDeleteQuery clientDeleteQuery,
                      ClientDeleteParams clientDeleteParams){
        mView = view;
        mAppId = appId;
        mClientsRepository = clientsRepository;
        mClientDeleteQuery = clientDeleteQuery;
        mClientDeleteParams = clientDeleteParams;
    }

    /**
     * Method injection is used here to safely reference {@code this} after the object is created.
     * For more information, see Java Concurrency in Practice.
     */
    @Inject
    void setupListeners() {
        mView.setPresenter(this);
    }


    @Override
    public void fetchClients(String appId) {
        mClientsRepository.getClients(appId, new ClientsDataSource.LoadClientsCallBack() {
            @Override
            public void onClientsLoaded(List<Client> clientList) {
                mView.showLoadingIndicator(false);
                if(clientList.isEmpty()){
                    mView.showNoClients(true);
                } else {
                    mView.showNoClients(false);
                    mView.showClients(clientList);
                }
            }

            @Override
            public void onClientsNotLoaded() {
                mView.showLoadingIndicator(false);
                mView.showNoClients(true);
            }
        });
    }

    @Override
    public void addNewClient(String appId) {
        mView.showAddClient(appId);
    }

    @Override
    public void deleteClient(String appId, final Client client) {
        if(appId == null || appId.isEmpty()){
            return;
        }

        if(mClientDeleteParams != null && client != null){
            mClientDeleteParams.setAppid(appId);
            mClientDeleteParams.setItemid(client.getId());

            if(mClientDeleteQuery != null){
                mClientDeleteQuery.setParams(mClientDeleteParams);
                mClientsRepository.deleteClient(mClientDeleteQuery, new ClientsDataSource.DeleteClientCallBack() {
                    @Override
                    public void onClientDeleted(ClientDeleteResponse clientDeleteResponse) {
                        mView.showClientDeleted(client);
                    }

                    @Override
                    public void onClientNotDeleted() {
                        mView.showClientDeleteFailed(client.getName());
                    }
                });
            }
        }
    }

    @Override
    public void start() {

        if(null == mAppId || mAppId.isEmpty()){
            return;
        }

        mView.showLoadingIndicator(true);
        fetchClients(mAppId);

    }
}
