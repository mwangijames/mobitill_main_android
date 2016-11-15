package com.mobitill.mobitill_2.components.showall;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mobitill.mobitill_2.Constants;
import com.mobitill.mobitill_2.MobitillApplication;
import com.mobitill.mobitill_2.R;
import com.mobitill.mobitill_2.apps.AppsActivity;
import com.mobitill.mobitill_2.clientsdetail.ClientsDetailActivity;
import com.mobitill.mobitill_2.components.ShowAllUtils;
import com.mobitill.mobitill_2.menu.MenuActivity;
import com.mobitill.mobitill_2.menu.MenuAppSettings;
import com.mobitill.mobitill_2.reports.DrawerAdapter;
import com.mobitill.mobitill_2.reports.ReportsActivity;
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

public class ShowAllActivity extends AppCompatActivity {

    @Inject ShowAllPresenter mShowAllPresenter;

    private static final String TAG = ShowAllActivity.class.getSimpleName();
    private static final String EXTRA_SHOW_ALL_UTILS = "extra_show_all_utils";
    private static final String ARGS_SHOW_ALL_UTILS = "args_show_all_utils";
    public static final String EXTRA_APP_SETTINGS = "extra_app_settings";
    public static final String ARGS_APP_SETTINGS= "args_app_settings";




    private SharedPreferences mSharedPreferences;
    private Constants mConstants;
    private ShowAllUtils mShowAllUtils;

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    //@BindView(R.id.nav_view) NavigationView mNavigationView;
    @BindView(R.id.drawer_recycler_view)
    RecyclerView mDrawerRecyclerView;
    @BindView(R.id.drawer_button_apps)
    Button mAppsButton;
    @BindView(R.id.drawer_button_reports) Button mReportsButton;
    @BindView(R.id.drawer_button_inventory) Button mInventoryButton;

    private List<String> mModels;
    private DrawerAdapter mDrawerAdapter;
    private MenuAppSettings mMenuAppSettings;

    public static Intent newIntent(Context context, ShowAllUtils showAllUtils, MenuAppSettings menuAppSettings){
        Intent intent = new Intent(context, ShowAllActivity.class);
        intent.putExtra(EXTRA_SHOW_ALL_UTILS, showAllUtils);
        intent.putExtra(EXTRA_APP_SETTINGS, menuAppSettings);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all);

        ButterKnife.bind(this);

        mConstants = new Constants();
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        if(savedInstanceState == null){
            mShowAllUtils = (ShowAllUtils) getIntent().getSerializableExtra(EXTRA_SHOW_ALL_UTILS);
            mMenuAppSettings = (MenuAppSettings) getIntent().getSerializableExtra(EXTRA_APP_SETTINGS);
        } else {
            mShowAllUtils = (ShowAllUtils) savedInstanceState.getSerializable(ARGS_SHOW_ALL_UTILS);
            mMenuAppSettings = (MenuAppSettings) savedInstanceState.getSerializable(ARGS_APP_SETTINGS);
        }

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);

        mDrawerRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        showMenuItems();
        implementRecyclerViewClickListeners();

        if(mShowAllUtils != null){
            if(mSharedPreferences != null ){
                String title = mSharedPreferences.getString(mConstants.APPNAME, null);
                actionBar.setTitle(title + ": " + mShowAllUtils.getModel().toUpperCase());
            }
        } else {
            if(mSharedPreferences != null){
                String title = mSharedPreferences.getString(mConstants.APPNAME, null);
                actionBar.setTitle(title);
            }
        }

        ShowAllFragment showAllFragment =
                (ShowAllFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if(showAllFragment == null){
            showAllFragment = ShowAllFragment.newInstance(mShowAllUtils);
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    showAllFragment,
                    R.id.contentFrame
            );
        }

        DaggerShowAllComponent.builder()
                .showAllPresenterModule(new ShowAllPresenterModule(showAllFragment, mShowAllUtils))
                .baseComponent(((MobitillApplication) getApplication()).getBaseComponent())
                .build()
                .inject(this);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(ARGS_SHOW_ALL_UTILS, mShowAllUtils);
        outState.putSerializable(ARGS_APP_SETTINGS, mMenuAppSettings);
        super.onSaveInstanceState(outState);
    }

    @OnClick(R.id.drawer_button_apps)
    public void showApps(Button button){
        startActivity(AppsActivity.newIntent(this));
    }

    @OnClick(R.id.drawer_button_reports)
    public void showReports(Button button){
        startActivity(ReportsActivity.newIntent(this, mShowAllUtils.getAppId(), mMenuAppSettings));
    }

    @OnClick(R.id.drawer_button_inventory)
    public void showInventory(Button button){
        ShowAllUtils showAllUtils = new ShowAllUtils();
        showAllUtils.setModel("inventory");
        showAllUtils.setAppId(mShowAllUtils.getAppId());
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
        if(mShowAllUtils.getAppId() != null){
            if(mMenuAppSettings!=null && mMenuAppSettings.getSettings() != null){
                ShowAllUtils showAllUtils = new ShowAllUtils();
                showAllUtils.setAppId(mShowAllUtils.getAppId());
                showAllUtils.setSettings(mMenuAppSettings.getSettings());
                showAllUtils.setModel(model);
                if(showAllUtils.isEmpty()){
                    Log.i(TAG, "openShowAll: show showAllUtils fields are null or empty");
                    Log.i(TAG, "openShowAll: appId: " + mShowAllUtils.getAppId());
                    Log.i(TAG, "openShowAll: menuAppSettings: " + mMenuAppSettings.getSettings());
                    Log.i(TAG, "openShowAll: model: " + model);
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
                Log.i(TAG, "start: " + mMenuAppSettings.getSettings());
                models = settingsHelper.getModels(mMenuAppSettings.getSettings());
                for (String model: models) {
                    Log.i(TAG, "getMenuList: " + model);
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
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void goBack(){
        if(mShowAllUtils!=null){
            MenuAppSettings menuAppSettings = new MenuAppSettings();
            menuAppSettings.setSettings(mShowAllUtils.getSettings());
            startActivity(ReportsActivity.newIntent(this, mShowAllUtils.getAppId(), menuAppSettings));
        } else {
            finish();
        }
    }
}
