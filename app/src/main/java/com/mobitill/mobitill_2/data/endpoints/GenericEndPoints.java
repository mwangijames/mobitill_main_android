package com.mobitill.mobitill_2.data.endpoints;

import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by james on 11/1/2016.
 */

public interface GenericEndPoints {
    @POST("{model}/{action}")
    Call<String> fetch(@Path("model") String model, @Path("action") String action, @Body RequestBody body);
}

