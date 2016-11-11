package com.mobitill.mobitill_2.reports;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Spinner;
import android.widget.Toast;

import com.mobitill.mobitill_2.apps.AppsContract;
import com.mobitill.mobitill_2.data.models.apps.AppsRepository;
import com.mobitill.mobitill_2.data.models.cashiers.CashiersDataSource;
import com.mobitill.mobitill_2.data.models.cashiers.CashiersRepository;
import com.mobitill.mobitill_2.data.models.cashiers.models.Cashier;
import com.mobitill.mobitill_2.data.models.clients.ClientsDataSource;
import com.mobitill.mobitill_2.data.models.clients.ClientsRepository;
import com.mobitill.mobitill_2.data.models.clients.models.Client;
import com.mobitill.mobitill_2.data.models.fleet.FleetDataSource;
import com.mobitill.mobitill_2.data.models.fleet.FleetRepository;
import com.mobitill.mobitill_2.data.models.fleet.models.FleetItem;
import com.mobitill.mobitill_2.data.models.generic.Actions;
import com.mobitill.mobitill_2.data.models.generic.GenericDataSource;
import com.mobitill.mobitill_2.data.models.generic.GenericRepository;
import com.mobitill.mobitill_2.data.models.generic.Payload;
import com.mobitill.mobitill_2.data.models.products.ProductsDataSource;
import com.mobitill.mobitill_2.data.models.products.ProductsRepository;
import com.mobitill.mobitill_2.data.models.products.models.Product;
import com.mobitill.mobitill_2.data.models.products.remote.ProductsRemoteDataSource;
import com.mobitill.mobitill_2.data.models.reports.ReportsDataSource;
import com.mobitill.mobitill_2.data.models.reports.ReportsRepository;
import com.mobitill.mobitill_2.data.models.reports.models.ReportItem;
import com.mobitill.mobitill_2.data.models.reports.models.ReportsLocal;
import com.mobitill.mobitill_2.menu.MenuAppSettings;
import com.mobitill.mobitill_2.utils.SettingsHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.logging.MemoryHandler;

import javax.inject.Inject;

/**
 * Created by james on 8/31/2016.
 */
public final class ReportsPresenter implements ReportsContract.Presenter {

    private static final String TAG = ReportsPresenter.class.getSimpleName();

    private  ReportsRepository mReportsRepository;
    private  ProductsRepository mProductsRepository;
    private  CashiersRepository mCashiersRepository;
    private  ReportsContract.View mView;
    private Payload mPayload;
    private final GenericRepository mGenericRepository;
    @Nullable String mAppId;
    private MenuAppSettings mMenuAppSettings;
    private SettingsHelper mSettingsHelper;
    private final Actions mActions;
    private final Context mContext;

    @Inject
    public ReportsPresenter(ReportsContract.View view, ReportsRepository reportsRepository,
                            ProductsRepository productsRepository, CashiersRepository cashiersRepository,
                            @Nullable String appId, MenuAppSettings menuAppSettings,
                            SettingsHelper settingsHelper,
                            Payload payload,
                            GenericRepository genericRepository,
                            Actions actions,
                            Context context){

        mView = view;
        mReportsRepository = reportsRepository;
        mProductsRepository = productsRepository;
        mCashiersRepository = cashiersRepository;
        mAppId = appId;
        mMenuAppSettings = menuAppSettings;
        mSettingsHelper = settingsHelper;
        mPayload = payload;
        mGenericRepository = genericRepository;
        mActions = actions;
        mContext = context;
    }

    @Override
    public void sendQuery() {

    }

    @Override
    public void getMenuList() {
        if(mMenuAppSettings != null){
            if(mMenuAppSettings.getSettings() != null){
                Log.i(TAG, "start: " + mMenuAppSettings.getSettings());
                ArrayList<String> models = mSettingsHelper.getModels(mMenuAppSettings.getSettings());
                for (String model: models) {
                    Log.i(TAG, "getMenuList: " + model);
                }
                mView.showMenuItems(models);
            } else {
                mSettingsHelper.getModels("{}");
            }

        } else {
            Log.i(TAG, "start: mMenuAppSettings is null");
        }
    }

    @Override
    public void fetchReports(String appId, List<Long> dates) {
        String cashier = "";
        String productId = "";
        mView.setLoadingIndicator(true);
        mReportsRepository.getReports(dates, cashier, productId, appId, new ReportsDataSource.LoadReportsCallback() {
            @Override
            public void onLocalReportsLoaded(List<ReportsLocal> reportsLocalList) {

            }

            @Override
            public void onRemoteDataLoaded(List<ReportItem> reportItemList) {
                mView.setLoadingIndicator(false);
                getQuantity(reportItemList.size());
                getTotal(reportItemList);
            }

            @Override
            public void onDataNotAvailable() {
                mView.setLoadingIndicator(false);
                mView.showNoReports();
            }
        });
    }

    @Override
    public void fetchReports(String appId, List<Long> dates, String product) {
        String cashier = "";
        mView.setLoadingIndicator(true);
        mReportsRepository.getReports(dates, cashier, product, appId, new ReportsDataSource.LoadReportsCallback() {
            @Override
            public void onLocalReportsLoaded(List<ReportsLocal> reportsLocalList) {

            }

            @Override
            public void onRemoteDataLoaded(List<ReportItem> reportItemList) {
                mView.setLoadingIndicator(false);
                getQuantity(reportItemList.size());
                getTotal(reportItemList);
            }

            @Override
            public void onDataNotAvailable() {
                mView.setLoadingIndicator(false);
                mView.showNoReports();
            }
        });
    }

