package com.mobitill.mobitill_2.components.showall;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.mobitill.mobitill_2.components.ShowAllUtils;
import com.mobitill.mobitill_2.data.models.generic.Actions;
import com.mobitill.mobitill_2.data.models.generic.GenericDataSource;
import com.mobitill.mobitill_2.data.models.generic.GenericRepository;
import com.mobitill.mobitill_2.data.models.generic.Payload;
import com.mobitill.mobitill_2.sync.MobitillSyncAdapter;
import com.mobitill.mobitill_2.utils.SettingsHelper;

import java.util.ArrayList;
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
    private final Context mContext;

    @Inject
    ShowAllPresenter(ShowAllContract.View view,
                     ShowAllUtils showAllUtils,
                     GenericRepository genericRepository,
                     Payload payload,
                     SettingsHelper settingsHelper,
                     Actions actions,
                     Context context){
        mView = view;
        mShowAllUtils = showAllUtils;
        mGenericRepository = genericRepository;
        mPayload = payload;
        mSettingsHelper = settingsHelper;
        mActions = actions;
        mContext = context;
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
        mView.showLoading(true);
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
                    mPayload.setPayload(mSettingsHelper.getFetchPayload(mActions.FETCH, mShowAllUtils.getAppId()));
                    mPayload.setAppid(mShowAllUtils.getAppId());
                    if(mShowAllUtils.getModel().equalsIgnoreCase("inventory")){
                        mPayload.setAction(mActions.LIST);
                        mPayload.setPayload(mSettingsHelper.getInventoryPayload(mShowAllUtils.getAppId()));
                        Log.i(TAG, "fetch: " + mSettingsHelper.getInventoryPayload(mShowAllUtils.getAppId()));
                    }
                    if(mSettingsHelper.isDemo(mShowAllUtils.getSettings()) && mShowAllUtils.getModel().equalsIgnoreCase("inventory")){
                        mPayload.setDemo(true);
                    } else {
                        mPayload.setDemo(false);
                    }

                    if(mPayload.isEmpty()){
                        Log.i(TAG, "fetch: " + "Payload has some issues");
                    } else {
                        mView.showLoading(true);
                        mGenericRepository.postData(mPayload, new GenericDataSource.LoadDataCallBack() {
                            @Override
                            public void onDataLoaded(String data) {
                                List<HashMap<String, String>> items = mSettingsHelper.getList(data);
                                mView.showLoading(false);

                                if(items.isEmpty()){
                                   mView.showEmpty(true);
                                   List<HashMap<String, String>> emptyItems = new ArrayList<HashMap<String, String>>();
                                   mView.show(emptyItems);
                                } else {
                                    mView.showHeader(items.get(0));
                                    mView.show(items);
                                    mView.showEmpty(false);
                                }
                                mView.showDataError(false);
                                //mView.showNetworkError(isNetworkAvailable());
                                mView.showNetworkAvailable(isNetworkAvailable());
                            }

                            @Override
                            public void onDataNotLoaded() {
                                mView.showLoading(false);
                                mView.showDataError(true);
                               // mView.showNetworkError(isNetworkAvailable());
                                mView.showNetworkAvailable(isNetworkAvailable());
                                Log.i(TAG, "onDataNotLoaded: No data");
                            }
                        });
                    }
                }
            }
        }
    }

    @Override
    public void fetch(String action) {
        if(mShowAllUtils != null) {
            if(mShowAllUtils.isEmpty()){
                Log.i(TAG, "fetch: " + "Some utils are not available");
            } else {
                if(mPayload != null){
                    mPayload.setModel(mShowAllUtils.getModel());
                    mPayload.setAction(mActions.FETCH);
                    mPayload.setPayload(mSettingsHelper.getFetchPayload(mActions.FETCH, mShowAllUtils.getAppId()));
                    mPayload.setAppid(mShowAllUtils.getAppId());
                    if(mShowAllUtils.getModel().equalsIgnoreCase("inventory")){
                        mPayload.setAction(action);
                        mPayload.setPayload(mSettingsHelper.getInventoryPayload(mShowAllUtils.getAppId()));
                        Log.i(TAG, "fetch: " + mSettingsHelper.getInventoryPayload(mShowAllUtils.getAppId()));
                    }
                    if(mSettingsHelper.isDemo(mShowAllUtils.getSettings()) && mShowAllUtils.getModel().equalsIgnoreCase("inventory")){
                        mPayload.setDemo(true);
                    } else {
                        mPayload.setDemo(false);
                    }

                    if(mPayload.isEmpty()){
                        Log.i(TAG, "fetch: " + "Payload has some issues");
                    } else {
                        mView.showLoading(true);
                        mGenericRepository.postData(mPayload, new GenericDataSource.LoadDataCallBack() {
                            @Override
                            public void onDataLoaded(String data) {
                                List<HashMap<String, String>> items = mSettingsHelper.getList(data);
                                mView.showLoading(false);

                                if(items.isEmpty()){
                                    mView.showEmpty(true);
                                    List<HashMap<String, String>> emptyItems = new ArrayList<HashMap<String, String>>();
                                    mView.show(emptyItems);
                                } else {
                                    mView.showHeader(items.get(0));
                                    mView.show(items);
                                    mView.showEmpty(false);

                                }
                                mView.showDataError(false);
                                mView.showNetworkError(isNetworkAvailable());
                            }

                            @Override
                            public void onDataNotLoaded() {
                                mView.showLoading(false);
                                mView.showDataError(true);
                                mView.showNetworkError(isNetworkAvailable());
                                Log.i(TAG, "onDataNotLoaded: No data");
                            }
                        });
                    }
                }
            }
        }
    }

    @Override
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    @Override
    public void delete(final HashMap<String, String> item) {
        if(mShowAllUtils != null){
            if(mShowAllUtils.isEmpty()){
                Log.i(TAG, "delete: show ShowAllUtils fields are empty or null");
            } else {
                if(mSettingsHelper != null){
                   String payloadString = mSettingsHelper.getDeletePayload(item, mShowAllUtils.getAppId());

                    if(payloadString != null){
                        if(mPayload != null){
                            mPayload.setModel(mShowAllUtils.getModel());
                            mPayload.setPayload(payloadString);
                            mPayload.setAction(mActions.DELETE);
                            mPayload.setDemo(false);
                            mPayload.setAppid(mShowAllUtils.getAppId());
                            if(mPayload.isEmpty()){
                                Log.i(TAG, "delete: some Payload fields are Empty or Null");
                            } else {
                                mGenericRepository.postData(mPayload, new GenericDataSource.LoadDataCallBack() {
                                    @Override
                                    public void onDataLoaded(String data) {
                                        mView.showItemDeleted(item);
                                        MobitillSyncAdapter.syncImmediately(mContext);
                                    }

                                    @Override
                                    public void onDataNotLoaded() {
                                        mView.showItemDeleteFailed();
                                    }
                                });
                            }
                        }
                    }

                }
            }
        }
    }

    @Override
    public void openEdit(HashMap<String ,String> item) {
        if(mShowAllUtils != null && !mShowAllUtils.isEmpty()){
            mView.showEdit(mShowAllUtils, item);
        }
    }


}
