package com.mobitill.mobitill_2.data;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by james on 8/23/2016.
 */
@Module
public class RealmModule {

    @Provides
    @Singleton
    RealmConfiguration provideRealmConfiguration(Context context){
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(context)
                .build();

        return realmConfiguration;
    }


    @Provides
    @Singleton
    Realm provideRealm(RealmConfiguration realmConfiguration){
        Realm.setDefaultConfiguration(realmConfiguration);
        return Realm.getDefaultInstance();
    }

}
