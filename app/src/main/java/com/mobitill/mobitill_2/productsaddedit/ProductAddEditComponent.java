package com.mobitill.mobitill_2.productsaddedit;

import com.mobitill.mobitill_2.BaseComponent;
import com.mobitill.mobitill_2.utils.FragmentScoped;

import dagger.Component;

/**
 * Created by james on 10/12/2016.
 */
@FragmentScoped
@Component(dependencies = {BaseComponent.class},
        modules = {ProductAddEditPresenterModule.class})
public interface ProductAddEditComponent {
    void inject(ProductAddEditActivity productAddEditActivity);
}
