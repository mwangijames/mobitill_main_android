package com.mobitill.mobitill_2.reports;

import com.mobitill.mobitill_2.Constants;
import com.mobitill.mobitill_2.MobitillApplication;
import com.mobitill.mobitill_2.R;
import com.mobitill.mobitill_2.apps.AppsFragment;
import com.mobitill.mobitill_2.products.ProductsActivity;
import com.mobitill.mobitill_2.utils.ActivityUtils;
import com.mobitill.mobitill_2.utils.SetUpDrawerContent;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.Set;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReportsActivity extends AppCompatActivity {

    public static final String EXTRA_APP_ID = "APPID";
    private static final String ARGS_APP_ID = "args_app_id";

    @Inject ReportsPresenter mReportsPresenter;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.drawer_layout) DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view) NavigationView mNavigationView;

    SharedPreferences mSharedPreferences;
    Constants mConstants;

    String mAppId;

    public static Intent newIntent(Context context, String appId){
        Intent intent = new Intent(context, ReportsActivity.class);
        intent.putExtra(EXTRA_APP_ID, appId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
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


        // Set up the toolbar.
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        mConstants = new Constants();
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        if(mSharedPreferences != null){
            String title = mSharedPreferences.getString(mConstants.APPNAME, null);
            ab.setTitle(title + ": Reports");
        } else {
            ab.setTitle("Reports");
        }

        mDrawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
        if(mNavigationView != null){
            setupDrawerContent();
        }

        ReportsFragment reportsFragment =
                (ReportsFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (reportsFragment == null){
            reportsFragment = ReportsFragment.newInstance(mAppId);
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), reportsFragment, R.id.contentFrame);
        }

        // create the presenter
        DaggerReportsComponent.builder()
                .reportsPresenterModule(new ReportsPresenterModule(reportsFragment, mAppId))
                .baseComponent(((MobitillApplication) getApplication()).getBaseComponent())
                .build()
                .inject(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
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
        setUpDrawerContent.setupDrawerContent(mNavigationView, ReportsActivity.this, mDrawerLayout);
    }

}
