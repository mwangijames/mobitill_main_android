package com.mobitill.mobitill_2.productsaddedit;

import android.support.annotation.Nullable;
import android.util.Log;

import com.mobitill.mobitill_2.data.models.products.ProductsRepository;
import com.mobitill.mobitill_2.data.models.products.models.create.ProductCreateParams;
import com.mobitill.mobitill_2.data.models.products.models.create.ProductCreateQuery;
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
        Log.i(TAG, "saveProduct: Called");
    }

    @Override
    public void start() {

    }
}
