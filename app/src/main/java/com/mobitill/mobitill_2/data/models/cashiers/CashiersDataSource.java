package com.mobitill.mobitill_2.data.models.cashiers;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mobitill.mobitill_2.data.models.cashiers.models.Cashier;
import com.mobitill.mobitill_2.data.models.cashiers.models.create.CashierCreateQuery;
import com.mobitill.mobitill_2.data.models.cashiers.models.create.CashierCreateResponse;
import com.mobitill.mobitill_2.data.models.cashiers.models.delete.CashierDeleteQuery;
import com.mobitill.mobitill_2.data.models.cashiers.models.delete.CashierDeleteResponse;

import java.util.List;

/**
 * Created by james on 9/16/2016.
 */
public interface CashiersDataSource {
    interface LoadCashiersCallback{
        void onCashiersLoaded(List<Cashier> cashierList);
        void onDataNotAvailable();
    }

    interface CreateCashiersCallBack{
        void onCashiersCreated(CashierCreateResponse cashierCreateResponse);
        void onCashierCreateFail();
    }

    interface DeleteCashierCallBack{
        void onCashierDeleted(CashierDeleteResponse cashierDeleteResponse);
        void onCashierNotDeleted();
    }

    void getCashiers(String appId, @NonNull LoadCashiersCallback callback);

    void createCashier(CashierCreateQuery cashierCreateQuery, @NonNull CreateCashiersCallBack callBack);

    void deleteCashier(CashierDeleteQuery cashierDeleteQuery, @NonNull DeleteCashierCallBack callBack);
}
