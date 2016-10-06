package com.mobitill.mobitill_2.clients;


import android.support.annotation.Nullable;

import dagger.Module;
import dagger.Provides;

/**
 * Created by james on 9/26/2016.
 */
@Module
public class ClientsPresenterModule {
    private final ClientsContract.View mView;
    private final String mAppId;

    public ClientsPresenterModule(ClientsContract.View view, @Nullable String appId){
        mView = view;
        mAppId = appId;
    }

    @Provides
    ClientsContract.View provideClientsContractView(){
        return mView;
    }

    @Provides
    @Nullable
    String provideAppId(){
        return mAppId;
    }
}
