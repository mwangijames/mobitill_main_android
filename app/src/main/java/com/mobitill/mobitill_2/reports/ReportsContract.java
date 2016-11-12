package com.mobitill.mobitill_2.reports;

import com.google.common.base.Strings;
import com.mobitill.mobitill_2.BasePresenter;
import com.mobitill.mobitill_2.BaseView;
import com.mobitill.mobitill_2.data.models.cashiers.models.Cashier;
import com.mobitill.mobitill_2.data.models.products.models.Product;
import com.mobitill.mobitill_2.data.models.reports.models.ReportItem;

import java.util.ArrayList;
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
        void showTotal(String total);
        boolean isActive();
        void showQuantity(int quantity);
        void showMenuItems(List<String> models);
        void setUpFilterView(HashMap<String, List<HashMap<String, String>>> filterItems);
    }

    interface Presenter extends BasePresenter{
        void sendQuery();
        void fetchReport(List<Long> range, HashMap<String, String> items);
        void getTotal(List<HashMap<String, String>> report);
        String getFormattedDate(Date date);
        Calendar getMidnight();
         void getMenuList();
    }
}
