package com.mobitill.mobitill_2;

import android.content.Context;
import android.content.SharedPreferences;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.mobitill.mobitill_2.data.RealmModule;
import com.mobitill.mobitill_2.data.models.apps.AppsModule;
import com.mobitill.mobitill_2.data.models.apps.AppsRepository;
import com.mobitill.mobitill_2.data.models.apps.AppsRepositoryModule;
import com.mobitill.mobitill_2.data.models.apps.models.App;
import com.mobitill.mobitill_2.data.models.apps.models.Apps;
import com.mobitill.mobitill_2.data.models.apps.models.Body;
import com.mobitill.mobitill_2.data.models.apps.models.Datum;
import com.mobitill.mobitill_2.data.models.apps.models.Params;
import com.mobitill.mobitill_2.data.models.generic.Actions;
import com.mobitill.mobitill_2.data.models.generic.GenericModule;
import com.mobitill.mobitill_2.data.models.generic.GenericRepository;
import com.mobitill.mobitill_2.data.models.generic.GenericRepositoryModule;
import com.mobitill.mobitill_2.data.models.generic.Payload;
import com.mobitill.mobitill_2.data.models.users.Error;
import com.mobitill.mobitill_2.data.models.users.ErrorMessage;
import com.mobitill.mobitill_2.data.models.users.User;
import com.mobitill.mobitill_2.data.models.users.UserData;
import com.mobitill.mobitill_2.data.models.users.UserModule;
import com.mobitill.mobitill_2.data.models.users.UserParams;
import com.mobitill.mobitill_2.data.models.users.UserResponse;
import com.mobitill.mobitill_2.net.ConnectivityReceiver;
import com.mobitill.mobitill_2.net.NetModule;
import com.mobitill.mobitill_2.utils.SettingsHelper;
import com.mobitill.mobitill_2.utils.UIHelper;
import com.mobitill.mobitill_2.utils.UtilsModule;

import javax.inject.Singleton;

import dagger.Component;
import io.realm.Realm;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * Created by DI on 8/9/2016.
 */
@Singleton
@Component(modules={ApplicationModule.class, NetModule.class, UserModule.class, ConstantsModule.class,
        AppsModule.class, RealmModule.class, AppsRepositoryModule.class, JobsModule.class,
        UtilsModule.class, GenericRepositoryModule.class,
        GenericModule.class})
public interface BaseComponent {
    // Application Module
    Context context();

    // NetModule
    Retrofit retrofit();
    UserResponse userResponse();
    OkHttpClient.Builder okHttpClient();
    SharedPreferences sharedPreferences();
    HttpLoggingInterceptor httpLoggingInterceptor();
    ConnectivityReceiver connectivityReceiver();

    //UserModule
    UserParams userParams();
    User user();
    UserData userData();
    Error error();
    ErrorMessage errorMessage();

    //ConstantsModule
    Constants constants();

    //AppsModule
    App app();
    Datum datum();
    Apps apps();
    Params params();
    Body body();
    AppsRepository appsRepository();
    //Realm Module
    Realm realm();


//    JobsModule
    FirebaseJobDispatcher firebaseJobDispatcher();



    // Utils
    SettingsHelper settingsHelper();
    UIHelper uiHelper();

    //GenericModule
    Payload payLoad();
    GenericRepository genericRepository();
    Actions actions();

}
