package com.mobitill.mobitill_2.fleetaddedit;

import com.mobitill.mobitill_2.BaseComponent;
import com.mobitill.mobitill_2.utils.FragmentScoped;

import dagger.Component;

/**
 * Created by james on 10/10/2016.
 */
@FragmentScoped
@Component(dependencies = {BaseComponent.class},
            modules = {FleetAddEditPresenterModule.class})
public interface FleetAddEditComponent {
    void inject(FleetAddEditActivity fleetAddEditActivity);
}
