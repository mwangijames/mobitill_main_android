package com.mobitill.mobitill_2.clientsaddedit;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;

import com.mobitill.mobitill_2.clientsdetail.ClientsAppId;
import com.mobitill.mobitill_2.clientsdetail.ClientsJson;

import dagger.Module;
import dagger.Provides;

/**
 * Created by james on 10/7/2016.
 */
@Module
public class ClientAddEditPresenterModule {
    private final ClientAddEditContract.View mView;
    private final ClientsAppId mAppId;
    private final ClientsJson mClientsJson;

    public ClientAddEditPresenterModule(ClientAddEditContract.View view,
                                        ClientsAppId appId,
                                        ClientsJson clientsJson){

        mView = view;
        mAppId = appId;
        mClientsJson = clientsJson;
    }

    @Provides
    ClientAddEditContract.View provideClientAddEditView(){
        return mView;
    }

    @Provides
    @Nullable
    ClientsAppId provideAppId(){
        return mAppId;
    }

    @Provides
    @Nullable
    ClientsJson provideClientsJson(){
        return mClientsJson;
    }
}
