package com.mobitill.mobitill_2.fleetdetail;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mobitill.mobitill_2.Constants;
import com.mobitill.mobitill_2.MobitillApplication;
import com.mobitill.mobitill_2.R;
import com.mobitill.mobitill_2.utils.ActivityUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FleetDetailActivity extends AppCompatActivity {

    public static final String TAG = FleetDetailActivity.class.getSimpleName();
    public static final String EXTRA_APP_ID = "extra_app_id";
    public static final String ARGS_APP_ID = "args_app_id";
    public static final String EXTRA_FLEET_JSON = "extra_fleet_json";
    public static final String ARGS_FLEET_JSON= "args_fleet_json";

    @Inject FleetDetailPresenter mFleetDetailPresenter;
    private SharedPreferences mSharedPreferences;
    private Constants mConstants;

    @BindView(R.id.toolbar) Toolbar mToolbar;

    private FleetAppId mAppId;
    private FleetJson mFleetJson;

    public static Intent newIntent(Context context, FleetAppId appId, FleetJson fleetJson){
        Intent intent = new Intent(context, FleetDetailActivity.class);
        intent.putExtra(EXTRA_APP_ID, appId);
        intent.putExtra(EXTRA_FLEET_JSON, fleetJson);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fleet_detail);

        ButterKnife.bind(this);

        mConstants = new Constants();
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        if(savedInstanceState == null){
            mAppId = (FleetAppId) getIntent().getSerializableExtra(EXTRA_APP_ID);
            if(mAppId == null){
                mAppId = new FleetAppId();
                mAppId.setAppId(mSharedPreferences.getString(mConstants.APPID, null));
            }
            mFleetJson = (FleetJson) getIntent().getSerializableExtra(EXTRA_FLEET_JSON);
        } else {
            mAppId = (FleetAppId) savedInstanceState.getSerializable(ARGS_APP_ID);
            mFleetJson = (FleetJson) savedInstanceState.getSerializable(ARGS_FLEET_JSON);
        }

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        if(mSharedPreferences != null){
            String title = mSharedPreferences.getString(mConstants.APPNAME, null);
            actionBar.setTitle(title + ": Fleet");
        } else {
            actionBar.setTitle("Fleet");
        }

        FleetDetailFragment fleetDetailFragment =
                (FleetDetailFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if(fleetDetailFragment == null){
            fleetDetailFragment = FleetDetailFragment.newInstance(mAppId, mFleetJson);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    fleetDetailFragment, R.id.contentFrame);
        }

        DaggerFleetDetailComponent.builder()
                .fleetDetailPresenterModule(new FleetDetailPresenterModule(fleetDetailFragment,
                        mAppId, mFleetJson))
                .baseComponent(((MobitillApplication) getApplication()).getBaseComponent())
                .build()
                .inject(this);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(ARGS_APP_ID, mAppId);
        outState.putSerializable(ARGS_FLEET_JSON, mFleetJson);
        super.onSaveInstanceState(outState);
    }
}
