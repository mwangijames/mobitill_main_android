package com.mobitill.mobitill_2.components.addedit;

import com.mobitill.mobitill_2.components.ShowAllUtils;

import dagger.Module;
import dagger.Provides;

/**
 * Created by james on 11/4/2016.
 */
@Module
public class AddEditPresenterModule {

    private final AddEditContract.View mView;
    private final ShowAllUtils mShowAllUtils;

    public AddEditPresenterModule(
            AddEditContract.View view,
            ShowAllUtils showAllUtils
    ){
        mView = view;
        mShowAllUtils = showAllUtils;
    }

    @Provides
    AddEditContract.View provideAddEditView(){
        return mView;
    }

    @Provides
    ShowAllUtils provideShowAllUtils(){
        return mShowAllUtils;
    }

}
