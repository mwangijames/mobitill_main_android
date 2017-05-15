package com.mobitill.mobitill_2.components.transactions;

import com.mobitill.mobitill_2.BaseComponent;
import com.mobitill.mobitill_2.utils.FragmentScoped;

import dagger.Component;

/**
 * Created by andronicus on 5/5/2017.
 */

@FragmentScoped
@Component(dependencies = {BaseComponent.class},
        modules = {TransactionsPresenterModule.class})
public interface TransactionsComponent {

        void inject(TransactionsActivity transactionsActivity);

}
