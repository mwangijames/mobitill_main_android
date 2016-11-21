package com.mobitill.mobitill_2.login;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.mobitill.mobitill_2.Constants;
import com.mobitill.mobitill_2.data.endpoints.UserEndpoints;
import com.mobitill.mobitill_2.data.models.users.Error;
import com.mobitill.mobitill_2.data.models.users.ErrorMessage;
import com.mobitill.mobitill_2.data.models.users.User;
import com.mobitill.mobitill_2.data.models.users.UserData;
import com.mobitill.mobitill_2.data.models.users.UserParams;
import com.mobitill.mobitill_2.data.models.users.UserResponse;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by DI on 8/5/2016.
 */
final class LoginPresenter implements LoginContract.Presenter {

    private static final String TAG = LoginPresenter.class.getSimpleName();


    @NonNull
    private final LoginContract.View mView;

    private final Retrofit mRetrofit;
    private UserParams mUserParams;
    private User mUser;
    private UserData mUserData;
    private UserResponse mUserResponse;
    private SharedPreferences mSharedPreferences;
    private Error mError;
    private ErrorMessage mErrorMessage;
    private Constants mConstants;

    @Inject
    LoginPresenter(LoginContract.View view, Retrofit retrofit, UserParams userParams, User user,
                   UserData userData, UserResponse userResponse,
                   SharedPreferences sharedPreferences, Error error, ErrorMessage errorMessage,
                   Constants constants){
        mView = view;
        mRetrofit = retrofit;
        mUserParams = userParams;
        mUser = user;
        mUserData = userData;
        mUserResponse = userResponse;
        mSharedPreferences = sharedPreferences;
        mError = error;
        mErrorMessage = errorMessage;
        mConstants = constants;
    }


    /**
     * Method injection is used here to safely reference {@code this} after the object is created.
     * For more information, see Java Concurrency in Practice.
     */
    @Inject
    void setUpListeners(){
        mView.setPresenter(this);
    }

    @Override
    public void login(String username, String password) {
        mView.showProgress(true);
        UserEndpoints userEndpoints = getUserEndpoints(username, password);
        Call<User> call = userEndpoints.authorise(mUser);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                mView.showProgress(false);
                if(response != null){
                    if(response.code() == 401){
                        Log.i(TAG, "onResponse: " + "Authorisation Error");
                        mView.showLoginFailError();
                    } else if(response.code() == 200){
                        if(response.body() != null){
                            mUserData = response.body().getUserData();
                            //Log.i(TAG, new Gson().toJson(response.errorBody()));
                            // Log.w(TAG,new Gson().toJson(response));
                            Log.i(TAG, "onResponse: " + mUserData.getEmail());
                            mSharedPreferences.edit()
                                    .putString(mConstants.EMAIL, mUserData.getEmail())
                                    .putString(mConstants.USERID, mUserData.getId())
                                    .putString(mConstants.LOGINTIME, mUserData.getTime())
                                    .apply();
                            mView.showTaskActivity();
                        }
                    } else {
                        Log.i(TAG, "onResponse: Error code " + Integer.toString(response.code()));
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                mView.showProgress(false);
                Log.i(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private UserEndpoints getUserEndpoints(String username, String password) {

        if(username.isEmpty()){
            mView.showEmptyUsernameError();
        }

        if(password.isEmpty()){
            mView.showEmptyPasswordError();
        }

        mUserParams.setUsername(username);
        mUserParams.setPassword(password);
        mUser.setParams(mUserParams);

        return mRetrofit.create(UserEndpoints.class);
    }


    @Override
    public void automaticallyLogin() {
        String userId = mSharedPreferences.getString(mConstants.USERID, null);
        if(userId != null){
            mView.showTaskActivity();
        }
    }

    @Override
    public void start() {

    }
}
