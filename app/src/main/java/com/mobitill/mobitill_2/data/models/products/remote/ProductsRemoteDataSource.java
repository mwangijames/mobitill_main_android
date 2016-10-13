package com.mobitill.mobitill_2.data.models.products.remote;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.mobitill.mobitill_2.Constants;
import com.mobitill.mobitill_2.data.endpoints.ProductsEndPoints;
import com.mobitill.mobitill_2.data.models.products.ProductsDataSource;
import com.mobitill.mobitill_2.data.models.products.models.Product;
import com.mobitill.mobitill_2.data.models.products.models.Products;
import com.mobitill.mobitill_2.data.models.products.models.ProductsFetch;
import com.mobitill.mobitill_2.data.models.products.models.ProductsParams;
import com.mobitill.mobitill_2.data.models.products.models.ProductsQuery;
import com.mobitill.mobitill_2.data.models.products.models.create.ProductCreateQuery;
import com.mobitill.mobitill_2.data.models.products.models.create.ProductCreateResponse;
import com.mobitill.mobitill_2.data.models.products.models.delete.ProductDeleteQuery;
import com.mobitill.mobitill_2.data.models.products.models.delete.ProductDeleteResponse;

import java.util.HashSet;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by james on 9/13/2016.
 */
@Singleton
public class ProductsRemoteDataSource implements ProductsDataSource {
    private static final String TAG = ProductsRemoteDataSource.class.getSimpleName();


    private final Retrofit mRetrofit;
    private final SharedPreferences mSharedPreferences;
    private final Constants mConstants;
    private final ProductsFetch mProductsFetch;
    private final ProductsParams mProductsParams;
    private final ProductsQuery mProductsQuery;


    @Inject
    public ProductsRemoteDataSource(Retrofit retrofit, SharedPreferences sharedPreferences,
                                    Constants constants, ProductsQuery productsQuery,
                                    ProductsParams productsParams, ProductsFetch productsFetch){
        mRetrofit = retrofit;
        mSharedPreferences = sharedPreferences;
        mConstants = constants;
        mProductsFetch = productsFetch;
        mProductsParams = productsParams;
        mProductsQuery = productsQuery;
    }


    @Override
    public void getProducts(String appId, @NonNull LoadProductsCallBack callBack) {
        ProductsEndPoints   productsEndPoints = mRetrofit.create(ProductsEndPoints.class);
        String sharedAppId = mSharedPreferences.getString(mConstants.APPID, null);
        if(mProductsParams != null && mProductsQuery != null && mProductsFetch != null){
            mProductsFetch.setAppid(sharedAppId);
            mProductsParams.setFetch(mProductsFetch);
            mProductsQuery.setParams(mProductsParams);
            getRemoteProducts(productsEndPoints, mProductsQuery, callBack);
        }
    }

    @Override
    public void createProduct(ProductCreateQuery productCreateQuery, @NonNull final CreateProductCallBack callBack) {
        ProductsEndPoints productsEndPoints = mRetrofit.create(ProductsEndPoints.class);
        if(productCreateQuery!=null){
            Call<ProductCreateResponse> call = productsEndPoints.createProduct(productCreateQuery);
            call.enqueue(new Callback<ProductCreateResponse>() {
                @Override
                public void onResponse(Call<ProductCreateResponse> call, Response<ProductCreateResponse> response) {
                    if(response.isSuccessful()){
                        callBack.onProductCreated(response.body());
                    } else {
                        callBack.onProductNotCreated();
                    }
                }

                @Override
                public void onFailure(Call<ProductCreateResponse> call, Throwable t) {
                    callBack.onProductNotCreated();
                }
            });
        }
    }

    @Override
    public void deleteProduct(ProductDeleteQuery productDeleteQuery, @NonNull final DeleteProductCallBack callBack) {
        ProductsEndPoints productsEndPoints = mRetrofit.create(ProductsEndPoints.class);
        if(productDeleteQuery != null){
            Call<ProductDeleteResponse> call =productsEndPoints.deleteProduct(productDeleteQuery);
            call.enqueue(new Callback<ProductDeleteResponse>() {
                @Override
                public void onResponse(Call<ProductDeleteResponse> call, Response<ProductDeleteResponse> response) {
                    if(response.isSuccessful()){
                        callBack.onProductDeleted(response.body());
                    } else {
                        callBack.onProductNotDeleted();
                    }
                }

                @Override
                public void onFailure(Call<ProductDeleteResponse> call, Throwable t) {
                    callBack.onProductNotDeleted();
                }
            });
        }
    }

    private void getRemoteProducts(ProductsEndPoints productsEndPoints,
                                   ProductsQuery productsQuery, final LoadProductsCallBack callBack) {
        Call<Products> call = productsEndPoints.fetchProducts(productsQuery);

        call.enqueue(new Callback<Products>() {
            @Override
            public void onResponse(Call<Products> call, Response<Products> response) {
                if(response.isSuccessful()){
                    callBack.onProductsLoaded(response.body().getData());
                } else {
                    callBack.onDataNotAvailable();
                }
            }

            @Override
            public void onFailure(Call<Products> call, Throwable t) {
                callBack.onDataNotAvailable();
            }
        });
    }
}
