package com.mobitill.mobitill_2.data.models.cashiers;

import android.support.annotation.NonNull;

import com.mobitill.mobitill_2.data.Remote;
import com.mobitill.mobitill_2.data.models.cashiers.models.Cashier;
import com.mobitill.mobitill_2.data.models.cashiers.models.create.CashierCreateQuery;
import com.mobitill.mobitill_2.data.models.cashiers.models.create.CashierCreateResponse;
import com.mobitill.mobitill_2.data.models.cashiers.models.create.CashierCreateResponseData;
import com.mobitill.mobitill_2.data.models.cashiers.models.delete.CashierDeleteQuery;
import com.mobitill.mobitill_2.data.models.cashiers.models.delete.CashierDeleteResponse;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by james on 9/16/2016.
 */
public class CashiersRepository implements CashiersDataSource {

    private final CashiersDataSource mCashiersRemoteDataSource;

    @Inject
    CashiersRepository(@Remote CashiersDataSource cashiersRemoteDataSource){
        mCashiersRemoteDataSource = cashiersRemoteDataSource;
    }

    @Override
    public void getCashiers(String appId, @NonNull final LoadCashiersCallback callback) {
        mCashiersRemoteDataSource.getCashiers(appId, new LoadCashiersCallback() {
            @Override
            public void onCashiersLoaded(List<Cashier> cashierList) {
                callback.onCashiersLoaded(cashierList);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void createCashier(CashierCreateQuery cashierCreateQuery, @NonNull final CreateCashiersCallBack callBack) {
        mCashiersRemoteDataSource.createCashier(cashierCreateQuery, new CreateCashiersCallBack() {
            @Override
            public void onCashiersCreated(CashierCreateResponse cashierCreateResponse) {
                callBack.onCashiersCreated(cashierCreateResponse);
            }

            @Override
            public void onCashierCreateFail() {
                callBack.onCashierCreateFail();
            }
        });
    }

    @Override
    public void deleteCashier(CashierDeleteQuery cashierDeleteQuery, @NonNull final DeleteCashierCallBack callBack) {
            mCashiersRemoteDataSource.deleteCashier(cashierDeleteQuery, new DeleteCashierCallBack() {
                @Override
                public void onCashierDeleted(CashierDeleteResponse cashierDeleteResponse) {
                    callBack.onCashierDeleted(cashierDeleteResponse);
                }

                @Override
                public void onCashierNotDeleted() {
                    callBack.onCashierNotDeleted();
                }
            });
    }


}
