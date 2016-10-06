package com.mobitill.mobitill_2.data.endpoints;
import com.mobitill.mobitill_2.data.models.users.User;

import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by DI on 8/2/2016.
 */
@Singleton
public interface UserEndpoints {
    @POST("authorise")
    Call<User> authorise(@Body User user);
}