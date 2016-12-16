package com.mobitill.mobitill_2.apps;

import com.mobitill.mobitill_2.BaseComponent;
import com.mobitill.mobitill_2.utils.FragmentScoped;

import dagger.Component;

/**
 * Created by DI on 8/10/2016.
 */
@FragmentScoped
@Component(dependencies = BaseComponent.class, modules = AppsPresenterModule.class)
public interface AppsComponent {
    void inject(AppsActivity appsActivity);
}
