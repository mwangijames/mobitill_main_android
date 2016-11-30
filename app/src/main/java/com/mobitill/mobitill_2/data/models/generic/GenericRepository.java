package com.mobitill.mobitill_2.data.models.generic;

import android.support.annotation.NonNull;

import com.mobitill.mobitill_2.data.Local;
import com.mobitill.mobitill_2.data.Remote;

import javax.inject.Inject;

/**
 * Created by james on 11/1/2016.
 */

public class GenericRepository implements GenericDataSource {

    private final GenericDataSource mGenericDataRemoteDataSource;
    private final GenericDataSource mGenericLocalDataSource;

    @Inject
    GenericRepository(@Remote GenericDataSource genericDataRemoteDataSource,
                      @Local GenericDataSource genericLocalDataSource){
        mGenericDataRemoteDataSource = genericDataRemoteDataSource;
        mGenericLocalDataSource = genericLocalDataSource;
    }

    @Override
    public void postData(Payload payload, @NonNull final LoadDataCallBack callBack) {

        // query the local data source if available, if not query the remove data source

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

    @Override
    public void refreshData(Payload payload, @NonNull LoadDataCallBack callBack) {

    }
}
