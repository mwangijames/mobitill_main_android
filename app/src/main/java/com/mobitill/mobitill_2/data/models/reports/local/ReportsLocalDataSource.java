package com.mobitill.mobitill_2.data.models.reports.local;

import android.support.annotation.NonNull;

import com.mobitill.mobitill_2.data.models.reports.ReportsDataSource;
import com.mobitill.mobitill_2.data.models.reports.models.ReportItem;

import java.util.List;

/**
 * Created by james on 9/2/2016.
 */
public class ReportsLocalDataSource implements ReportsDataSource{


    @Override
    public void getReports(List<Long> dateRange, String cashier, String productId, String appId, @NonNull LoadReportsCallback callback) {

    }

    @Override
    public void saveReports(@NonNull List<ReportItem> reportItemList) {

    }

    @Override
    public void deleteAllReports() {

    }

    @Override
    public void refreshReports() {

    }
}
