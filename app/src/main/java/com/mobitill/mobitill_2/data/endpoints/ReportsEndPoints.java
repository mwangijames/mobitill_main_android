package com.mobitill.mobitill_2.data.endpoints;

import com.mobitill.mobitill_2.data.models.reports.models.Query;
import com.mobitill.mobitill_2.data.models.reports.models.Reports;

import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by james on 9/2/2016.
 */
@Singleton
public interface ReportsEndPoints {
    @POST("transactions/query")
    Call<Reports> fetchReports(@Body Query query);
}