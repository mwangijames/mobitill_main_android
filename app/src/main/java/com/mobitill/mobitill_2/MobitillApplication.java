package com.mobitill.mobitill_2;

import com.mobitill.mobitill_2.data.RealmModule;
import com.mobitill.mobitill_2.data.models.apps.AppsModule;
import com.mobitill.mobitill_2.data.models.apps.AppsRepositoryComponent;
import com.mobitill.mobitill_2.data.models.apps.AppsRepositoryModule;
import com.mobitill.mobitill_2.data.models.apps.DaggerAppsRepositoryComponent;
import com.mobitill.mobitill_2.data.models.users.UserModule;
import com.mobitill.mobitill_2.net.ConnectivityReceiver;
import com.mobitill.mobitill_2.net.NetModule;
import com.orm.SugarApp;

/**
 * Created by DI on 8/5/2016.
 */
public class MobitillApplication extends SugarApp {

    private BaseComponent mBaseComponent;
    private AppsRepositoryComponent mAppsRepositoryComponent;


    private static MobitillApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;

        FontsOverride.setDefaultFont(this, "MONOSPACE", "fonts/Roboto-Regular.ttf");


        mBaseComponent = DaggerBaseComponent.builder()
                .applicationModule(new ApplicationModule(getApplicationContext()))
                .netModule(new NetModule("http://api.mobitill.com/v1/"))
                .userModule(new UserModule())
                .constantsModule(new ConstantsModule())
                .appsModule(new AppsModule())
                .realmModule(new RealmModule())
                .appsRepositoryModule(new AppsRepositoryModule())
                .build();

        mAppsRepositoryComponent = DaggerAppsRepositoryComponent.builder()
                .appsRepositoryModule(new AppsRepositoryModule())
                .baseComponent(getBaseComponent())
                .build();

    }

    public BaseComponent getBaseComponent(){
        return mBaseComponent;
    }

    public AppsRepositoryComponent getAppsRepositoryComponent(){
        return mAppsRepositoryComponent;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener){
        ConnectivityReceiver.sConnectivityReceiverListener = listener;
    }

    public static synchronized MobitillApplication getInstance(){
        return mInstance;
    }

}
