package com.mobitill.mobitill_2.net;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by DI on 8/5/2016.
 */
@Module
public class NetModule {

    String mBaseUrl;

    // Constructor needs one parameter to instantiate.
    public NetModule(String baseUrl) {
        this.mBaseUrl = baseUrl;
    }

    // Dagger will only look for methods annotated with @Provides

    @Provides
    @Singleton
    // Application reference must come from AppsModule.class
    SharedPreferences providesSharedPreferences(Context application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Provides
    @Singleton
    ConnectivityReceiver provideConnectivityReceiver(){
        return new ConnectivityReceiver();
    }

    @Provides
    @Singleton
    Cache provideOkHttpCache(Context application) {
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(application.getCacheDir(), cacheSize);
        return cache;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    AddCookiesInterceptor provideAddCookiesInterceptor(Context context, SharedPreferences sharedPreferences){
        AddCookiesInterceptor addCookiesInterceptor = new AddCookiesInterceptor(context, sharedPreferences);
        return addCookiesInterceptor;
    }

    @Provides
    @Singleton
    ReceivedCookiesInterceptor receivedCookiesInterceptor(Context context, SharedPreferences sharedPreferences){
        ReceivedCookiesInterceptor receivedCookiesInterceptor = new ReceivedCookiesInterceptor(context, sharedPreferences);
        return receivedCookiesInterceptor;
    }

    @Provides
    @Singleton
    LogginInterceptor provideLogginInterceptor(){
        return new LogginInterceptor();
    }

    @Provides
    @Singleton
    HttpLoggingInterceptor provideHttpLoggingInterceptor(){

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.i("HttpLoggingInterceptor", "log: " + message);
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return httpLoggingInterceptor;
    }

    @Provides
    @Singleton
    OkHttpClient.Builder provideOkHttpClient(Cache cache, ReceivedCookiesInterceptor receivedCookiesInterceptor,
                                     AddCookiesInterceptor addCookiesInterceptor,
                                     LogginInterceptor logginInterceptor, HttpLoggingInterceptor httpLoggingInterceptor) {
        OkHttpClient.Builder client = new OkHttpClient().newBuilder();

        client.addInterceptor(addCookiesInterceptor)
                .addInterceptor(receivedCookiesInterceptor)
                .addInterceptor(logginInterceptor)
                .addInterceptor(httpLoggingInterceptor)
                .cache(cache)
                .build();
        return client;
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient.Builder okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl(mBaseUrl)
                .client(okHttpClient.build())
                .build();
        return retrofit;
    }

}
