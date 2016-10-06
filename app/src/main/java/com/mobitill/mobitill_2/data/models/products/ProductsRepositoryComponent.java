package com.mobitill.mobitill_2.data.models.products;

import android.content.Context;
import android.content.SharedPreferences;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.mobitill.mobitill_2.BaseComponent;
import com.mobitill.mobitill_2.Constants;
import com.mobitill.mobitill_2.data.Data;
import com.mobitill.mobitill_2.data.models.products.models.Product;
import com.mobitill.mobitill_2.data.models.products.models.Products;
import com.mobitill.mobitill_2.data.models.products.models.ProductsFetch;
import com.mobitill.mobitill_2.data.models.products.models.ProductsParams;
import com.mobitill.mobitill_2.data.models.products.models.ProductsQuery;
import com.mobitill.mobitill_2.data.models.reports.ReportsRepository;
import com.mobitill.mobitill_2.data.models.reports.models.Fetch;
import com.mobitill.mobitill_2.data.models.reports.models.Query;
import com.mobitill.mobitill_2.data.models.reports.models.ReportItem;
import com.mobitill.mobitill_2.data.models.reports.models.Reports;
import com.mobitill.mobitill_2.data.models.reports.models.ReportsLocal;
import com.mobitill.mobitill_2.data.models.users.Error;
import com.mobitill.mobitill_2.data.models.users.ErrorMessage;
import com.mobitill.mobitill_2.data.models.users.User;
import com.mobitill.mobitill_2.data.models.users.UserData;
import com.mobitill.mobitill_2.data.models.users.UserModule_ProvideUserDataDataFactory;
import com.mobitill.mobitill_2.data.models.users.UserParams;
import com.mobitill.mobitill_2.data.models.users.UserResponse;
import com.mobitill.mobitill_2.utils.AppScoped;
import com.mobitill.mobitill_2.utils.FragmentScoped;

import dagger.Component;
import io.realm.Realm;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * Created by james on 9/13/2016.
 */
@Data
@Component(dependencies = {BaseComponent.class}, modules = {ProductsRepositoryModule.class})
public interface ProductsRepositoryComponent  {

    ProductsRepository getProductsRepository();
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

    //ProductsModule
    Product product();
    Products products();
    ProductsFetch productsFetch();
    ProductsParams productsParams();
    ProductsQuery productsQuery();


    //Realm Module
    Realm realm();

    // Application Module
    Context context();

    // JobsModule
    FirebaseJobDispatcher firebaseJobDispatcher();

}
