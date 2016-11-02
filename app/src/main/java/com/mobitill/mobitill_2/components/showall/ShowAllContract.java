package com.mobitill.mobitill_2.components.showall;

import com.mobitill.mobitill_2.BasePresenter;
import com.mobitill.mobitill_2.BaseView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by james on 11/1/2016.
 */

public interface ShowAllContract {

    interface View extends BaseView<Presenter>{
        void show(List<HashMap<String, String>> items);
    }

    interface Presenter extends BasePresenter{
        void fetch();
    }

}
