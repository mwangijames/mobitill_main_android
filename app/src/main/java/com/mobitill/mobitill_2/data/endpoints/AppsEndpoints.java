package com.mobitill.mobitill_2.data.endpoints;

import com.mobitill.mobitill_2.data.models.apps.models.Apps;
import com.mobitill.mobitill_2.data.models.apps.models.Body;

import retrofit2.Call;
import retrofit2.http.POST;

/**
 * Created by DI on 8/18/2016.
 */
public interface AppsEndpoints {
    @POST("apps/ofid")
    Call<Apps> fetchApps(@retrofit2.http.Body Body body);
}