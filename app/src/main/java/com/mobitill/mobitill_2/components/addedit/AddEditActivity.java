package com.mobitill.mobitill_2.components.addedit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.mobitill.mobitill_2.Constants;
import com.mobitill.mobitill_2.MobitillApplication;
import com.mobitill.mobitill_2.R;
import com.mobitill.mobitill_2.components.ShowAllUtils;
import com.mobitill.mobitill_2.components.showall.ShowAllActivity;
import com.mobitill.mobitill_2.menu.MenuActivity;
import com.mobitill.mobitill_2.menu.MenuAppSettings;
import com.mobitill.mobitill_2.utils.ActivityUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddEditActivity extends AppCompatActivity {

    @Inject AddEditPresenter mAddEditPresenter;

    public static final String TAG = AddEditActivity.class.getSimpleName();
    private static final String EXTRA_SHOW_ALL_UTILS = "extra_show_all_utils";
    private static final String ARGS_SHOW_ALL_UTILS = "args_show_all_utils";

    public static Intent newIntent(Context context, ShowAllUtils showAllUtils){
        Intent intent = new Intent(context, AddEditActivity.class);
        intent.putExtra(EXTRA_SHOW_ALL_UTILS, showAllUtils);
        return intent;
    }

    private SharedPreferences mSharedPreferences;
    private Constants mConstants;
    private ShowAllUtils mShowAllUtils;

    @BindView(R.id.toolbar) Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        ButterKnife.bind(this);

        mConstants = new Constants();
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        if(savedInstanceState == null){
            mShowAllUtils = (ShowAllUtils) getIntent().getSerializableExtra(EXTRA_SHOW_ALL_UTILS);
        } else {
            mShowAllUtils = (ShowAllUtils) savedInstanceState.getSerializable(ARGS_SHOW_ALL_UTILS);
        }

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

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

        AddEditFragment addEditFragment = (AddEditFragment)
                getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if(addEditFragment == null){
            addEditFragment = AddEditFragment.newInstance(mShowAllUtils);
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    addEditFragment,
                    R.id.contentFrame
            );
        }

        DaggerAddEditComponent.builder()
                .addEditPresenterModule(new AddEditPresenterModule(addEditFragment, mShowAllUtils))
                .baseComponent(((MobitillApplication) getApplication()).getBaseComponent())
                .build()
                .inject(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(ARGS_SHOW_ALL_UTILS, mShowAllUtils);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                goBack();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void goBack(){
        if(mShowAllUtils!=null){
            startActivity(ShowAllActivity.newIntent(this, mShowAllUtils));
        } else {
            finish();
        }
    }
}
