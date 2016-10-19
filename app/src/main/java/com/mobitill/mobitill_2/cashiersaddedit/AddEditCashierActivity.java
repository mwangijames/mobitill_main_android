package com.mobitill.mobitill_2.cashiersaddedit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.mobitill.mobitill_2.Constants;
import com.mobitill.mobitill_2.MobitillApplication;
import com.mobitill.mobitill_2.R;
import com.mobitill.mobitill_2.cashiersdetail.AppId;
import com.mobitill.mobitill_2.cashiersdetail.CashierGson;
import com.mobitill.mobitill_2.utils.ActivityUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddEditCashierActivity extends AppCompatActivity {

    public static final String TAG = AddEditCashierActivity.class.getSimpleName();
    public static final String EXTRA_APP_ID = "extra_app_id";
    public static final String ARGS_APP_ID = "args_app_id";
    public static final String EXTRA_CASHIER_GSON = "extra_cashier_gson";
    public static final String ARGS_CASHIER_GSON = "args_cashier_gson";
    public static final int REQUEST_ADD_CASHIER = 1;

    @Inject AddEditCashierPresenter mAddEditCashierPresenter;
    SharedPreferences mSharedPreferences;
    Constants mConstants;

    @BindView(R.id.toolbar) Toolbar mToolbar;


    AppId mAppId;
    CashierGson mCashierGson;

    public static Intent newIntent(Context context,String appId){
        Intent intent = new Intent(context, AddEditCashierActivity.class);
        intent.putExtra(EXTRA_APP_ID, appId);
        return intent;
    }

    public static Intent newEditIntent(Context context,String appId, String cashiersGson){
        Intent intent = new Intent(context, AddEditCashierActivity.class);
        intent.putExtra(EXTRA_APP_ID, appId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_cashier);
        ButterKnife.bind(this);

        mConstants = new Constants();
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mAppId = new AppId();
        mCashierGson = new CashierGson();

        if(savedInstanceState == null){
            mAppId.setAppId(getIntent().getStringExtra(EXTRA_APP_ID));
            // TODO: 10/15/2016 resume from here
            if(mAppId == null){
                mAppId.setAppId(mSharedPreferences.getString(mConstants.APPID, null));
            }
        } else {
            mAppId.setAppId(savedInstanceState.getString(ARGS_APP_ID));
        }

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);



        if(mSharedPreferences != null){
            String title = mSharedPreferences.getString(mConstants.APPNAME, null);
            actionBar.setTitle(title + ": Add Cashier");
        } else {
            actionBar.setTitle("Add Cashier");
        }

        AddEditCashierFragment addEditCashierFragment = (AddEditCashierFragment) getSupportFragmentManager()
                                                        .findFragmentById(R.id.contentFrame);
        if(addEditCashierFragment == null){
            addEditCashierFragment = AddEditCashierFragment.newInstance(mAppId, mCashierGson);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    addEditCashierFragment, R.id.contentFrame);
        }
        
        DaggerAddEditCashierComponent.builder()
                .addEditCashierPresenterModule(new AddEditCashierPresenterModule(addEditCashierFragment, mAppId, mCashierGson))
                .baseComponent(((MobitillApplication) getApplication()).getBaseComponent())
                .build()
                .inject(this);
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(ARGS_APP_ID, mAppId);
        super.onSaveInstanceState(outState);
    }
}
