package com.mobitill.mobitill_2.data.models.products;

import android.support.annotation.NonNull;

import com.mobitill.mobitill_2.data.models.products.models.Product;
import com.mobitill.mobitill_2.data.models.products.models.create.ProductCreateQuery;
import com.mobitill.mobitill_2.data.models.products.models.create.ProductCreateResponse;

import java.util.List;

/**
 * Created by james on 9/13/2016.
 */
public interface ProductsDataSource {
    interface LoadProductsCallBack{
        void onProductsLoaded(List<Product> productList);
        void onDataNotAvailable();
    }

    interface CreateProductCallBack{
        void onProductCreated(ProductCreateResponse productCreateResponse);
        void onProductNotCreated();
    }

    void getProducts(String appId, @NonNull LoadProductsCallBack callBack);

    void createProduct(ProductCreateQuery productCreateQuery, @NonNull CreateProductCallBack callBack);
}
