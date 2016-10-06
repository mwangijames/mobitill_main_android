package com.mobitill.mobitill_2.data.models.products;

import android.content.SharedPreferences;

import com.mobitill.mobitill_2.Constants;
import com.mobitill.mobitill_2.data.Remote;
import com.mobitill.mobitill_2.data.models.products.models.Products;
import com.mobitill.mobitill_2.data.models.products.models.ProductsFetch;
import com.mobitill.mobitill_2.data.models.products.models.ProductsParams;
import com.mobitill.mobitill_2.data.models.products.models.ProductsQuery;
import com.mobitill.mobitill_2.data.models.products.remote.ProductsRemoteDataSource;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by james on 9/13/2016.
 */
@Module
public class ProductsRepositoryModule {
    @Provides
    @Remote
    ProductsDataSource provideProductsRemoteDataSource(Retrofit retrofit, SharedPreferences sharedPreferences,
                                                       Constants constants, ProductsQuery productsQuery,
                                                       ProductsParams productsParams, ProductsFetch productsFetch){
        return new ProductsRemoteDataSource(retrofit,  sharedPreferences,
                 constants,  productsQuery,
                 productsParams,  productsFetch);
    }
}
