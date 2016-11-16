package com.mobitill.mobitill_2.clientsdetail;

import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.mobitill.mobitill_2.data.models.clients.models.Client;

import javax.inject.Inject;

/**
 * Created by james on 10/25/2016.
 */

public class ClientsDetailPresenter implements ClientsDetailContract.Presenter {

    private static final String TAG = ClientsDetailPresenter.class.getSimpleName();
    private final ClientsDetailContract.View mView;
    private final ClientsAppId mAppId;
    @Nullable private ClientsJson mClientsJson;

    @Inject
    ClientsDetailPresenter(ClientsDetailContract.View view,
                           ClientsAppId appId,
                           @Nullable ClientsJson clientsJson){
        mView = view;
        mAppId = appId;
        mClientsJson = clientsJson;
    }

    @Override
    public void start() {
        if(mClientsJson!=null){
            mView.showClient(getClientFromGson());
        } else {
            mView.showMissingClient();
        }
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
    public Client getClientFromGson() {
        Client client = new Client();
        if(mClientsJson != null){
            Gson gson = new Gson();
            client = gson.fromJson(mClientsJson.getJson(), Client.class);
        }
        return client;
    }

    @Override
    public void editClient() {
        if(mClientsJson != null){
            mView.showEditClient(mClientsJson);
        }
    }

    @Override
    public void deleteClient() {

    }
}
