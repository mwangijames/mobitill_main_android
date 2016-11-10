package com.mobitill.mobitill_2.reports;

import com.google.common.base.Strings;
import com.mobitill.mobitill_2.BasePresenter;
import com.mobitill.mobitill_2.BaseView;
import com.mobitill.mobitill_2.data.models.cashiers.models.Cashier;
import com.mobitill.mobitill_2.data.models.products.models.Product;
import com.mobitill.mobitill_2.data.models.reports.models.ReportItem;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by james on 8/31/2016.
 */
public interface ReportsContract {
    interface View extends BaseView<Presenter>{
        void setLoadingIndicator(boolean active);
        void showNoReports();
        void showNoNetwork(boolean show);
        void hideTitle();
        void showTitle();
        void showReports();
        void showTotal(int total);
        void showProducts(List<Product> products);
        void showCashiers(List<Cashier> cashiers);
        boolean isActive();
        void showQuantity(int quantity);
        void showMenuItems(List<String> models);
    }

    interface Presenter extends BasePresenter{
        void sendQuery();
        void fetchReports(String appId, List<Long> dates);
        void fetchReports(String appId, List<Long> dates, String product);
        void fetchReports(String appId, List<Long> dates, String product, String cashier);
        void fetchReport(List<Long> range, HashMap<String, String> items);
        void getQuantity(int quantity);
        void getTotal(List<ReportItem> reportItemList);
        String getFormattedDate(Date date);
        void fetchProducts(String appId);
        void fetchCashiers(String appId);
        Product[] getProductsArray(List<Product> products);
        Cashier[] getCashiersArray(List<Cashier> cashiers);
        Calendar getMidnight();
         void getMenuList();
    }
}
