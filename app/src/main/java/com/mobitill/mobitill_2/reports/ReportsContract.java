package com.mobitill.mobitill_2.reports;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.mobitill.mobitill_2.BasePresenter;
import com.mobitill.mobitill_2.BaseView;

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
        void createChart(String title, BarDataSet barDataSet, BarData barData, ArrayList<String> labels);
        void removeChartLayoutViews();
        void createDayChart(String title, PieDataSet pieDataSet, PieData pieData, ArrayList<String> labels);
        void showDateChart(String Title, LineDataSet lineDataSet, LineData lineData,ArrayList<String> labels);
        void showFetchReportFailed();
    }

    interface Presenter extends BasePresenter{
        void sendQuery();
        void fetchReport(List<Long> range, HashMap<String, String> items);
        void getTotal(List<HashMap<String, String>> report);
        String getFormattedDate(Date date);
        Calendar getMidnight();
        void getMenuList();
        void createDateChart(List<Long> range, List<HashMap<String, String>> report);
    }
}
