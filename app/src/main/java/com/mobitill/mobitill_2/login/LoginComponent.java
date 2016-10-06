package com.mobitill.mobitill_2.login;

import com.mobitill.mobitill_2.BaseComponent;
import com.mobitill.mobitill_2.utils.FragmentScoped;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by DI on 8/5/2016.
 */
@FragmentScoped
@Component(dependencies = {BaseComponent.class}, modules = {LoginPresenterModule.class})
public interface LoginComponent {
    void inject(LoginActivity loginActivity);
}
