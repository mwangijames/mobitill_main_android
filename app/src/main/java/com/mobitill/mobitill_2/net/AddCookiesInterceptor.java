package com.mobitill.mobitill_2.net;

/**
 * Created by DI on 8/9/2016.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.IOException;
import java.util.HashSet;
import java.util.prefs.Preferences;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * This interceptor put all the Cookies in Preferences in the Body.
 * Your implementation on how to get the Preferences MAY VARY.
 * <p>
 * Created by tsuharesu on 4/1/15.
 */
public class AddCookiesInterceptor implements Interceptor {
    public static final String PREF_COOKIES = "PREF_COOKIES";

    Context mContext;
    SharedPreferences mSharedPreferences;

    @Inject
    public AddCookiesInterceptor(Context context, SharedPreferences sharedPreferences) {
        mContext = context;
        mSharedPreferences = sharedPreferences;
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Log.v("OkHttp", "Adding Header: " + "response");
        Request.Builder builder = chain.request().newBuilder();
        HashSet<String> preferences = (HashSet<String>) mSharedPreferences.getStringSet(PREF_COOKIES, new HashSet<String>());
        for (String cookie : preferences) {
            builder.addHeader("Cookie", cookie);
            Log.v("OkHttp", "Adding Header: " + cookie); // This is done so I know which headers are being added; this interceptor is used after the normal logging of OkHttp
        }
        return chain.proceed(builder.build());
    }

}

