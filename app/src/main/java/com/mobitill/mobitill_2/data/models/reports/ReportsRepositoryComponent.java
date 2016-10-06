package com.mobitill.mobitill_2.data.models.reports;

import android.content.Context;
import android.content.SharedPreferences;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.mobitill.mobitill_2.BaseComponent;
import com.mobitill.mobitill_2.Constants;
import com.mobitill.mobitill_2.data.Data;
import com.mobitill.mobitill_2.data.models.apps.AppsRepository;
import com.mobitill.mobitill_2.data.models.apps.AppsRepositoryModule;
import com.mobitill.mobitill_2.data.models.apps.models.App;
import com.mobitill.mobitill_2.data.models.apps.models.Apps;
import com.mobitill.mobitill_2.data.models.apps.models.Body;
import com.mobitill.mobitill_2.data.models.apps.models.Datum;
import com.mobitill.mobitill_2.data.models.apps.models.Params;
import com.mobitill.mobitill_2.data.models.reports.models.Fetch;
import com.mobitill.mobitill_2.data.models.reports.models.Query;
import com.mobitill.mobitill_2.data.models.reports.models.ReportItem;
import com.mobitill.mobitill_2.data.models.reports.models.Reports;
import com.mobitill.mobitill_2.data.models.reports.models.ReportsLocal;
import com.mobitill.mobitill_2.data.models.users.Error;
import com.mobitill.mobitill_2.data.models.users.ErrorMessage;
import com.mobitill.mobitill_2.data.models.users.User;
import com.mobitill.mobitill_2.data.models.users.UserData;
import com.mobitill.mobitill_2.data.models.users.UserParams;
import com.mobitill.mobitill_2.data.models.users.UserResponse;
import com.mobitill.mobitill_2.utils.AppScoped;

import dagger.Component;
import io.realm.Realm;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * Created by james on 9/2/2016.
 */
@Data
@Component(dependencies ={BaseComponent.class}, modules = {ReportsRepositoryModule.class})
public interface ReportsRepositoryComponent{
    ReportsRepository getReportsRepository();
    SharedPreferences sharedPreferences();
    // NetModule
    Retrofit retrofit();
    UserResponse userResponse();
    OkHttpClient.Builder okHttpClient();
    HttpLoggingInterceptor httpLoggingInterceptor();

    //UserModule
    UserParams userParams();
    User user();
    UserData userData();
    Error error();
    ErrorMessage errorMessage();

    //ConstantsModule
    Constants constants();

    //ReportsModule
    ReportItem reportItem();
    Reports reports();
    ReportsLocal reportsLocal();
    Query query();
    com.mobitill.mobitill_2.data.models.reports.models.Params params();
    Fetch fetch();


    //Realm Module
    Realm realm();

    // Application Module
    Context context();

    // JobsModule
    FirebaseJobDispatcher firebaseJobDispatcher();
}
