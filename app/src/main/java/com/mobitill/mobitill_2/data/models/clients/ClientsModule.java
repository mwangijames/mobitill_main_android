package com.mobitill.mobitill_2.data.models.clients;

import com.mobitill.mobitill_2.data.models.clients.models.Client;
import com.mobitill.mobitill_2.data.models.clients.models.Clients;
import com.mobitill.mobitill_2.data.models.clients.models.ClientsFetch;
import com.mobitill.mobitill_2.data.models.clients.models.ClientsParams;
import com.mobitill.mobitill_2.data.models.clients.models.ClientsQuery;
import com.mobitill.mobitill_2.data.models.clients.models.create.ClientCreateParams;
import com.mobitill.mobitill_2.data.models.clients.models.create.ClientCreateQuery;
import com.mobitill.mobitill_2.data.models.clients.models.create.ClientCreateResponse;
import com.mobitill.mobitill_2.data.models.clients.models.create.ClientCreateResponseData;
import com.mobitill.mobitill_2.data.models.clients.models.create.ClientEditQuery;
import com.mobitill.mobitill_2.data.models.clients.models.delete.ClientDeleteParams;
import com.mobitill.mobitill_2.data.models.clients.models.delete.ClientDeleteQuery;
import com.mobitill.mobitill_2.data.models.clients.models.delete.ClientDeleteResponse;
import com.mobitill.mobitill_2.data.models.clients.models.delete.ClientDeleteResponseData;

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

    @Provides
    ClientCreateParams provideClientCreateParams(){
        return new ClientCreateParams();
    }

    @Provides
    ClientCreateQuery provideClientCreateQuery(){
        return new ClientCreateQuery();
    }

    @Provides
    ClientCreateResponse provideClientCreateResponse(){
        return new ClientCreateResponse();
    }

    @Provides
    ClientCreateResponseData provideClientCreateResponseData(){
        return new ClientCreateResponseData();
    }

    @Provides
    ClientDeleteParams provideClientDeleteParams(){
        return new ClientDeleteParams();
    }

    @Provides
    ClientDeleteQuery provideClientDeleteQuery(){
        return new ClientDeleteQuery();
    }

    @Provides
    ClientDeleteResponse provideClientDeleteResponse(){
        return new ClientDeleteResponse();
    }

    @Provides
    ClientDeleteResponseData provideClientDeleteResponseData(){
        return new ClientDeleteResponseData();
    }

    @Provides
    ClientEditQuery provideClientEditQuery(){
        return new ClientEditQuery();
    }

}
