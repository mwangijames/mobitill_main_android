package com.mobitill.mobitill_2.data.models.clients;

import android.support.annotation.NonNull;

import com.mobitill.mobitill_2.data.models.clients.models.Client;

import java.util.List;

/**
 * Created by james on 9/19/2016.
 */
public interface ClientsDataSource {
    interface LoadClientsCallBack{
        void onClientsLoaded(List<Client> clientList);
        void onClientsNotLoaded();
    }

    void getClients(String appId, @NonNull LoadClientsCallBack callBack);

}
