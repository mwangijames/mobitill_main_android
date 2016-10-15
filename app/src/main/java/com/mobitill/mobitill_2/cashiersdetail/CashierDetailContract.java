package com.mobitill.mobitill_2.cashiersdetail;

import com.mobitill.mobitill_2.BasePresenter;
import com.mobitill.mobitill_2.BaseView;
import com.mobitill.mobitill_2.data.models.cashiers.models.Cashier;

/**
 * Created by james on 10/13/2016.
 */

public interface CashierDetailContract {

    interface View extends BaseView<Presenter> {
        void showLoadingIndicator(boolean show);
        void showNoNetwork(boolean show);
        void showMissingCashier();
        void showCashier(Cashier cashier);
        void showEditCashier(String cashierGson);
        boolean isActive();
    }
    interface Presenter extends BasePresenter {
        void convertJson(String cashierGsonString);
        void editCashier();
        void deleteCashier();
    }
}
