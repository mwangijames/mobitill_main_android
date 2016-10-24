package com.mobitill.mobitill_2.cashiersaddedit;

import com.mobitill.mobitill_2.BasePresenter;
import com.mobitill.mobitill_2.BaseView;
import com.mobitill.mobitill_2.cashiersdetail.CashierGson;
import com.mobitill.mobitill_2.data.models.cashiers.models.Cashier;
import com.mobitill.mobitill_2.data.models.cashiers.models.create.CashierCreateResponse;
import com.mobitill.mobitill_2.data.models.cashiers.models.create.CashierCreateResponseData;

/**
 * Created by james on 9/27/2016.
 */

public interface AddEditCashierContract {
    interface View extends BaseView<Presenter>{
        void showNoNetwork(boolean show);
        void showTitle();
        void showEmptyCashierError();
        void showCashiersList();
        void showCashierCreateFailed();
        void showCashierCreated(CashierCreateResponseData cashierCreateResponse);
        void showCashierEdited(CashierCreateResponse cashierCreateResponse);
        void showCashierEditFailed();
        void showCashierDetail(CashierGson cashierGson);
        void showNoApplicationId();
        void populateCashier(Cashier cashier);
        void showNoFields();
        void setName(String name);
        void setUsername(String username);
        void setPassword(String password);
        boolean isActive();
    }

    interface Presenter extends BasePresenter{
        void saveCashier(String appId, String name, String username, String password);
        void populateCashier();
        void editCashier(String appId, String name, String username, String password);
        void openCashierDetail(CashierGson cashierGson);
    }
}
