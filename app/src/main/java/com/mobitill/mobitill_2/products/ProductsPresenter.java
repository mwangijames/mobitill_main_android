package com.mobitill.mobitill_2.products;

import android.support.annotation.Nullable;
import android.util.Log;

import com.mobitill.mobitill_2.data.models.products.ProductsDataSource;
import com.mobitill.mobitill_2.data.models.products.ProductsRepository;
import com.mobitill.mobitill_2.data.models.products.models.Product;
import com.mobitill.mobitill_2.data.models.products.models.delete.ProductDeleteParams;
import com.mobitill.mobitill_2.data.models.products.models.delete.ProductDeleteQuery;
import com.mobitill.mobitill_2.data.models.products.models.delete.ProductDeleteResponse;
import com.mobitill.mobitill_2.reports.ReportsContract;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by james on 9/19/2016.
 */
public class ProductsPresenter implements ProductsContract.Presenter {

    private final static String TAG = ProductsPresenter.class.getSimpleName();

    private final ProductsContract.View mView;
    private final ProductsRepository mProductsRepository;
    private ProductDeleteQuery mProductDeleteQuery;
    private ProductDeleteParams mProductDeleteParams;
    @Nullable String mAppId;

    @Inject
    ProductsPresenter(ProductsContract.View view,
                      @Nullable String appId,
                      ProductsRepository productsRepository,
                      ProductDeleteParams productDeleteParams,
                      ProductDeleteQuery productDeleteQuery) {
        mView = view;
        mAppId = appId;
        mProductsRepository = productsRepository;
        mProductDeleteParams = productDeleteParams;
        mProductDeleteQuery = productDeleteQuery;
    }

    @Override
    public void fetchProducts(String appId) {
        mProductsRepository.getProducts(appId, new ProductsDataSource.LoadProductsCallBack() {
            @Override
            public void onProductsLoaded(List<Product> productList) {
                mView.setLoadingIndicator(false);
                mView.showProducts(productList);
                mView.showNoProducts(false);
            }

            @Override
            public void onDataNotAvailable() {
                mView.setLoadingIndicator(false);
                mView.showNoProducts(true);
            }
        });
    }

    @Override
    public void createProduct(String appId) {
      mView.showAddEditProduct();
    }

    @Override
    public void deleteProduct(String appId, final Product product) {
        if(appId == null || appId.equals("")){
            Log.i(TAG, "deleteProduct: No appId provided");
        }

        if(mProductDeleteParams != null){
            mProductDeleteParams.setAppid(appId);
            mProductDeleteParams.setItemid(product.getId());

            if(mProductDeleteQuery!=null){
                mProductDeleteQuery.setParams(mProductDeleteParams);
                mProductsRepository.deleteProduct(mProductDeleteQuery, new ProductsDataSource.DeleteProductCallBack() {
                    @Override
                    public void onProductDeleted(ProductDeleteResponse productDeleteResponse) {
                        mView.showProductDeleted(product);
                    }

                    @Override
                    public void onProductNotDeleted() {
                        mView.showProductNotDeleted(product);
                    }
                });
            }
        }

    }


    @Override
    public void start() {
        getProducts();
    }

    private void getProducts(){
        if(null == mAppId || mAppId.isEmpty()){
            return;
        }

        mView.setLoadingIndicator(true);
        fetchProducts(mAppId);

    }

    /**
     * Method injection is used here to safely reference {@code this} after the object is created.
     * For more information, see Java Concurrency in Practice.
     */
    @Inject
    void setupListeners() {
        mView.setPresenter(this);
    }
}