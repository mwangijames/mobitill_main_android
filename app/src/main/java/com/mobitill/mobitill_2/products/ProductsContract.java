package com.mobitill.mobitill_2.products;

import com.mobitill.mobitill_2.BaseView;
import com.mobitill.mobitill_2.BasePresenter;
import com.mobitill.mobitill_2.data.models.products.models.Product;

import java.util.List;

/**
 * Created by james on 9/19/2016.
 */
public interface ProductsContract {
    interface View extends BaseView<Presenter>{
        void setLoadingIndicator(boolean active);
        void showNoProducts(boolean show);
        void showNoNetwork(boolean show);
        void hideTitle();
        void showTitle();
        void showProducts(List<Product> products);
        boolean isActive();


    }

    interface Presenter extends BasePresenter{
        void fetchProducts(String appId);
        void createProduct();
    }
}
