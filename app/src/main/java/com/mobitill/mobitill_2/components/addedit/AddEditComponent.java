package com.mobitill.mobitill_2.components.addedit;

import com.mobitill.mobitill_2.BaseComponent;
import com.mobitill.mobitill_2.utils.FragmentScoped;

import dagger.Component;

/**
 * Created by james on 11/4/2016.
 */
@FragmentScoped
@Component(dependencies = {BaseComponent.class},
    modules = {AddEditPresenterModule.class})
public interface AddEditComponent {
    void inject(AddEditActivity addEditActivity);
}
