package com.mobitill.mobitill_2.data.models.clients;

import com.mobitill.mobitill_2.data.models.clients.models.Client;
import com.mobitill.mobitill_2.data.models.clients.models.Clients;
import com.mobitill.mobitill_2.data.models.clients.models.ClientsFetch;
import com.mobitill.mobitill_2.data.models.clients.models.ClientsParams;
import com.mobitill.mobitill_2.data.models.clients.models.ClientsQuery;

import dagger.Module;
import dagger.Provides;

/**
 * Created by james on 9/19/2016.
 */
@Module
public class ClientsModule {
    @Provides
    Client provideClient(){
        return new Client();
    }
    @Provides
    Clients provideClients(){
        return new Clients();
    }

    @Provides
    ClientsFetch provideClientsFetch(){
        return new ClientsFetch();
    }

    @Provides
    ClientsParams provideClientsParams(){
        return new ClientsParams();
    }

    @Provides
    ClientsQuery provideClientsQuery(){
        return new ClientsQuery();
    }
}
