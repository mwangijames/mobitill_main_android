package com.mobitill.mobitill_2.reports;

import com.mobitill.mobitill_2.BaseComponent;
import com.mobitill.mobitill_2.data.models.products.ProductsModule;
import com.mobitill.mobitill_2.data.models.products.ProductsRepository;
import com.mobitill.mobitill_2.data.models.products.ProductsRepositoryComponent;
import com.mobitill.mobitill_2.data.models.products.ProductsRepositoryModule;
import com.mobitill.mobitill_2.data.models.reports.ReportsRepositoryComponent;
import com.mobitill.mobitill_2.utils.FragmentScoped;

import dagger.Component;

/**
 * Created by james on 8/31/2016.
 */
@FragmentScoped
@Component(dependencies = {BaseComponent.class},
        modules = {ReportsPresenterModule.class})
public interface ReportsComponent {
    void inject(ReportsActivity reportsActivity);
}
