package com.mobitill.mobitill_2.productsaddedit;

import android.support.annotation.Nullable;
import android.util.Log;

import com.mobitill.mobitill_2.data.models.products.ProductsDataSource;
import com.mobitill.mobitill_2.data.models.products.ProductsRepository;
import com.mobitill.mobitill_2.data.models.products.models.create.ProductCreateParams;
import com.mobitill.mobitill_2.data.models.products.models.create.ProductCreateQuery;
import com.mobitill.mobitill_2.data.models.products.models.create.ProductCreateResponse;
import com.mobitill.mobitill_2.fleetaddedit.FleetAddEditPresenter;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by james on 10/12/2016.
 */

public class ProductAddEditPresenter implements ProductAddEditContract.Presenter {


    private static final String TAG = ProductAddEditPresenter.class.getSimpleName();
    private final ProductAddEditContract.View mView;
    private final ProductsRepository mProductsRepository;
    private ProductCreateQuery mProductCreateQuery;
    private ProductCreateParams mProductCreateParams;
    @Nullable String mAppId;

    @Inject
    ProductAddEditPresenter(ProductAddEditContract.View view,
                            ProductsRepository productsRepository,
                            ProductCreateQuery productCreateQuery,
                            ProductCreateParams productCreateParams, 
                            @Nullable String appId){
        mView = view;
        mProductsRepository = productsRepository;
        mProductCreateQuery = productCreateQuery;
        mProductCreateParams = productCreateParams;
        mAppId = appId;
    }

    /**
     * Method injection is used here to safely reference {@code this} after the object is created.
     * For more information, see Java Concurrency in Practice.
     */
    @Inject
    void setupListeners() {
        mView.setPresenter(this);
    }

    @Override
    public void saveProduct(String appId, String identifier, String name, List<String> categories, String description, String price, String size, String vat) {
        if(appId == null || appId.equals("")){
            Log.i(TAG, "saveProduct: App appId must be available");
            return;
        }

        if(mProductCreateParams != null){

            mProductCreateParams.setAppid(appId);
            mProductCreateParams.setIdentifier(identifier);
            mProductCreateParams.setName(name);
            mProductCreateParams.setCategories(categories);
            mProductCreateParams.setDescription(description);
            mProductCreateParams.setPrice(price);
            mProductCreateParams.setSize(size);
            mProductCreateParams.setVat(vat);

            if(mProductCreateQuery!=null){
                mProductCreateQuery.setParams(mProductCreateParams);
                mProductsRepository.createProduct(mProductCreateQuery, new ProductsDataSource.CreateProductCallBack() {
                    @Override
                    public void onProductCreated(ProductCreateResponse productCreateResponse) {
                        mView.showProductCreated(productCreateResponse.getData());
                        mView.showProductList();
                    }

                    @Override
                    public void onProductNotCreated() {
                        mView.showProductCreateFailed();
                    }
                });
            }
        }
    }

    @Override
    public void start() {

    }
}
