package com.mobitill.mobitill_2.cashiers;

import com.mobitill.mobitill_2.BaseComponent;
import com.mobitill.mobitill_2.utils.FragmentScoped;

import dagger.Component;

/**
 * Created by james on 9/22/2016.
 */
@FragmentScoped
@Component(dependencies = {BaseComponent.class},
            modules = {CashiersPresenterModule.class})
public interface CashiersComponent {
    void inject(CashiersActivity cashiersActivity);
}
