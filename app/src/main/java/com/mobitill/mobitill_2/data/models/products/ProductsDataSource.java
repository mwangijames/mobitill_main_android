package com.mobitill.mobitill_2.data.models.products;

import android.support.annotation.NonNull;

import com.mobitill.mobitill_2.data.models.products.models.Product;

import java.util.List;

/**
 * Created by james on 9/13/2016.
 */
public interface ProductsDataSource {
    interface LoadProductsCallBack{
        void onProductsLoaded(List<Product> productList);
        void onDataNotAvailable();
    }

    void getProducts(String appId, @NonNull LoadProductsCallBack callBack);
}
