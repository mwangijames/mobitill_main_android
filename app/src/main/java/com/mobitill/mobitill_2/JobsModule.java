package com.mobitill.mobitill_2;

import android.content.Context;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by james on 8/29/2016.
 */
@Module
@Singleton
public class JobsModule {
    @Provides
    @Singleton
    FirebaseJobDispatcher provideFirebaseJobDispatcher(Context context){
        return new FirebaseJobDispatcher(new GooglePlayDriver(context));
    }
}
