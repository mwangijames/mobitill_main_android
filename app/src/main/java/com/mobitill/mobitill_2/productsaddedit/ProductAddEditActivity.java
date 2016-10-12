package com.mobitill.mobitill_2.productsaddedit;

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
import com.mobitill.mobitill_2.fleetaddedit.FleetAddEditActivity;
import com.mobitill.mobitill_2.fleetaddedit.FleetAddEditPresenter;
import com.mobitill.mobitill_2.utils.ActivityUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ProductAddEditActivity extends AppCompatActivity {

    public static final String TAG = ProductAddEditActivity.class.getSimpleName();
    public static final String EXTRA_APP_ID = "extra_app_id";
    public static final String ARGS_APP_ID = "args_app_id";

    @Inject ProductAddEditPresenter mPresenter;
    SharedPreferences mSharedPreferences;
    Constants mConstants;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    String mAppId;

    public static Intent newIntent(Context context, String appId){
        Intent intent = new Intent(context, ProductAddEditActivity.class);
        intent.putExtra(EXTRA_APP_ID, appId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_add_edit);

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
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        if(mSharedPreferences != null){
            String title = mSharedPreferences.getString(mConstants.APPNAME, null);
            actionBar.setTitle(title + ": Add Product");
        } else {
            actionBar.setTitle("Add Product");
        }

        ProductAddEditFragment productAddEditFragment = (ProductAddEditFragment)
                getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if(productAddEditFragment == null){
            productAddEditFragment = productAddEditFragment.newInstance(mAppId);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    productAddEditFragment, R.id.contentFrame);
        }

        DaggerProductAddEditComponent.builder()
                .productAddEditPresenterModule(new ProductAddEditPresenterModule(productAddEditFragment, mAppId))
                .baseComponent(((MobitillApplication) getApplication()).getBaseComponent())
                .build()
                .inject(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(ARGS_APP_ID, mAppId);
        super.onSaveInstanceState(outState);
    }
}
