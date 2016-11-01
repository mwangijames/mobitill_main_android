package com.mobitill.mobitill_2.components.showall;

import com.mobitill.mobitill_2.BaseComponent;
import com.mobitill.mobitill_2.utils.FragmentScoped;

import dagger.Component;

/**
 * Created by james on 11/1/2016.
 */
@FragmentScoped
@Component(modules = {ShowAllPresenterModule.class}, dependencies = {BaseComponent.class})
public interface ShowAllComponent {
    void inject(ShowAllActivity showAllActivity);
}
