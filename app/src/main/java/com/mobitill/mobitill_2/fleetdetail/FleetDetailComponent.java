package com.mobitill.mobitill_2.fleetdetail;

import com.mobitill.mobitill_2.BaseComponent;
import com.mobitill.mobitill_2.utils.FragmentScoped;

import dagger.Component;

/**
 * Created by james on 10/27/2016.
 */
@FragmentScoped
@Component(dependencies = {BaseComponent.class}, modules = FleetDetailPresenterModule.class)
public interface FleetDetailComponent {
    void inject(FleetDetailActivity fleetDetailActivity);
}
