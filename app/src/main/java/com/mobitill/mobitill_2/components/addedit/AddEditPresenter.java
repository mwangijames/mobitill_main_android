package com.mobitill.mobitill_2.components.addedit;

import android.support.annotation.Nullable;
import android.util.Log;

import com.mobitill.mobitill_2.components.ShowAllUtils;
import com.mobitill.mobitill_2.data.models.generic.Actions;
import com.mobitill.mobitill_2.data.models.generic.GenericDataSource;
import com.mobitill.mobitill_2.data.models.generic.GenericRepository;
import com.mobitill.mobitill_2.data.models.generic.Payload;
import com.mobitill.mobitill_2.utils.SettingsHelper;

import org.json.JSONException;

import java.util.Arrays;
import java.util.HashMap;

import javax.inject.Inject;

/**
 * Created by james on 11/4/2016.
 */

public class AddEditPresenter implements AddEditContract.Presenter {

    private static final String TAG = AddEditPresenter.class.getSimpleName();

    private final AddEditContract.View mView;
    private final ShowAllUtils mShowAllUtils;
    private final GenericRepository mGenericRepository;
    private final Payload mPayload;
    private final SettingsHelper mSettingsHelper;
    private final Actions mActions;
    @Nullable private final HashMap<String, String> mItem;

    @Inject
    public AddEditPresenter(
            AddEditContract.View view,
            ShowAllUtils showAllUtils,
            GenericRepository genericRepository,
            Payload payload,
            SettingsHelper settingsHelper,
            Actions actions,
            @Nullable HashMap<String, String> item
    ){
        mView = view;
        mShowAllUtils = showAllUtils;
        mGenericRepository = genericRepository;
        mPayload = payload;
        mSettingsHelper = settingsHelper;
        mActions = actions;
        mItem = item;
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
        if(mItem == null){
            generateUI();
        } else {
            generateAndPopulateUI(mItem);
            Log.i(TAG, "start: " + mItem);
        }
    }

    @Override
    public void add(HashMap<String, String> data) {
        if(mSettingsHelper != null){
            if(mShowAllUtils != null && !mShowAllUtils.isEmpty()){
                try {
                   String payload = mSettingsHelper.getInsertPayLoad(data, mShowAllUtils.getAppId());
                    if(payload != null && mPayload != null){
                        mPayload.setPayload(payload);
                        mPayload.setModel(mShowAllUtils.getModel());
                        mPayload.setAction(mActions.INSERT);
                        mPayload.setDemo(false);
                        if(mPayload.isEmpty()){
                            Log.i(TAG, "add: " + "Some Payload fields are empty or null");
                        } else {
                            if(mGenericRepository != null){
                                mView.showLoading(true);
                                mGenericRepository.postData(mPayload, new GenericDataSource.LoadDataCallBack() {
                                    @Override
                                    public void onDataLoaded(String data) {
                                        mView.showSuccess(true);
                                        mView.showLoading(false);
                                        openShowAll();
                                    }

                                    @Override
                                    public void onDataNotLoaded() {
                                        mView.showFail(true);
                                        mView.showLoading(false);
                                    }
                                });
                            }
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    @Override
    public void addStock(HashMap<String, String> data) {
        // TODO: 11/16/2016 start from here to add stock 
    }

    @Override
    public void edit(HashMap<String, String> data) {
        if(mSettingsHelper != null){
            if(mShowAllUtils != null && !mShowAllUtils.isEmpty()){
                try {
                    String payload = mSettingsHelper.getUpdatePayLoad(data, mShowAllUtils.getAppId(), mItem.get("id"));
                    Log.i(TAG, "edit: " + payload);
                    if(payload != null && mPayload != null){
                        mPayload.setPayload(payload);
                        mPayload.setModel(mShowAllUtils.getModel());
                        mPayload.setAction(mActions.UPDATE);
                        mPayload.setDemo(false);
                        if(mPayload.isEmpty()){
                            Log.i(TAG, "edit: " + "Some Payload fields are empty or null");
                        } else {
                            if(mGenericRepository != null){
                                mView.showLoading(true);
                                mGenericRepository.postData(mPayload, new GenericDataSource.LoadDataCallBack() {
                                    @Override
                                    public void onDataLoaded(String data) {
                                        mView.showSuccess(true);
                                        mView.showLoading(false);
                                        openShowAll();
                                    }

                                    @Override
                                    public void onDataNotLoaded() {
                                        mView.showFail(true);
                                        mView.showLoading(false);
                                    }
                                });
                                Log.i(TAG, "edit: " + payload);
                            }
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void generateUI() {
        if(mShowAllUtils!=null){
            if(mShowAllUtils.isEmpty()){
                Log.i(TAG, "generateUI: mShowAllUtils missing some fields");
            } else {
                HashMap<String, String[]> schema = new HashMap<>();
                if(!mShowAllUtils.getModel().equalsIgnoreCase("inventory")){
                    schema = mSettingsHelper.getSchema(mShowAllUtils.getSettings(), mShowAllUtils.getModel());
                } else {
                    schema = mSettingsHelper.getInventorySchema(mShowAllUtils.getSettings(), mShowAllUtils.getModel());
                }
                
                mView.showUI(schema);
            }
        } else {
            Log.i(TAG, "generateUI: mShowAllUtils is null");
        }
    }

    @Override
    public void generateAndPopulateUI(HashMap<String, String> item) {
        if(mShowAllUtils!=null){
            if(mShowAllUtils.isEmpty()){
                Log.i(TAG, "generateUI: mShowAllUtils missing some fields");
            } else {
                HashMap<String, String[]> schema =
                        mSettingsHelper.getSchema(mShowAllUtils.getSettings(), mShowAllUtils.getModel());
                mView.showAndPopulateUI(schema, item);

            }
        } else {
            Log.i(TAG, "generateUI: mShowAllUtils is null");
        }
    }

    @Override
    public void openShowAll() {
        if(mShowAllUtils != null && !mShowAllUtils.isEmpty()){
            mView.showAll(mShowAllUtils);
        }
    }

}
