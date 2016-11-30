package com.mobitill.mobitill_2.data.models.generic.local;

import android.support.annotation.NonNull;

import com.mobitill.mobitill_2.data.models.generic.GenericDataSource;
import com.mobitill.mobitill_2.data.models.generic.Payload;

import javax.inject.Inject;

/**
 * Created by james on 11/1/2016.
 */

public class GenericDataLocalDataSource implements GenericDataSource {

    @Inject
    public GenericDataLocalDataSource(){

    }

    @Override
    public void postData(Payload payload, @NonNull LoadDataCallBack callBack) {

    }

    @Override
    public void refreshData(Payload payload, @NonNull LoadDataCallBack callBack) {

    }
}
