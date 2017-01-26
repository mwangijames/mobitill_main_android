package com.mobitill.mobitill_2.reports;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.mobitill.mobitill_2.Constants;
import com.mobitill.mobitill_2.MobitillApplication;
import com.mobitill.mobitill_2.R;
import com.mobitill.mobitill_2.apps.AppsActivity;
import com.mobitill.mobitill_2.components.ShowAllUtils;
import com.mobitill.mobitill_2.components.showall.ShowAllActivity;
import com.mobitill.mobitill_2.menu.MenuAppSettings;
import com.mobitill.mobitill_2.utils.ActivityUtils;
import com.mobitill.mobitill_2.utils.RecyclerClickListener;
import com.mobitill.mobitill_2.utils.RecyclerTouchListener;
import com.mobitill.mobitill_2.utils.SettingsHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReportsActivity extends AppCompatActivity {

    public static final String EXTRA_APP_ID = "APPID";
    private static final String ARGS_APP_ID = "args_app_id";
    public static final String EXTRA_APP_SETTINGS = "extra_app_settings";
    public static final String ARGS_APP_SETTINGS= "args_app_settings";

    @Inject ReportsPresenter mReportsPresenter;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.drawer_layout) DrawerLayout mDrawerLayout;
    //@BindView(R.id.nav_view) NavigationView mNavigationView;
    @BindView(R.id.drawer_recycler_view) RecyclerView mDrawerRecyclerView;
    @BindView(R.id.drawer_button_apps) Button mAppsButton;
    @BindView(R.id.drawer_button_reports) Button mReportsButton;
    @BindView(R.id.drawer_button_inventory) Button mInventoryButton;


    private List<String> mModels;
    private DrawerAdapter mDrawerAdapter;

    SharedPreferences mSharedPreferences;
    Constants mConstants;

    String mAppId;
    private MenuAppSettings mMenuAppSettings;
    private static final String TAG = ReportsActivity.class.getSimpleName();
    private SettingsHelper mSettingsHelper;

    public static Intent newIntent(Context context, String appId, MenuAppSettings menuAppSettings){
        Intent intent = new Intent(context, ReportsActivity.class);
        intent.putExtra(EXTRA_APP_ID, appId);
        intent.putExtra(EXTRA_APP_SETTINGS, menuAppSettings);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        ButterKnife.bind(this);

        mSettingsHelper = new SettingsHelper();



        mConstants = new Constants();
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        if(savedInstanceState == null){
            mAppId = getIntent().getStringExtra(EXTRA_APP_ID);
            if(mAppId == null){
                mAppId = mSharedPreferences.getString(mConstants.APPID, null);
            }
            mMenuAppSettings = (MenuAppSettings) getIntent().getSerializableExtra(EXTRA_APP_SETTINGS);
        } else {
            mAppId = savedInstanceState.getString(ARGS_APP_ID);
            mMenuAppSettings = (MenuAppSettings) savedInstanceState.getSerializable(ARGS_APP_SETTINGS);
        }

        hideInventory();

        // Set up the toolbar.
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        mConstants = new Constants();
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        if(mSharedPreferences != null){
            String title = mSharedPreferences.getString(mConstants.APPNAME, null);
            ab.setSubtitle(title);
            ab.setTitle("Reports");
        } else {
            ab.setTitle("Reports");
        }

        mDrawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
//        if(mNavigationView != null){
//            //setupDrawerContent();
//        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mDrawerRecyclerView.setLayoutManager(linearLayoutManager);

        showMenuItems();
        implementRecyclerViewClickListeners();

        ReportsFragment reportsFragment =
                (ReportsFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (reportsFragment == null){
            reportsFragment = ReportsFragment.newInstance(mAppId);
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), reportsFragment, R.id.contentFrame);
        }

        // create the presenter
        DaggerReportsComponent.builder()
                .reportsPresenterModule(new ReportsPresenterModule(reportsFragment, mAppId, mMenuAppSettings))
                .baseComponent(((MobitillApplication) getApplication()).getBaseComponent())
                .build()
                .inject(this);
    }

    private void hideInventory() {

        mInventoryButton.setVisibility(
                mSettingsHelper.isInventory(
                        mMenuAppSettings.getSettings()) ? View.VISIBLE : View.GONE);

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(ARGS_APP_ID, mAppId);
        outState.putSerializable(ARGS_APP_SETTINGS, mMenuAppSettings);
        super.onSaveInstanceState(outState);
    }

    @OnClick(R.id.drawer_button_apps)
    public void showApps(Button button){
        startActivity(AppsActivity.newIntent(this));
    }

    @OnClick(R.id.drawer_button_reports)
    public void showReports(Button button){
        startActivity(ReportsActivity.newIntent(this, mAppId, mMenuAppSettings));
    }

    @OnClick(R.id.drawer_button_inventory)
    public void showInventory(Button button){
        ShowAllUtils showAllUtils = new ShowAllUtils();
        showAllUtils.setModel("inventory");
        showAllUtils.setAppId(mAppId);
        showAllUtils.setSettings(mMenuAppSettings.getSettings());
        startActivity(ShowAllActivity.newIntent(this, showAllUtils, mMenuAppSettings));
    }

    public void showMenuItems() {
            if(mDrawerAdapter == null){
                mDrawerAdapter = new DrawerAdapter(getMenuList());
                mModels = getMenuList();
                mDrawerRecyclerView.setAdapter(mDrawerAdapter);
            } else {
                mDrawerAdapter.setModels(getMenuList());
                mModels = getMenuList();
                mDrawerAdapter.notifyDataSetChanged();
            }
    }

    private void implementRecyclerViewClickListeners(){
        mDrawerRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, mDrawerRecyclerView, new RecyclerClickListener() {
            @Override
            public void onClick(View view, int position) {
                if(mModels != null && !mModels.isEmpty()){
                    openShowAll(mModels.get(position));
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    public void openShowAll(String model) {
        if(mAppId != null){
            if(mMenuAppSettings!=null && mMenuAppSettings.getSettings() != null){
                ShowAllUtils showAllUtils = new ShowAllUtils();
                showAllUtils.setAppId(mAppId);
                showAllUtils.setSettings(mMenuAppSettings.getSettings());
                showAllUtils.setModel(model);
                if(showAllUtils.isEmpty()){
//                    Log.i(TAG, "openShowAll: show showAllUtils fields are null or empty");
//                    Log.i(TAG, "openShowAll: appId: " + mAppId);
//                    Log.i(TAG, "openShowAll: menuAppSettings: " + mMenuAppSettings.getSettings());
//                    Log.i(TAG, "openShowAll: model: " + model);
                } else {
                    //mView.showAllActivity(showAllUtils);
                    startActivity(ShowAllActivity.newIntent(this, showAllUtils, mMenuAppSettings));
                    //menuItem.setChecked(true);
                    mDrawerLayout.closeDrawers();
                }
            } else {
                Log.i(TAG, "openShowAll: " + "mMenuAppSettings has issues");
            }
        } else {
            Log.i(TAG, "openShowAll: " + "appId is null");
        }
    }

    public List<String> getMenuList() {
        List<String> models = new ArrayList<>();
        SettingsHelper settingsHelper = new SettingsHelper();
        if(mMenuAppSettings != null){

            if(mMenuAppSettings.getSettings() != null){
               // Log.i(TAG, "start: " + mMenuAppSettings.getSettings());
                 models = settingsHelper.getModels(mMenuAppSettings.getSettings());
                for (String model: models) {
                    //Log.i(TAG, "getMenuList: " + model);
                }
                //mView.showMenuItems(models);
            } else {
                settingsHelper.getModels("{}");
            }

        } else {
            Log.i(TAG, "start: mMenuAppSettings is null");
        }

        return models;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Open the navigation drawer when the home icon is selected from the toolbar.
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    private void setupDrawerContent(){
//        SetUpDrawerContent setUpDrawerContent = new SetUpDrawerContent(this);
//        setUpDrawerContent.setupDrawerContent(mNavigationView, ReportsActivity.this, mDrawerLayout);
//    }

}
