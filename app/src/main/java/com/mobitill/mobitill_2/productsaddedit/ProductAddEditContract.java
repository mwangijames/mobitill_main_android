package com.mobitill.mobitill_2.productsaddedit;

import com.mobitill.mobitill_2.BasePresenter;
import com.mobitill.mobitill_2.BaseView;

/**
 * Created by james on 10/11/2016.
 */

public interface ProductAddEditContract {
    interface View extends BaseView<Presenter>{
        void showNoNetwork(boolean show);
        void showTitle();
        void showProductsList();
        void showProductCreateFailed();
        void showNoFields();
        boolean isActive();
    }
    interface Presenter extends BasePresenter{
        void saveProduct();
    }
}
