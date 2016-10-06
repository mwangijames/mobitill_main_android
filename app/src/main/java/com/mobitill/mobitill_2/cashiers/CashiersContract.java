package com.mobitill.mobitill_2.cashiers;

import android.support.annotation.NonNull;

import com.mobitill.mobitill_2.BasePresenter;
import com.mobitill.mobitill_2.BaseView;
import com.mobitill.mobitill_2.data.models.cashiers.models.Cashier;
import com.mobitill.mobitill_2.products.ProductsContract;

import java.util.List;

/**
 * Created by james on 9/22/2016.
 */

public interface CashiersContract {

    interface View extends BaseView<Presenter>{
        void showLoadingIndicator(boolean show);
        void showNoCashiers(boolean show);
        void showNoNetwork(boolean show);
        void showAddCashier(String appId);
        void showCashierDeleted(Cashier cashier);
        void showCashierDeleteFailed(String name);
        void deleteRows();
        void hideTitle();
        void showTitle();
        void showCashiers(List<Cashier> cashiers);
        boolean isActive();
    }

    interface Presenter extends BasePresenter{
        void fetchCashiers(String appId);
        void addNewCashier(String appId);
        void deleteCashier(String appId, Cashier cashier);
    }
}
