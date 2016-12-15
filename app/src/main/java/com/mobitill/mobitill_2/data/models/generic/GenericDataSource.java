package com.mobitill.mobitill_2.data.models.generic;

import android.support.annotation.NonNull;

import com.mobitill.mobitill_2.data.models.generic.models.LocalGeneric;

/**
 * Created by james on 11/1/2016.
 */

public interface GenericDataSource {
    interface LoadDataCallBack{
        void onDataLoaded(String data);
        void onDataNotLoaded();
    }



    void postData(Payload payload, @NonNull LoadDataCallBack callBack);
    void refreshData(Payload payload, @NonNull LoadDataCallBack callBack);
    void deleteAll();
    void saveItem(LocalGeneric localGeneric);
}
