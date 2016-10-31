package com.mobitill.mobitill_2.menu;

import dagger.Module;
import dagger.Provides;

/**
 * Created by james on 10/31/2016.
 */
@Module
public class MenuPresenterModule {
    private final MenuContract.View mView;
    private final String mAppId;
    private final MenuAppSettings mMenuAppSettings;

    public MenuPresenterModule(MenuContract.View view,
                               String appId,
                               MenuAppSettings menuAppSettings){
        mView = view;
        mAppId = appId;
        mMenuAppSettings = menuAppSettings;
    }

    @Provides
    MenuContract.View provideMenuView(){
        return mView;
    }

    @Provides
    String provideAppId(){
        return  mAppId;
    }

    @Provides
    MenuAppSettings provideAppSettings(){
        return mMenuAppSettings;
    }
}
