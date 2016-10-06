package com.mobitill.mobitill_2.fleet;

import com.mobitill.mobitill_2.BaseComponent;
import com.mobitill.mobitill_2.clients.ClientsActivity;
import com.mobitill.mobitill_2.utils.FragmentScoped;

import dagger.Component;

/**
 * Created by james on 9/27/2016.
 */
@FragmentScoped
@Component(dependencies = {BaseComponent.class},
            modules = {FleetPresenterModule.class})
public interface FleetComponent {
    void inject(FleetActivity fleetActivity);
}
