package com.mobitill.mobitill_2.login;

import dagger.Module;
import dagger.Provides;

/**
 * Created by DI on 8/5/2016.
 */
@Module
public class LoginPresenterModule  {
    private final LoginContract.View mView;

    public LoginPresenterModule(LoginContract.View view){
        mView = view;
    }

    @Provides
    LoginContract.View provideLoginContractView(){
        return mView;
    }

}
