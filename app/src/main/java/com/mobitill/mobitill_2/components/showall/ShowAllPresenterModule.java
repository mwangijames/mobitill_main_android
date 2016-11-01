package com.mobitill.mobitill_2.components.showall;

import com.mobitill.mobitill_2.components.ShowAllUtils;

import dagger.Module;
import dagger.Provides;
import io.realm.annotations.PrimaryKey;

/**
 * Created by james on 11/1/2016.
 */
@Module
public class ShowAllPresenterModule {
    private final ShowAllContract.View mView;
    private final ShowAllUtils mShowAllUtils;


    public ShowAllPresenterModule(
            ShowAllContract.View view,
            ShowAllUtils showAllUtils
    ){
        mView = view;
        mShowAllUtils = showAllUtils;
    }

    @Provides
    ShowAllContract.View provideShowAllView(){
        return mView;
    }

    @Provides
    ShowAllUtils provideShoAllUtils(){
        return mShowAllUtils;
    }
}
