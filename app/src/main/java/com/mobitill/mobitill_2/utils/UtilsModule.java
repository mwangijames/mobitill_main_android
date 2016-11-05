package com.mobitill.mobitill_2.utils;

import dagger.Module;
import dagger.Provides;

/**
 * Created by james on 10/31/2016.
 */
@Module
public class UtilsModule {

    @Provides
    SettingsHelper provideSettingsHelper(){
        return new SettingsHelper();
    }

    @Provides
    UIHelper provideUIHelper(){
        return new UIHelper();
    }


}
