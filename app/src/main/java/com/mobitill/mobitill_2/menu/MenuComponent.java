package com.mobitill.mobitill_2.menu;

import com.mobitill.mobitill_2.BaseComponent;
import com.mobitill.mobitill_2.utils.FragmentScoped;

import dagger.Component;

/**
 * Created by james on 10/31/2016.
 */
@FragmentScoped
@Component(dependencies = {BaseComponent.class},
            modules = {MenuPresenterModule.class})
public interface MenuComponent {
    void inject(MenuActivity menuActivity);
}
