package com.mobitill.mobitill_2.menu;

import android.util.Log;
import android.view.Menu;

import com.mobitill.mobitill_2.components.ShowAllUtils;
import com.mobitill.mobitill_2.utils.SettingsHelper;

import java.util.ArrayList;

import javax.inject.Inject;


/**
 * Created by james on 10/31/2016.
 */

public class MenuPresenter implements MenuContract.Presenter {

    private static final String TAG = MenuPresenter.class.getSimpleName();

    private final MenuContract.View mView;
    private final String mAppId;
    private final MenuAppSettings mMenuAppSettings;
    private final SettingsHelper mSettingHelper;

    @Inject
    MenuPresenter(
            MenuContract.View view,
            String appId,
            MenuAppSettings menuAppSettings,
            SettingsHelper settingsHelper

    ){
        mView = view;
        mAppId = appId;
        mMenuAppSettings = menuAppSettings;
        mSettingHelper = settingsHelper;
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
        getMenuList();
    }

    @Override
    public void getMenuList() {
        if(mMenuAppSettings != null){
            if(mMenuAppSettings.getSettings() != null){
                Log.i(TAG, "start: " + mMenuAppSettings.getSettings());
                ArrayList<String> models = mSettingHelper.getModels(mMenuAppSettings.getSettings());
                for (String model: models) {
                    Log.i(TAG, "getMenuList: " + model);
                }
                mView.showMenuItems(models);
            } else {
                mSettingHelper.getModels("{}");
            }

        } else {
            Log.i(TAG, "start: mMenuAppSettings is null");
        }
    }

    @Override
    public void openShowAll(String model) {
        if(mAppId != null){
            if(mMenuAppSettings!=null && mMenuAppSettings.getSettings() != null){
                ShowAllUtils showAllUtils = new ShowAllUtils();
                showAllUtils.setAppId(mAppId);
                showAllUtils.setSettings(mMenuAppSettings.getSettings());
                showAllUtils.setModel(model);
                if(showAllUtils.isEmpty()){
                    Log.i(TAG, "openShowAll: show showAllUtils fields are null or empty");
                    Log.i(TAG, "openShowAll: appId: " + mAppId);
                    Log.i(TAG, "openShowAll: menuAppSettings: " + mMenuAppSettings.getSettings());
                    Log.i(TAG, "openShowAll: model: " + model);
                } else {
                    mView.showAllActivity(showAllUtils);
                }
            } else {
                Log.i(TAG, "openShowAll: " + "mMenuAppSettings has issues");
            }
        } else {
            Log.i(TAG, "openShowAll: " + "appId is null");
        }
    }

}
