package com.mobitill.mobitill_2;

import android.content.Context;
import android.content.SharedPreferences;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.mobitill.mobitill_2.data.RealmModule;
import com.mobitill.mobitill_2.data.models.apps.AppsRepositoryModule;
import com.mobitill.mobitill_2.data.models.apps.models.App;
import com.mobitill.mobitill_2.data.models.apps.AppsModule;
import com.mobitill.mobitill_2.data.models.apps.models.Apps;
import com.mobitill.mobitill_2.data.models.apps.models.Body;
import com.mobitill.mobitill_2.data.models.apps.models.Datum;
import com.mobitill.mobitill_2.data.models.apps.models.Params;
import com.mobitill.mobitill_2.data.models.cashiers.CashiersModule;
import com.mobitill.mobitill_2.data.models.cashiers.CashiersRepository;
import com.mobitill.mobitill_2.data.models.cashiers.CashiersRepositoryModule;
import com.mobitill.mobitill_2.data.models.cashiers.models.Cashier;
import com.mobitill.mobitill_2.data.models.cashiers.models.Cashiers;
import com.mobitill.mobitill_2.data.models.cashiers.models.CashiersFetch;
import com.mobitill.mobitill_2.data.models.cashiers.models.CashiersParams;
import com.mobitill.mobitill_2.data.models.cashiers.models.CashiersQuery;
import com.mobitill.mobitill_2.data.models.cashiers.models.create.CashierCreateParams;
import com.mobitill.mobitill_2.data.models.cashiers.models.create.CashierCreateQuery;
import com.mobitill.mobitill_2.data.models.cashiers.models.create.CashierCreateResponse;
import com.mobitill.mobitill_2.data.models.cashiers.models.delete.CashierDeleteParams;
import com.mobitill.mobitill_2.data.models.cashiers.models.delete.CashierDeleteQuery;
import com.mobitill.mobitill_2.data.models.cashiers.models.delete.CashierDeleteResponse;
import com.mobitill.mobitill_2.data.models.cashiers.models.delete.CashierDeleteResponseData;
import com.mobitill.mobitill_2.data.models.clients.ClientsModule;
import com.mobitill.mobitill_2.data.models.clients.ClientsRepository;
import com.mobitill.mobitill_2.data.models.clients.ClientsRepositoryModule;
import com.mobitill.mobitill_2.data.models.clients.models.Client;
import com.mobitill.mobitill_2.data.models.clients.models.Clients;
import com.mobitill.mobitill_2.data.models.clients.models.ClientsFetch;
import com.mobitill.mobitill_2.data.models.clients.models.ClientsParams;
import com.mobitill.mobitill_2.data.models.clients.models.ClientsQuery;
import com.mobitill.mobitill_2.data.models.fleet.FleetModule;
import com.mobitill.mobitill_2.data.models.fleet.FleetRepository;
import com.mobitill.mobitill_2.data.models.fleet.FleetRepositoryModule;
import com.mobitill.mobitill_2.data.models.fleet.models.Fleet;
import com.mobitill.mobitill_2.data.models.fleet.models.FleetFetch;
import com.mobitill.mobitill_2.data.models.fleet.models.FleetItem;
import com.mobitill.mobitill_2.data.models.fleet.models.FleetParams;
import com.mobitill.mobitill_2.data.models.fleet.models.FleetQuery;
import com.mobitill.mobitill_2.data.models.products.ProductsModule;
import com.mobitill.mobitill_2.data.models.products.ProductsRepository;
import com.mobitill.mobitill_2.data.models.products.ProductsRepositoryModule;
import com.mobitill.mobitill_2.data.models.products.models.Product;
import com.mobitill.mobitill_2.data.models.products.models.Products;
import com.mobitill.mobitill_2.data.models.products.models.ProductsFetch;
import com.mobitill.mobitill_2.data.models.products.models.ProductsParams;
import com.mobitill.mobitill_2.data.models.products.models.ProductsQuery;
import com.mobitill.mobitill_2.data.models.reports.ReportsModule;
import com.mobitill.mobitill_2.data.models.reports.ReportsRepository;
import com.mobitill.mobitill_2.data.models.reports.ReportsRepositoryModule;
import com.mobitill.mobitill_2.data.models.reports.models.Fetch;
import com.mobitill.mobitill_2.data.models.reports.models.Query;
import com.mobitill.mobitill_2.data.models.reports.models.ReportItem;
import com.mobitill.mobitill_2.data.models.reports.models.Reports;
import com.mobitill.mobitill_2.data.models.reports.models.ReportsLocal;
import com.mobitill.mobitill_2.data.models.users.Error;
import com.mobitill.mobitill_2.data.models.users.ErrorMessage;
import com.mobitill.mobitill_2.data.models.users.User;
import com.mobitill.mobitill_2.data.models.users.UserData;
import com.mobitill.mobitill_2.data.models.users.UserModule;
import com.mobitill.mobitill_2.data.models.users.UserParams;
import com.mobitill.mobitill_2.data.models.users.UserResponse;
import com.mobitill.mobitill_2.net.ConnectivityReceiver;
import com.mobitill.mobitill_2.net.NetModule;


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
        ReportsModule.class, ReportsRepositoryModule.class, ProductsModule.class, ProductsRepositoryModule.class,
        CashiersRepositoryModule.class, CashiersModule.class, FleetModule.class, FleetRepositoryModule.class,
        ClientsModule.class, ClientsRepositoryModule.class})
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

    //Realm Module
    Realm realm();


//    JobsModule
    FirebaseJobDispatcher firebaseJobDispatcher();

    //ReportsModule
    ReportItem reportItem();
    Reports reports();
    ReportsLocal reportsLocal();
    Query query();
    com.mobitill.mobitill_2.data.models.reports.models.Params reportsParams();
    Fetch fetch();
    ReportsRepository reportsRepository();


    //ProductsModule
    Product product();
    Products products();
    ProductsFetch productsFetch();
    ProductsParams productsParams();
    ProductsQuery productsQuery();
    ProductsRepository productsRepository();

    // CashiersModule
    Cashiers cashiers();
    Cashier cashier();
    CashiersFetch cashiersFetch();
    CashiersParams cashiersParams();
    CashiersQuery cashiersQuery();
    CashiersRepository cashiersRepository();
    CashierCreateQuery cashierCreateQuery();
    CashierCreateResponse cashierCreateResponse();
    CashierCreateParams cashierCreateParams();
    CashierDeleteResponse cashierDeleteResponse();
    CashierDeleteResponseData cashierDeleteResponseData();
    CashierDeleteQuery cashierDeleteQuery();
    CashierDeleteParams cashierDelteParams();

    // FleetModule
    Fleet fleet();
    FleetItem fleetItem();
    FleetFetch fleetFetch();
    FleetParams fleetParams();
    FleetQuery fleetQuery();
    FleetRepository fleetRepository();


    // ClientsModule
    Client client();
    Clients clients();
    ClientsFetch clientsFetch();
    ClientsParams clientsParams();
    ClientsQuery clientsQuery();
    ClientsRepository clientsRepository();
}
