package com.mobitill.mobitill_2.data.models.products;

import android.support.annotation.NonNull;

import com.mobitill.mobitill_2.data.Remote;
import com.mobitill.mobitill_2.data.models.products.models.Product;
import com.mobitill.mobitill_2.data.models.products.models.create.ProductCreateQuery;
import com.mobitill.mobitill_2.data.models.products.models.create.ProductCreateResponse;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by james on 9/13/2016.
 */
public class ProductsRepository implements ProductsDataSource {


    private final ProductsDataSource mProductsRemoteDataSource;

    @Inject
    ProductsRepository(@Remote ProductsDataSource productsRemoteDataSource){
        mProductsRemoteDataSource = productsRemoteDataSource;
    }

    @Override
    public void getProducts(String appId, @NonNull final LoadProductsCallBack callBack) {
        mProductsRemoteDataSource.getProducts(appId, new LoadProductsCallBack() {
            @Override
            public void onProductsLoaded(List<Product> productList) {
                callBack.onProductsLoaded(productList);
            }

            @Override
            public void onDataNotAvailable() {
                callBack.onDataNotAvailable();
            }
        });
    }

    @Override
    public void createProduct(ProductCreateQuery productCreateQuery, @NonNull final CreateProductCallBack callBack) {
        mProductsRemoteDataSource.createProduct(productCreateQuery, new CreateProductCallBack() {
            @Override
            public void onProductCreated(ProductCreateResponse productCreateResponse) {
                callBack.onProductCreated(productCreateResponse);
            }

            @Override
            public void onProductNotCreated() {
                callBack.onProductNotCreated();
            }
        });
    }
}
