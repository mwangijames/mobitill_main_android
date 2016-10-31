package com.mobitill.mobitill_2.menu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.mobitill.mobitill_2.Constants;
import com.mobitill.mobitill_2.MobitillApplication;
import com.mobitill.mobitill_2.R;
import com.mobitill.mobitill_2.fleetdetail.FleetDetailActivity;
import com.mobitill.mobitill_2.utils.ActivityUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuActivity extends AppCompatActivity {

    public static final String TAG = MenuActivity.class.getSimpleName();
    public static final String EXTRA_APP_ID = "extra_app_id";
    public static final String ARGS_APP_ID = "args_app_id";
    public static final String EXTRA_APP_SETTINGS = "extra_app_settings";
    public static final String ARGS_APP_SETTINGS= "args_app_settings";

    @Inject MenuPresenter mMenuPresenter;
    @BindView(R.id.toolbar) Toolbar mToolbar;

    private SharedPreferences mSharedPreferences;
    private Constants mConstants;
    private String mAppId;
    private MenuAppSettings mMenuAppSettings;

    public static Intent newIntent(Context context, String appId, MenuAppSettings menuAppSettings){
        Intent intent = new Intent(context, MenuActivity.class);
        intent.putExtra(EXTRA_APP_ID, appId);
        intent.putExtra(EXTRA_APP_SETTINGS, menuAppSettings);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ButterKnife.bind(this);

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

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        if(mSharedPreferences != null){
            String title = mSharedPreferences.getString(mConstants.APPNAME, null);
            actionBar.setTitle(title + ": Menu");
        } else {
            actionBar.setTitle("Menu");
        }

        MenuFragment menuFragment = (MenuFragment)
                getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if(menuFragment == null){
            menuFragment = MenuFragment.newInstance(mAppId, mMenuAppSettings);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                        menuFragment, R.id.contentFrame);
        }

        DaggerMenuComponent.builder()
                .menuPresenterModule(new MenuPresenterModule(menuFragment, mAppId, mMenuAppSettings))
                .baseComponent(((MobitillApplication) getApplication()).getBaseComponent())
                .build()
                .inject(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(ARGS_APP_ID, mAppId);
        outState.putSerializable(ARGS_APP_SETTINGS, mMenuAppSettings);
        super.onSaveInstanceState(outState);
    }
}
