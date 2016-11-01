package com.mobitill.mobitill_2.menu;

import com.mobitill.mobitill_2.BasePresenter;
import com.mobitill.mobitill_2.BaseView;

import java.util.List;

/**
 * Created by james on 10/31/2016.
 */

public interface MenuContract {

    interface View extends BaseView<Presenter>{
        void showMenuItems(List<String> models);
    }

    interface Presenter extends BasePresenter{
        void getMenuList();
    }

}
