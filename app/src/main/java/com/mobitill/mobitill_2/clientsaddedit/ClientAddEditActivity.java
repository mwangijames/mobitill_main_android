package com.mobitill.mobitill_2.clientsaddedit;

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
import com.mobitill.mobitill_2.cashiersaddedit.AddEditCashierActivity;
import com.mobitill.mobitill_2.clientsdetail.ClientsAppId;
import com.mobitill.mobitill_2.clientsdetail.ClientsJson;
import com.mobitill.mobitill_2.net.ConnectivityReceiver;
import com.mobitill.mobitill_2.utils.ActivityUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClientAddEditActivity extends AppCompatActivity {

        public static final String TAG = ClientAddEditActivity.class.getSimpleName();
        public static final String EXTRA_APP_ID = "extra_app_id";
        public static final String EXTRA_CLIENTS_JSON = "extra_clients_json";
        public static final String ARGS_CLIENTS_JSON = "args_clients_json";
        public static final String ARGS_APP_ID = "args_app_id";


    @Inject ClientAddEditPresenter mPresenter;
    SharedPreferences mSharedPreferences;
    Constants mConstants;

    @BindView(R.id.toolbar) Toolbar mToolbar;

    ClientsAppId mAppId;
    ClientsJson mClientsJson;

    public static Intent newIntent(Context context, ClientsAppId appId){
        Intent intent = new Intent(context, ClientAddEditActivity.class);
        intent.putExtra(EXTRA_APP_ID, appId);
        return intent;
    }

    public static Intent newIntent(Context context, ClientsAppId appId, ClientsJson clientsJson){
        Intent intent = new Intent(context, ClientAddEditActivity.class);
        intent.putExtra(EXTRA_APP_ID, appId);
        intent.putExtra(EXTRA_CLIENTS_JSON, clientsJson);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_add_edit);
        ButterKnife.bind(this);

        mConstants = new Constants();
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        if(savedInstanceState == null){
            mAppId = (ClientsAppId) getIntent().getSerializableExtra(EXTRA_APP_ID);
            mClientsJson = (ClientsJson) getIntent().getSerializableExtra(EXTRA_CLIENTS_JSON);
            if(mAppId == null){
                mAppId = new ClientsAppId();
                mAppId.setAppId(mSharedPreferences.getString(mConstants.APPID, null));
            }
        } else {
            mAppId = (ClientsAppId) savedInstanceState.getSerializable(ARGS_APP_ID);
            mClientsJson = (ClientsJson) savedInstanceState.getSerializable(ARGS_CLIENTS_JSON);
        }

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        if(mSharedPreferences != null){
            String title = mSharedPreferences.getString(mConstants.APPNAME, null);
            actionBar.setTitle(title + ": Add Client");
        } else {
            actionBar.setTitle("Add Client");
        }

        ClientAddEditFragment clientAddEditFragment = (ClientAddEditFragment) getSupportFragmentManager()
                                                        .findFragmentById(R.id.contentFrame);

        if(clientAddEditFragment  == null){
            if(mClientsJson == null){
                clientAddEditFragment = ClientAddEditFragment.newInstance(mAppId);
            } else {
                clientAddEditFragment = ClientAddEditFragment.newInstance(mAppId, mClientsJson);
            }

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), clientAddEditFragment,
                    R.id.contentFrame);
        }

        DaggerClientAddEditComponent.builder()
                .clientAddEditPresenterModule(new ClientAddEditPresenterModule(clientAddEditFragment, mAppId, mClientsJson))
                .baseComponent(((MobitillApplication) getApplication()).getBaseComponent())
                .build()
                .inject(this);

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(ARGS_APP_ID, mAppId);
        outState.putSerializable(ARGS_CLIENTS_JSON, mClientsJson);
        super.onSaveInstanceState(outState);
    }
}
