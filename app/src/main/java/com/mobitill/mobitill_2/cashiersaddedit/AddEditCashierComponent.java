package com.mobitill.mobitill_2.cashiersaddedit;

import com.mobitill.mobitill_2.BaseComponent;
import com.mobitill.mobitill_2.utils.FragmentScoped;

import dagger.Component;

/**
 * Created by james on 9/27/2016.
 */
@FragmentScoped
@Component(dependencies =  {BaseComponent.class},
            modules = {AddEditCashierPresenterModule.class})
public interface AddEditCashierComponent {
    void inject(AddEditCashierActivity addEditCashierActivity);
}
