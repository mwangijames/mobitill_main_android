package com.mobitill.mobitill_2.components.showall;

import com.mobitill.mobitill_2.BasePresenter;
import com.mobitill.mobitill_2.BaseView;
import com.mobitill.mobitill_2.components.ShowAllUtils;

import java.util.HashMap;
import java.util.List;

/**
 * Created by james on 11/1/2016.
 */

public interface ShowAllContract {

    interface View extends BaseView<Presenter>{
        void showLoading(boolean show);
        void showEmpty(boolean show);
        void show(List<HashMap<String, String>> items);
        void showNetworkError(boolean show);
        void showDataError(boolean show);
        void delete();
        void showItemDeleted(HashMap<String, String> item);
        void showItemDeleteFailed();
        void showEdit(ShowAllUtils showAllUtils, HashMap<String ,String> item);
        void showHeader(HashMap<String, String> item, boolean show);
    }

    interface Presenter extends BasePresenter{
        void fetch();
        void delete(HashMap<String, String> item);
        void openEdit(HashMap<String ,String> item);
        void fetch(String action);
    }

}
