package com.mobitill.mobitill_2;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by DI on 8/5/2016.
 */
@Module
public final class ApplicationModule {
    private final Context mContext;

    ApplicationModule(Context context){
        mContext = context;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return mContext;
    }

}
