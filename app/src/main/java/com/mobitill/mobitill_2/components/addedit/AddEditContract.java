package com.mobitill.mobitill_2.components.addedit;

import com.mobitill.mobitill_2.BasePresenter;
import com.mobitill.mobitill_2.BaseView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by james on 11/4/2016.
 */

public interface AddEditContract {

    interface View extends BaseView<Presenter>{
        void showLoading(boolean show);
        void showEmpty(boolean show);
        void showForm();
        void showNetworkError(boolean show);
        void showDataError(boolean show);
    }

    interface Presenter extends BasePresenter{
        void add();
    }

}
