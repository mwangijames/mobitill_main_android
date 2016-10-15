package com.mobitill.mobitill_2.cashiersdetail;

import com.mobitill.mobitill_2.BaseComponent;
import com.mobitill.mobitill_2.utils.FragmentScoped;

import dagger.Component;

/**
 * Created by james on 10/13/2016.
 */
@FragmentScoped
@Component(dependencies = {BaseComponent.class},
            modules = {CashierDetailPresenterModule.class})
public interface CashierDetailComponent {
    void inject(CashierDetailActivity cashierDetailActivity);
    AppId appId();
    CashierGson cashierGson();
}
