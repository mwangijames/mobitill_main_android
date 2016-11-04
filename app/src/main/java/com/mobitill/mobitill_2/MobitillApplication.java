package com.mobitill.mobitill_2;

import android.app.Application;


import com.mobitill.mobitill_2.data.RealmModule;
import com.mobitill.mobitill_2.data.models.apps.AppsModule;
import com.mobitill.mobitill_2.data.models.apps.AppsRepositoryComponent;
import com.mobitill.mobitill_2.data.models.apps.AppsRepositoryModule;
import com.mobitill.mobitill_2.data.models.apps.DaggerAppsRepositoryComponent;
import com.mobitill.mobitill_2.data.models.products.DaggerProductsRepositoryComponent;
import com.mobitill.mobitill_2.data.models.products.ProductsRepositoryComponent;
import com.mobitill.mobitill_2.data.models.products.ProductsRepositoryModule;
import com.mobitill.mobitill_2.data.models.reports.DaggerReportsRepositoryComponent;
import com.mobitill.mobitill_2.data.models.reports.ReportsRepositoryComponent;
import com.mobitill.mobitill_2.data.models.reports.ReportsRepositoryModule;
import com.mobitill.mobitill_2.data.models.users.UserModule;
import com.mobitill.mobitill_2.net.ConnectivityReceiver;
import com.mobitill.mobitill_2.net.NetModule;

/**
 * Created by DI on 8/5/2016.
 */
public class MobitillApplication extends Application {

    private BaseComponent mBaseComponent;
    private AppsRepositoryComponent mAppsRepositoryComponent;
    private ReportsRepositoryComponent mReportsRepositoryComponent;
    private ProductsRepositoryComponent mProductsRepositoryComponent;

    private static MobitillApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;

        FontsOverride.setDefaultFont(this, "MONOSPACE", "fonts/Roboto-Condensed.ttf");


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

        mReportsRepositoryComponent = DaggerReportsRepositoryComponent.builder()
                .reportsRepositoryModule(new ReportsRepositoryModule())
                .baseComponent(getBaseComponent())
                .build();

        mProductsRepositoryComponent = DaggerProductsRepositoryComponent.builder()
                .productsRepositoryModule(new ProductsRepositoryModule())
                .baseComponent(getBaseComponent())
                .build();
    }

    public BaseComponent getBaseComponent(){
        return mBaseComponent;
    }

    public AppsRepositoryComponent getAppsRepositoryComponent(){
        return mAppsRepositoryComponent;
    }

    public ReportsRepositoryComponent getReportsRepositoryComponent(){
        return mReportsRepositoryComponent;
    }

    public ProductsRepositoryComponent getProductsRepositoryComponent(){
        return mProductsRepositoryComponent;
    }


    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener){
        ConnectivityReceiver.sConnectivityReceiverListener = listener;
    }

    public static synchronized MobitillApplication getInstance(){
        return mInstance;
    }

}
