package com.mobitill.mobitill_2.data.endpoints;

import com.mobitill.mobitill_2.data.models.cashiers.models.Cashiers;
import com.mobitill.mobitill_2.data.models.cashiers.models.CashiersQuery;
import com.mobitill.mobitill_2.data.models.cashiers.models.create.CashierCreateQuery;
import com.mobitill.mobitill_2.data.models.cashiers.models.create.CashierCreateResponse;
import com.mobitill.mobitill_2.data.models.cashiers.models.create.CashierCreateResponseData;
import com.mobitill.mobitill_2.data.models.cashiers.models.delete.CashierDeleteQuery;
import com.mobitill.mobitill_2.data.models.cashiers.models.delete.CashierDeleteResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by james on 9/16/2016.
 */
public interface CashiersEndPoints {
    @POST("cashiers/fetch")
    Call<Cashiers> fetchCashiers(@Body CashiersQuery cashiersQuery);

    @POST("cashiers/insert")
    Call<CashierCreateResponse> insertCashier(@Body CashierCreateQuery cashierCreateQuery);

    @POST("cashiers/delete")
    Call<CashierDeleteResponse> deleteCashier(@Body CashierDeleteQuery cashierDeleteQuery);
}
