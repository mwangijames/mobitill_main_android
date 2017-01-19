package com.mobitill.mobitill_2.data.models.generic;

import android.support.annotation.NonNull;

import com.mobitill.mobitill_2.data.Local;
import com.mobitill.mobitill_2.data.Remote;
import com.mobitill.mobitill_2.data.models.generic.models.LocalGeneric;
import com.mobitill.mobitill_2.utils.SettingsHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

/**
 * Created by james on 11/1/2016.
 */

public class GenericRepository implements GenericDataSource {

    private final GenericDataSource mGenericDataRemoteDataSource;
    private final GenericDataSource mGenericLocalDataSource;
    private final SettingsHelper mSettingsHelper;

    @Inject
    GenericRepository(@Remote GenericDataSource genericDataRemoteDataSource,
                      @Local GenericDataSource genericLocalDataSource, SettingsHelper settingsHelper){
        mGenericDataRemoteDataSource = genericDataRemoteDataSource;
        mGenericLocalDataSource = genericLocalDataSource;
        mSettingsHelper = settingsHelper;
    }

    @Override
    public void postData(final Payload payload, @NonNull final LoadDataCallBack callBack) {

        if(payload.getModel().equalsIgnoreCase("transactions")){
            getDataFromRemoteDataSource(payload, callBack);
        } else {
            Actions actions = new Actions();
            if(payload.getAction().equalsIgnoreCase(actions.FETCH)){
                // query the local data source if available, if not query the remote data source
                mGenericLocalDataSource.postData(payload, new LoadDataCallBack() {
                    @Override
                    public void onDataLoaded(String data) {
                        callBack.onDataLoaded(data);
                    }

                    @Override
                    public void onDataNotLoaded() {
                        getDataFromRemoteDataSource(payload, callBack);
                    }
                });
            } else {
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

    }

    private void getDataFromRemoteDataSource(final Payload payload, final LoadDataCallBack callBack) {
        mGenericDataRemoteDataSource.postData(payload, new LoadDataCallBack() {
            @Override
            public void onDataLoaded(String data) {
                refreshLocalDataSource(payload, data);
                callBack.onDataLoaded(data);
            }

            @Override
            public void onDataNotLoaded() {
                callBack.onDataNotLoaded();
            }
        });
    }

    @Override
    public void refreshData(final Payload payload, @NonNull final LoadDataCallBack callBack) {
        mGenericDataRemoteDataSource.postData(payload, new LoadDataCallBack() {
            @Override
            public void onDataLoaded(String data) {
                refreshLocalDataSource(payload, data);
                callBack.onDataLoaded(data);
            }

            @Override
            public void onDataNotLoaded() {
                callBack.onDataNotLoaded();
            }
        });
    }

    @Override
    public void deleteAll() {
        mGenericLocalDataSource.deleteAll();
    }

    @Override
    public void saveItem(LocalGeneric localGeneric) {

    }

    private void refreshLocalDataSource(Payload payload, String data) {
            if(!payload.getModel().equalsIgnoreCase("transactions")){
                try {
                    JSONObject dataObject = new JSONObject(data);
                    JSONArray jsonArray = dataObject.getJSONArray("data");
                    // loop through saving each item to db
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject item = jsonArray.getJSONObject(i);
                        LocalGeneric localGeneric = new LocalGeneric();
                        localGeneric.setAppid(payload.getAppid());
                        localGeneric.setData(item.toString());
                        localGeneric.setModelName(payload.getModel());
                        mGenericLocalDataSource.saveItem(localGeneric);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
    }

}
