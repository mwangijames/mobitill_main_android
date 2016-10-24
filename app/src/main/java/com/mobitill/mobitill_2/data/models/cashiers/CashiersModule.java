package com.mobitill.mobitill_2.data.models.cashiers;

import com.mobitill.mobitill_2.data.models.cashiers.models.Cashier;
import com.mobitill.mobitill_2.data.models.cashiers.models.Cashiers;
import com.mobitill.mobitill_2.data.models.cashiers.models.CashiersFetch;
import com.mobitill.mobitill_2.data.models.cashiers.models.CashiersParams;
import com.mobitill.mobitill_2.data.models.cashiers.models.CashiersQuery;
import com.mobitill.mobitill_2.data.models.cashiers.models.create.CashierCreateParams;
import com.mobitill.mobitill_2.data.models.cashiers.models.create.CashierCreateQuery;
import com.mobitill.mobitill_2.data.models.cashiers.models.create.CashierCreateResponse;
import com.mobitill.mobitill_2.data.models.cashiers.models.create.CashierCreateResponseData;
import com.mobitill.mobitill_2.data.models.cashiers.models.create.CashierEditParams;
import com.mobitill.mobitill_2.data.models.cashiers.models.create.CashierEditQuery;
import com.mobitill.mobitill_2.data.models.cashiers.models.delete.CashierDeleteParams;
import com.mobitill.mobitill_2.data.models.cashiers.models.delete.CashierDeleteQuery;
import com.mobitill.mobitill_2.data.models.cashiers.models.delete.CashierDeleteResponse;
import com.mobitill.mobitill_2.data.models.cashiers.models.delete.CashierDeleteResponseData;

import dagger.Module;
import dagger.Provides;

/**
 * Created by james on 9/16/2016.
 */
@Module
public class CashiersModule {

    @Provides
    Cashier provideCashier() {
        return new Cashier();
    }

    @Provides
    Cashiers provideCashiers(){
        return new Cashiers();
    }

    @Provides
    CashiersFetch provideCashiersFetch(){
        return new CashiersFetch();
    }

    @Provides
    CashiersParams provideCashiersParams(){
        return new CashiersParams();
    }

    @Provides
    CashiersQuery provideCashiersQuery(){
        return new CashiersQuery();
    }

    @Provides
    CashierCreateParams provideCashierCreateParams(){
        return new CashierCreateParams();
    }

    @Provides
    CashierCreateQuery provideCashierCreateQuery(){
        return new CashierCreateQuery();
    }

    @Provides
    CashierCreateResponseData provideCashierCreateResponseData(){
        return new CashierCreateResponseData();
    }

    @Provides
    CashierCreateResponse provideCreateResponse(){
        return new CashierCreateResponse();
    }

    @Provides
    CashierDeleteParams provideCashierDeleteParams(){
        return new CashierDeleteParams();
    }

    @Provides
    CashierDeleteQuery provideCashierDeleteQuery(){
        return new CashierDeleteQuery();
    }

    @Provides
    CashierDeleteResponse provideCashierDeleteResponse(){
        return new CashierDeleteResponse();
    }

    @Provides
    CashierDeleteResponseData provideCashierDeleteResponseData(){
        return new CashierDeleteResponseData();
    }

    @Provides
    CashierEditParams provideCashierEditParams(){
        return new CashierEditParams();
    }

    @Provides
    CashierEditQuery provideCashierEditQuery(){
        return new CashierEditQuery();
    }

}
