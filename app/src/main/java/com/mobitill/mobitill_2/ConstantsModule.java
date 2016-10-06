package com.mobitill.mobitill_2;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by DI on 8/10/2016.
 */
@Module
public class ConstantsModule {
    @Singleton
    @Provides
    Constants provideConstants(){
        return new Constants();
    }

}
