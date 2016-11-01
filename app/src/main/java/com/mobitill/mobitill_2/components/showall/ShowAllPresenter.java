package com.mobitill.mobitill_2.components.showall;

import com.mobitill.mobitill_2.components.ShowAllUtils;

import javax.inject.Inject;

/**
 * Created by james on 11/1/2016.
 */

public class ShowAllPresenter implements ShowAllContract.Presenter {

    private final ShowAllContract.View mView;
    private final ShowAllUtils mShowAllUtils;

    @Inject
    ShowAllPresenter(ShowAllContract.View view,
                     ShowAllUtils showAllUtils){
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
}
