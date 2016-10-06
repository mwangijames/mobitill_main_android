package com.mobitill.mobitill_2.data.models.reports;

import android.support.annotation.NonNull;

import com.mobitill.mobitill_2.data.Local;
import com.mobitill.mobitill_2.data.Remote;
import com.mobitill.mobitill_2.data.models.reports.models.ReportItem;
import com.mobitill.mobitill_2.data.models.reports.models.ReportsLocal;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by james on 9/2/2016.
 */
public class ReportsRepository implements ReportsDataSource{

    private final ReportsDataSource mReportsRemoteDataSource;
    private final ReportsDataSource mReportsLocalDataSource;

    @Inject
    ReportsRepository(@Remote ReportsDataSource reportsRemoteDataSource,
                      @Local ReportsDataSource reportsLocalDataSource){
        mReportsRemoteDataSource = reportsRemoteDataSource;
        mReportsLocalDataSource = reportsLocalDataSource;
    }



    @Override
    public void getReports(List<Long> dateRange, String cashier, String productId, String appId, @NonNull final LoadReportsCallback callback) {
        mReportsRemoteDataSource.getReports(dateRange, cashier, productId, appId, new LoadReportsCallback() {
            @Override
            public void onLocalReportsLoaded(List<ReportsLocal> reportsLocalList) {
                callback.onLocalReportsLoaded(reportsLocalList);
            }

            @Override
            public void onRemoteDataLoaded(List<ReportItem> reportItemList) {
                callback.onRemoteDataLoaded(reportItemList);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
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
