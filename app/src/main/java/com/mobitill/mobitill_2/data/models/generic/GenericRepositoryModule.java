package com.mobitill.mobitill_2.data.models.generic;

import com.mobitill.mobitill_2.data.Remote;
import com.mobitill.mobitill_2.data.models.generic.remote.GenericDataRemoteDataSource;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by james on 11/1/2016.
 */
@Module
public class GenericRepositoryModule {
    @Provides
    @Remote
    GenericDataSource provideGenericDataRemoteDataSource(Retrofit retrofit){
        return new GenericDataRemoteDataSource(retrofit);
    }
}
