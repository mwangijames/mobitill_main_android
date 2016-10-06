package com.mobitill.mobitill_2.data.models.clients;

import android.content.SharedPreferences;

import com.mobitill.mobitill_2.Constants;
import com.mobitill.mobitill_2.data.Remote;
import com.mobitill.mobitill_2.data.models.clients.models.Client;
import com.mobitill.mobitill_2.data.models.clients.models.ClientsFetch;
import com.mobitill.mobitill_2.data.models.clients.models.ClientsParams;
import com.mobitill.mobitill_2.data.models.clients.models.ClientsQuery;
import com.mobitill.mobitill_2.data.models.clients.remote.ClientsRemoteDataSource;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by james on 9/19/2016.
 */
@Module
public class ClientsRepositoryModule {
    @Remote
    @Provides
    ClientsDataSource provideClientsRemoteDataSource(Retrofit retrofit, SharedPreferences sharedPreferences,
                                                     Constants constants, ClientsQuery clientsQuery,
                                                     ClientsParams clientsParams, ClientsFetch clientsFetch){
        return new ClientsRemoteDataSource(retrofit, sharedPreferences,
                constants, clientsQuery,
                clientsParams,clientsFetch);
    }
}
