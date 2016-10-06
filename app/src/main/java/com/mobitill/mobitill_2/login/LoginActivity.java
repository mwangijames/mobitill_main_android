package com.mobitill.mobitill_2.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mobitill.mobitill_2.MobitillApplication;
import com.mobitill.mobitill_2.R;
import com.mobitill.mobitill_2.utils.ActivityUtils;

import javax.inject.Inject;

public class LoginActivity extends AppCompatActivity{

    @Inject LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginFragment loginFragment =
                    (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if(loginFragment == null){
            loginFragment = loginFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), loginFragment,
                        R.id.contentFrame);
        }

       // Create the Presenter
        DaggerLoginComponent.builder()
                .loginPresenterModule(new LoginPresenterModule(loginFragment))
                .baseComponent(((MobitillApplication) getApplication()).getBaseComponent()).build()
                .inject(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
