package com.mobitill.mobitill_2.components.addedit;

import android.support.annotation.Nullable;

import com.mobitill.mobitill_2.components.ShowAllUtils;

import java.util.HashMap;

import dagger.Module;
import dagger.Provides;

/**
 * Created by james on 11/4/2016.
 */
@Module
public class AddEditPresenterModule {

    private final AddEditContract.View mView;
    private final ShowAllUtils mShowAllUtils;
    private final HashMap<String, String> mItem;

    public AddEditPresenterModule(
            AddEditContract.View view,
            ShowAllUtils showAllUtils,
            @Nullable HashMap<String, String> item
    ){
        mView = view;
        mShowAllUtils = showAllUtils;
        mItem = item;
    }

    @Provides
    AddEditContract.View provideAddEditView(){
        return mView;
    }

    @Provides
    ShowAllUtils provideShowAllUtils(){
        return mShowAllUtils;
    }

    @Provides
    @Nullable
    HashMap<String, String> provideItem(){
        return mItem;
    }

}