    @Override
    public void fetchReports(String appId, List<Long> dates, String product, String cashier) {
        mView.setLoadingIndicator(true);
        mReportsRepository.getReports(dates, cashier, product, appId, new ReportsDataSource.LoadReportsCallback() {
            @Override
            public void onLocalReportsLoaded(List<ReportsLocal> reportsLocalList) {

            }

            @Override
            public void onRemoteDataLoaded(List<ReportItem> reportItemList) {
                mView.setLoadingIndicator(false);
                getQuantity(reportItemList.size());
                getTotal(reportItemList);
            }

            @Override
            public void onDataNotAvailable() {
                mView.setLoadingIndicator(false);
                mView.showNoReports();
            }
        });
    }

    @Override
    public void fetchReport(List<Long> range, HashMap<String, String> items) {
        if(mMenuAppSettings!=null && mMenuAppSettings.getSettings() != null){
            if(mPayload!=null){
                if(mSettingsHelper != null){
                    mPayload.setAction(mActions.QUERY);
                    mPayload.setModel("transactions");
                    mPayload.setPayload(mSettingsHelper.getReportsPayload(mAppId, range, items));
                }
                if(mPayload.isEmpty()){
                    Log.i(TAG, "fetchReport: Some payloads fields are empty" );
                } else {
                    if(mGenericRepository!=null){
                        mGenericRepository.postData(mPayload, new GenericDataSource.LoadDataCallBack() {
                            @Override
                            public void onDataLoaded(String data) {
                                Log.i(TAG, "onDataLoaded: " + data);
                            }

                            @Override
                            public void onDataNotLoaded() {
                                Log.i(TAG, "onDataNotLoaded: ");
                            }
                        });
                    }
                }
            }
        }
    }

    @Override
    public void fetchProducts(String appId) {
        mProductsRepository.getProducts(appId, new ProductsDataSource.LoadProductsCallBack() {
            @Override
            public void onProductsLoaded(List<Product> productList) {
                mView.showProducts(productList);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void fetchCashiers(String appId) {
        mCashiersRepository.getCashiers(appId, new CashiersDataSource.LoadCashiersCallback() {
            @Override
            public void onCashiersLoaded(List<Cashier> cashierList) {
                    mView.showCashiers(cashierList);
            }

            @Override
            public void onDataNotAvailable() {
                Log.i(TAG, "onDataNotAvailable: " + "No Cashier Retrieved");
            }
        });
    }

    @Override
    public Product[] getProductsArray(List<Product> products) {
        Product product = new Product();
        product.setName("Products");
        product.setId("");
        products.add(0, product);

        return products.toArray(new Product[0]);
    }

    @Override
    public Cashier[] getCashiersArray(List<Cashier> cashiers) {
        Cashier cashier = new Cashier();
        cashier.setName("Cashiers");
        cashier.setId("");
        cashiers.add(0, cashier);

        return cashiers.toArray(new Cashier[0]);
    }

    @NonNull
    @Override
    public Calendar getMidnight() {
        // today
        Calendar date = new GregorianCalendar();
        // reset hour, minutes, seconds and millis to get midnight
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        return date;
    }

    @Override
    public void getQuantity(int quantity) {
        mView.showQuantity(quantity);
    }

    @Override
    public void getTotal(List<ReportItem> reportItemList) {
        int total = 0;
        for (ReportItem reportItem: reportItemList) {
            total += reportItem.getTotal();
        }
        mView.showTotal(total);
    }

    @Override
    public String getFormattedDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String dateString = Integer.toString(day) + "-" + Integer.toString(month) + "-" + Integer.toString(year);
        return dateString;
    }



    @Inject
    void setUpListeners(){
        mView.setPresenter(this);
    }

    @Override
    public void start() {

        setUpFilter();

        List<Long> dates = new ArrayList<Long>();
        dates.add(getMidnight().getTime().getTime());
        dates.add(new Date().getTime());
        fetchReport(dates, new HashMap<String, String>());


    }

    private void setUpFilter() {
        List<String> items = mSettingsHelper.getReportFilterItems(mMenuAppSettings.getSettings());

        final HashMap<String, List<HashMap<String, String>>> filterItems = new HashMap<>();

        for(final String item: items){
            if(mPayload != null && mSettingsHelper != null){
                mPayload.setModel(item);
                mPayload.setPayload(mSettingsHelper.getPayload(mActions.FETCH, mAppId));
                mPayload.setAction(mActions.FETCH);
                if(!mPayload.isEmpty()){
                    mGenericRepository.postData(mPayload, new GenericDataSource.LoadDataCallBack() {
                        @Override
                        public void onDataLoaded(String data) {
                            filterItems.put(item, mSettingsHelper.getList(data));
                        }

                        @Override
                        public void onDataNotLoaded() {
                            Log.i(TAG, "onDataNotLoaded: ");
                        }
                    });
                }
            }
        }

        mView.setUpFilterView(filterItems);
    }

}
