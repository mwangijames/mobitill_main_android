package com.mobitill.mobitill_2.data.models.cashiers;

import android.content.SharedPreferences;

import com.mobitill.mobitill_2.Constants;
import com.mobitill.mobitill_2.data.Remote;
import com.mobitill.mobitill_2.data.models.cashiers.models.CashiersFetch;
import com.mobitill.mobitill_2.data.models.cashiers.models.CashiersParams;
import com.mobitill.mobitill_2.data.models.cashiers.models.CashiersQuery;
import com.mobitill.mobitill_2.data.models.cashiers.remote.CashiersRemoteDataSource;
import com.mobitill.mobitill_2.data.models.products.models.ProductsFetch;
import com.mobitill.mobitill_2.data.models.products.models.ProductsParams;
import com.mobitill.mobitill_2.data.models.products.models.ProductsQuery;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by james on 9/16/2016.
 */
@Module
public class CashiersRepositoryModule {
    @Provides
    @Remote
    CashiersDataSource provideCashiersRemoteDataSource(Retrofit retrofit, SharedPreferences sharedPreferences,
                                                       Constants constants, CashiersQuery cashiersQuery,
                                                       CashiersParams cashiersParams, CashiersFetch cashiersFetch){
        return new CashiersRemoteDataSource(retrofit,sharedPreferences,
                constants,cashiersQuery,
                cashiersParams,cashiersFetch);
    }
}
