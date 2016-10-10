package com.mobitill.mobitill_2.data.models.clients.remote;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.mobitill.mobitill_2.Constants;
import com.mobitill.mobitill_2.data.endpoints.ClientsEndPoints;
import com.mobitill.mobitill_2.data.models.clients.ClientsDataSource;
import com.mobitill.mobitill_2.data.models.clients.models.Clients;
import com.mobitill.mobitill_2.data.models.clients.models.ClientsFetch;
import com.mobitill.mobitill_2.data.models.clients.models.ClientsParams;
import com.mobitill.mobitill_2.data.models.clients.models.ClientsQuery;
import com.mobitill.mobitill_2.data.models.clients.models.create.ClientCreateQuery;
import com.mobitill.mobitill_2.data.models.clients.models.create.ClientCreateResponse;
import com.mobitill.mobitill_2.data.models.clients.models.delete.ClientDeleteQuery;
import com.mobitill.mobitill_2.data.models.clients.models.delete.ClientDeleteResponse;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by james on 9/19/2016.
 */
public class ClientsRemoteDataSource implements ClientsDataSource {

    private final Retrofit mRetrofit;
    private final SharedPreferences mSharedPreferences;
    private final Constants mConstants;
    private final ClientsFetch mClientsFetch;
    private final ClientsParams mClientsParams;
    private final ClientsQuery mClientsQuery;

    @Inject
    public ClientsRemoteDataSource(Retrofit retrofit, SharedPreferences sharedPreferences,
                                   Constants constants, ClientsQuery clientsQuery,
                                   ClientsParams clientsParams, ClientsFetch clientsFetch){
        mRetrofit = retrofit;
        mSharedPreferences = sharedPreferences;
        mConstants = constants;
        mClientsFetch = clientsFetch;
        mClientsParams = clientsParams;
        mClientsQuery = clientsQuery;
    }


    @Override
    public void getClients(String appId, @NonNull LoadClientsCallBack callBack) {
        ClientsEndPoints clientsEndPoints = mRetrofit.create(ClientsEndPoints.class);
        if(mClientsQuery != null && mClientsParams != null && mClientsFetch != null){
            mClientsFetch.setAppid(appId);
            mClientsParams.setFetch(mClientsFetch);
            mClientsQuery.setParams(mClientsParams);
            getRemoteClients(clientsEndPoints, mClientsQuery, callBack);
        }
    }

    @Override
    public void createClient(ClientCreateQuery clientCreateQuery, @NonNull final CreateClientCallBack callBack) {
        ClientsEndPoints clientsEndPoints = mRetrofit.create(ClientsEndPoints.class);
        if(clientCreateQuery != null){
            Call<ClientCreateResponse> call = clientsEndPoints.createClient(clientCreateQuery);
            call.enqueue(new Callback<ClientCreateResponse>() {
                @Override
                public void onResponse(Call<ClientCreateResponse> call, Response<ClientCreateResponse> response) {
                    if(response.isSuccessful()){
                        callBack.onClientCreated(response.body());
                    } else {
                        callBack.onClientNotCreated();
                    }
                }

                @Override
                public void onFailure(Call<ClientCreateResponse> call, Throwable t) {
                    callBack.onClientNotCreated();
                }
            });
        }
    }

    @Override
    public void deleteClient(ClientDeleteQuery clientDeleteQuery, @NonNull final DeleteClientCallBack callBack) {
        ClientsEndPoints clientsEndPoints = mRetrofit.create(ClientsEndPoints.class);
        if(clientDeleteQuery!=null){
            Call<ClientDeleteResponse> call = clientsEndPoints.deleteClient(clientDeleteQuery);
            call.enqueue(new Callback<ClientDeleteResponse>() {
                @Override
                public void onResponse(Call<ClientDeleteResponse> call, Response<ClientDeleteResponse> response) {
                    if(response.isSuccessful()){
                        callBack.onClientDeleted(response.body());
                    } else {
                        callBack.onClientNotDeleted();
                    }
                }

                @Override
                public void onFailure(Call<ClientDeleteResponse> call, Throwable t) {
                    callBack.onClientNotDeleted();
                }
            });
        }
    }


    private void getRemoteClients(ClientsEndPoints clientsEndPoints,
                                  ClientsQuery clientsQuery,
                                  final LoadClientsCallBack callBack){
        Call<Clients> call = clientsEndPoints.fetchClients(clientsQuery);
        call.enqueue(new Callback<Clients>() {
            @Override
            public void onResponse(Call<Clients> call, Response<Clients> response) {
                if(response.isSuccessful()){
                    callBack.onClientsLoaded(response.body().getData());
                } else {
                    callBack.onClientsNotLoaded();
                }
            }

            @Override
            public void onFailure(Call<Clients> call, Throwable t) {
                callBack.onClientsNotLoaded();
            }
        });
    }

}
