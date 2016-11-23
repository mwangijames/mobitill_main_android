package com.mobitill.mobitill_2.apps;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.mobitill.mobitill_2.MobitillApplication;
import com.mobitill.mobitill_2.R;
import com.mobitill.mobitill_2.sync.MobitillSyncAdapter;
import com.mobitill.mobitill_2.utils.ActivityUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AppsActivity extends AppCompatActivity {
    public static final String TAG = AppsActivity.class.getSimpleName();

    @BindView(R.id.toolbar) Toolbar toolbar;

    @Inject AppsPresenter mAppsPresenter;

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context, AppsActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apps);

        ButterKnife.bind(this);


        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        //ab.hide();
//      ab.setHomeAsUpIndicator(R.drawable.ic_menu);
//      ab.setDisplayHomeAsUpEnabled(true);

        AppsFragment appsFragment =
                (AppsFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (appsFragment == null){
            appsFragment = AppsFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), appsFragment, R.id.contentFrame);
        }

        // Create the presenter
        DaggerAppsComponent.builder()
                .appsPresenterModule(new AppsPresenterModule(appsFragment))
                .appsRepositoryComponent(((MobitillApplication) getApplication()).getAppsRepositoryComponent())
                .build()
                .inject(this);

        MobitillSyncAdapter.initializeSyncAdapter(this);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                // Open the navigation drawer  when the home icon is selected from the toolbar
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


//    @OnClick(R.id.button_sync)
//    public void sync(Button button) {
//        SyncUtils.TriggerRefresh();
//    }

}

