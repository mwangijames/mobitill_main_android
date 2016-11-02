package com.mobitill.mobitill_2.components.showall;

import com.mobitill.mobitill_2.BasePresenter;
import com.mobitill.mobitill_2.BaseView;

/**
 * Created by james on 11/1/2016.
 */

public interface ShowAllContract {

    interface View extends BaseView<Presenter>{
        void show();
    }

    interface Presenter extends BasePresenter{
        void fetch();
    }

}
