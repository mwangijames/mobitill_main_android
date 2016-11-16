package com.mobitill.mobitill_2.components.addedit;

import com.mobitill.mobitill_2.BasePresenter;
import com.mobitill.mobitill_2.BaseView;
import com.mobitill.mobitill_2.components.ShowAllUtils;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;

/**
 * Created by james on 11/4/2016.
 */

public interface AddEditContract {

    interface View extends BaseView<Presenter>{
        void showLoading(boolean show);
        void showEmpty(boolean show);
        void showSuccess(boolean show);
        void showFail(boolean fail);
        void showUI(HashMap<String, String[]> schema);
        void showAndPopulateUI(HashMap<String, String[]> schema, HashMap<String, String> item);
        void showNetworkError(boolean show);
        void showDataError(boolean show);
        void showAll(ShowAllUtils showAllUtils);

        void showInvalidIdentifier();
    }

    interface Presenter extends BasePresenter{
        void add(HashMap<String, String> data);
        void edit(HashMap<String, String> data);
        void generateUI();
        void generateAndPopulateUI(HashMap<String, String> item);
        void openShowAll();
        void addStock(HashMap<String, String> createData);
    }

}
