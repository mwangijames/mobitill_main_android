package com.mobitill.mobitill_2.fleet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.content.pm.ActivityInfoCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.mobitill.mobitill_2.Constants;
import com.mobitill.mobitill_2.MobitillApplication;
import com.mobitill.mobitill_2.R;
import com.mobitill.mobitill_2.clients.ClientsActivity;
import com.mobitill.mobitill_2.data.models.clients.models.Client;
import com.mobitill.mobitill_2.data.models.fleet.models.FleetFetch;
import com.mobitill.mobitill_2.utils.ActivityUtils;
import com.mobitill.mobitill_2.utils.SetUpDrawerContent;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FleetActivity extends AppCompatActivity {

    private static final String TAG = FleetActivity.class.getSimpleName();
    private static final String EXTRA_APP_ID = "extra_app_id";
    private static final String ARGS_APP_ID = "args_app_id";

    @Inject
    FleetPresenter mFleetPresenter;

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.drawer_layout) DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view) NavigationView mNavigationView;

    String mAppId;

    SharedPreferences mSharedPreferences;
    Constants mConstants;


    public static Intent newIntent(Context context, String appId){
        Intent intent = new Intent(context, FleetActivity.class);
        intent.putExtra(EXTRA_APP_ID, appId);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fleet);
        ButterKnife.bind(this);

        mConstants = new Constants();
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        if(savedInstanceState == null){
            mAppId = getIntent().getStringExtra(EXTRA_APP_ID);
            if(mAppId == null){
                mAppId = mSharedPreferences.getString(mConstants.APPID, null);
            }
        } else {
            mAppId = savedInstanceState.getString(ARGS_APP_ID);
        }

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);



        if(mSharedPreferences != null){
            String title = mSharedPreferences.getString(mConstants.APPNAME, null);
            actionBar.setTitle(title + ": Fleet");
        } else {
            actionBar.setTitle("Fleet");
        }

        mDrawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);

        if(mNavigationView != null){
            setupDrawerContent();
        }

        FleetFragment fleetFragment = (FleetFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if(fleetFragment == null){
            fleetFragment = FleetFragment.newInstance(mAppId);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    fleetFragment, R.id.contentFrame);
        }

        DaggerFleetComponent.builder()
                .fleetPresenterModule(new FleetPresenterModule(fleetFragment, mAppId))
                .baseComponent(((MobitillApplication) getApplication()).getBaseComponent())
                .build()
                .inject(this);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(ARGS_APP_ID, mAppId);
        super.onSaveInstanceState(outState);
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

    private void setupDrawerContent(){
        SetUpDrawerContent setUpDrawerContent = new SetUpDrawerContent(this);
        setUpDrawerContent.setupDrawerContent(mNavigationView, FleetActivity.this, mDrawerLayout);
    }
}
