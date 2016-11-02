package com.mobitill.mobitill_2.data.models.generic;

import android.support.annotation.NonNull;

/**
 * Created by james on 11/1/2016.
 */

public interface GenericDataSource {
    interface LoadDataCallBack{
        void onDataLoaded(String data);
        void onDataNotLoaded();
    }

    void getData(Payload payload, @NonNull LoadDataCallBack callBack);

}
