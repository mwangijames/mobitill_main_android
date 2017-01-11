package com.mobitill.mobitill_2.data.models.generic.local;

import android.support.annotation.NonNull;

import com.mobitill.mobitill_2.data.models.generic.GenericController;
import com.mobitill.mobitill_2.data.models.generic.GenericDataSource;
import com.mobitill.mobitill_2.data.models.generic.Payload;
import com.mobitill.mobitill_2.data.models.generic.models.LocalGeneric;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by james on 11/1/2016.
 */

public class GenericDataLocalDataSource implements GenericDataSource {

    private static final String TAG = GenericDataLocalDataSource.class.getSimpleName();
    private final GenericController mGenericController;


    @Inject
    public GenericDataLocalDataSource(GenericController genericController){

        mGenericController = genericController;
    }

    @Override
    public void postData(Payload payload, @NonNull LoadDataCallBack callBack) {
            List<LocalGeneric> data = mGenericController.getData(payload.getModel(), payload.getAppid());
            if(data.isEmpty()){
                callBack.onDataNotLoaded();
            } else {
                // create json string same as from server
                try {
                    JSONObject dataObject = new JSONObject();
                    JSONArray jsonArray = new JSONArray();
                    for (int i = 0; i <  data.size(); i++) {
                        jsonArray.put(data.get(i).getData());
                    }
                    dataObject.put("data", jsonArray);
                    String dataString = dataObject.toString();
                    //Log.i(TAG, "postData: " + dataString);
                    callBack.onDataLoaded(dataString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

    }

    @Override
    public void refreshData(Payload payload, @NonNull LoadDataCallBack callBack) {

    }

    @Override
    public void deleteAll() {
        mGenericController.deleteAll();
    }

    @Override
    public void saveItem(LocalGeneric localGeneric) {
        mGenericController.save(localGeneric);
    }
}
