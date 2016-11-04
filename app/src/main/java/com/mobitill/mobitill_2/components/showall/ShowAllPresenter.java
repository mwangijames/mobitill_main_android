package com.mobitill.mobitill_2.components.showall;

import android.util.Log;

import com.mobitill.mobitill_2.components.ShowAllUtils;
import com.mobitill.mobitill_2.data.models.generic.Actions;
import com.mobitill.mobitill_2.data.models.generic.GenericDataSource;
import com.mobitill.mobitill_2.data.models.generic.GenericRepository;
import com.mobitill.mobitill_2.data.models.generic.Payload;
import com.mobitill.mobitill_2.utils.SettingsHelper;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by james on 11/1/2016.
 */

public class ShowAllPresenter implements ShowAllContract.Presenter {
    
    private static final String TAG = ShowAllPresenter.class.getSimpleName();

    private final ShowAllContract.View mView;
    private final ShowAllUtils mShowAllUtils;
    private final GenericRepository mGenericRepository;
    private final Payload mPayload;
    private final SettingsHelper mSettingsHelper;
    private final Actions mActions;

    @Inject
    ShowAllPresenter(ShowAllContract.View view,
                     ShowAllUtils showAllUtils,
                     GenericRepository genericRepository,
                     Payload payload,
                     SettingsHelper settingsHelper,
                     Actions actions){
        mView = view;
        mShowAllUtils = showAllUtils;
        mGenericRepository = genericRepository;
        mPayload = payload;
        mSettingsHelper = settingsHelper;
        mActions = actions;
    }

    /**
     * Method injection is used here to safely reference {@code this} after the object is created.
     * For more information, see Java Concurrency in Practice.
     */
    @Inject
    void setupListeners() {
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        fetch();
    }


    @Override
    public void fetch() {
        if(mShowAllUtils != null) {
            if(mShowAllUtils.isEmpty()){
                Log.i(TAG, "fetch: " + "Some utils are not available");
            } else {
                if(mPayload != null){
                    mPayload.setModel(mShowAllUtils.getModel());
                    mPayload.setAction(mActions.FETCH);
                    mPayload.setPayload(mSettingsHelper.getPayload(mActions.FETCH, mShowAllUtils.getAppId()));
                    if(mPayload.isEmpty()){
                        Log.i(TAG, "fetch: " + "Payload has some issues");
                    } else {
                        mGenericRepository.getData(mPayload, new GenericDataSource.LoadDataCallBack() {
                            @Override
                            public void onDataLoaded(String data) {
                                List<HashMap<String, String>> items = mSettingsHelper.getList(data);
                                mView.show(items);
                            }

                            @Override
                            public void onDataNotLoaded() {
                                Log.i(TAG, "onDataNotLoaded: No data");
                            }
                        });
                    }
                }
            }
        }
    }
}