package com.mobitill.mobitill_2.data.models.generic;

import android.support.annotation.NonNull;

import com.mobitill.mobitill_2.data.Remote;

import javax.inject.Inject;

/**
 * Created by james on 11/1/2016.
 */

public class GenericRepository implements GenericDataSource {

    private final GenericDataSource mGenericDataRemoteDataSource;

    @Inject
    GenericRepository(@Remote GenericDataSource genericDataRemoteDataSource){
        mGenericDataRemoteDataSource = genericDataRemoteDataSource;
    }

    @Override
    public void postData(Payload payload, @NonNull final LoadDataCallBack callBack) {
        mGenericDataRemoteDataSource.postData(payload, new LoadDataCallBack() {
            @Override
            public void onDataLoaded(String data) {
                callBack.onDataLoaded(data);
            }

            @Override
            public void onDataNotLoaded() {
                callBack.onDataNotLoaded();
            }
        });
    }
}
