package com.mobitill.mobitill_2.clientsaddedit;

import android.support.design.widget.TabLayout;

import dagger.Module;
import dagger.Provides;

/**
 * Created by james on 10/7/2016.
 */
@Module
public class ClientAddEditPresenterModule {
    private final ClientAddEditContract.View mView;
    private final String mAppId;

    public ClientAddEditPresenterModule(ClientAddEditContract.View view,
                                        String appId){
        mView = view;
        mAppId = appId;
    }

    @Provides
    ClientAddEditContract.View provideClientAddEditView(){
        return mView;
    }

    @Provides
    String provideAppId(){
        return mAppId;
    }
}
