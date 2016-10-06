package com.mobitill.mobitill_2.clients;

import com.mobitill.mobitill_2.BaseComponent;
import com.mobitill.mobitill_2.utils.FragmentScoped;

import dagger.Component;

/**
 * Created by james on 9/26/2016.
 */
@FragmentScoped
@Component(dependencies = {BaseComponent.class},
            modules = {ClientsPresenterModule.class})
public interface ClientsComponent {
    void inject(ClientsActivity clientsActivity);
}
