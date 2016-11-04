package com.mobitill.mobitill_2.components.addedit;

import com.mobitill.mobitill_2.components.ShowAllUtils;

import javax.inject.Inject;

/**
 * Created by james on 11/4/2016.
 */

public class AddEditPresenter implements AddEditContract.Presenter {

    private final AddEditContract.View mView;
    private final ShowAllUtils mShowAllUtils;

    @Inject
    public AddEditPresenter(
            AddEditContract.View view,
            ShowAllUtils showAllUtils
    ){
        mView = view;
        mShowAllUtils = showAllUtils;
    }


    /**
     * Method injection is used here to safely reference {@code this} after the object is created.
     * For more information, see Java Concurrency in Practice.
     */
    @Inject
    void setupListeners() {
        mView.setPresenter(this);
    }


    @Override
    public void start() {

    }


    @Override
    public void add() {

    }
}
