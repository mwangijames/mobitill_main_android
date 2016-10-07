package com.mobitill.mobitill_2.clientsaddedit;

import com.mobitill.mobitill_2.BaseComponent;
import com.mobitill.mobitill_2.utils.FragmentScoped;

import dagger.Component;

/**
 * Created by james on 10/7/2016.
 */
@FragmentScoped
@Component(dependencies = {BaseComponent.class},
            modules = {ClientAddEditPresenterModule.class})
public interface ClientAddEditComponent {
    void inject(ClientAddEditActivity clientAddEditActivity);
}
