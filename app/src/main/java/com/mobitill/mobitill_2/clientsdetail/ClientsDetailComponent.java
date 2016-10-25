package com.mobitill.mobitill_2.clientsdetail;

import com.mobitill.mobitill_2.BaseComponent;
import com.mobitill.mobitill_2.utils.FragmentScoped;

import dagger.Component;

/**
 * Created by james on 10/25/2016.
 */
@FragmentScoped
@Component(dependencies = {BaseComponent.class}, modules = {ClientsDetailPresenterModule.class})
public interface ClientsDetailComponent {
    void inject(ClientsDetailActivity clientsDetailActivity);
}
