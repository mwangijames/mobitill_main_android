package com.mobitill.mobitill_2.products;

import com.mobitill.mobitill_2.BaseComponent;
import com.mobitill.mobitill_2.reports.ReportsPresenter;
import com.mobitill.mobitill_2.utils.FragmentScoped;

import dagger.Component;

/**
 * Created by james on 9/19/2016.
 */
@FragmentScoped
@Component(dependencies = {BaseComponent.class},
            modules = {ProductsPresenterModule.class})
public interface ProductsComponent {
    void inject(ProductsActivity productsActivity);
}
