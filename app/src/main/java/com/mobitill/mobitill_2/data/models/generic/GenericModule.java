package com.mobitill.mobitill_2.data.models.generic;

import dagger.Module;
import dagger.Provides;

/**
 * Created by james on 11/2/2016.
 */
@Module
public class GenericModule {
    @Provides
    Payload providePayload(){
        return new Payload();
    }

    @Provides
    Actions provideActions(){
        return new Actions();
    }
}
