package com.mobitill.mobitill_2.sync;

import com.mobitill.mobitill_2.BaseComponent;
import com.mobitill.mobitill_2.utils.FragmentScoped;

import dagger.Component;

/**
 * Created by james on 11/28/2016.
 */
@FragmentScoped
@Component(dependencies = {BaseComponent.class})
public interface MobitillSyncComponent {
    void inject(MobitillSyncAdapter mobitillSyncAdapter);
}
