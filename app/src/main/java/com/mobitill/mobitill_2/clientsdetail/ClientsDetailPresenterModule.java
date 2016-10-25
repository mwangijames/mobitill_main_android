package com.mobitill.mobitill_2.clientsdetail;

import android.support.annotation.Nullable;

import dagger.Module;
import dagger.Provides;

/**
 * Created by james on 10/25/2016.
 */
@Module
public class ClientsDetailPresenterModule {

    private final ClientsDetailContract.View mView;
    private final ClientsAppId mAppId;
    private ClientsJson mClientsJson;


    public ClientsDetailPresenterModule(ClientsDetailContract.View view,
                                        ClientsAppId appId,
                                        @Nullable ClientsJson clientsJson){
        mView = view;
        mAppId = appId;
        mClientsJson = clientsJson;
    }

    @Provides
    ClientsDetailContract.View provideClientsDetailContract(){
        return mView;
    }

    @Provides
    ClientsAppId provideAppId(){
        return mAppId;
    }

    @Provides
    @Nullable
    ClientsJson provideClientsJson(){
        return mClientsJson;
    }

}
