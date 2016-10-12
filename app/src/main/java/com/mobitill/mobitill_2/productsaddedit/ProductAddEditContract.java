package com.mobitill.mobitill_2.productsaddedit;

import com.mobitill.mobitill_2.BasePresenter;
import com.mobitill.mobitill_2.BaseView;

import java.util.List;

/**
 * Created by james on 10/12/2016.
 */

public interface ProductAddEditContract {
    interface View extends BaseView<Presenter>{
        void showNoNetwork(boolean show);
        void showProductList();
        void showProductCreateFailed();
        void showNoFields();
        boolean isActive();
    }

    interface Presenter extends BasePresenter{
        void saveProduct(String appId, String identifier, String name, List<String> categories,
                         String description, String price, String size, String vat);
    }
}
