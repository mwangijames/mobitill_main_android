package com.mobitill.mobitill_2.data.models.reports;

import android.support.annotation.NonNull;

import com.mobitill.mobitill_2.data.models.reports.models.ReportItem;
import com.mobitill.mobitill_2.data.models.reports.models.Reports;
import com.mobitill.mobitill_2.data.models.reports.models.ReportsLocal;

import java.util.List;

/**
 * Created by james on 9/2/2016.
 */
public interface ReportsDataSource {

    interface LoadReportsCallback{
        void onLocalReportsLoaded(List<ReportsLocal> reportsLocalList);
        void onRemoteDataLoaded(List<ReportItem> reportItemList);
        void onDataNotAvailable();
    }


    void getReports(List<Long> dateRange, String cashier,
                    String productId, String appId, @NonNull LoadReportsCallback callback);

    void saveReports(@NonNull List<ReportItem> reportItemList);
    void deleteAllReports();
    void refreshReports();
}
