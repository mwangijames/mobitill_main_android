package com.mobitill.mobitill_2.reports;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.mobitill.mobitill_2.data.models.generic.Actions;
import com.mobitill.mobitill_2.data.models.generic.GenericDataSource;
import com.mobitill.mobitill_2.data.models.generic.GenericRepository;
import com.mobitill.mobitill_2.data.models.generic.Payload;

import com.mobitill.mobitill_2.menu.MenuAppSettings;
import com.mobitill.mobitill_2.utils.SettingsHelper;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by james on 8/31/2016.
 */
public final class ReportsPresenter implements ReportsContract.Presenter {

    private static final String TAG = ReportsPresenter.class.getSimpleName();


    private  ReportsContract.View mView;
    private Payload mPayload;
    private final GenericRepository mGenericRepository;
    @Nullable String mAppId;
    private MenuAppSettings mMenuAppSettings;
    private SettingsHelper mSettingsHelper;
    private final Actions mActions;
    private final Context mContext;

    @Inject
    public ReportsPresenter(ReportsContract.View view,
                            @Nullable String appId, MenuAppSettings menuAppSettings,
                            SettingsHelper settingsHelper,
                            Payload payload,
                            GenericRepository genericRepository,
                            Actions actions,
                            Context context){

        mView = view;
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
                //Log.i(TAG, "start: " + mMenuAppSettings.getSettings());
                ArrayList<String> models = mSettingsHelper.getModels(mMenuAppSettings.getSettings());
                for (String model: models) {
                   // Log.i(TAG, "getMenuList: " + model);
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
    public void fetchReport(List<Long> range, HashMap<String, String> items) {
        if(mMenuAppSettings!=null && mMenuAppSettings.getSettings() != null){
            if(mPayload!=null){
                if(mSettingsHelper != null){
                    mPayload.setAction(mActions.QUERY);
                    mPayload.setModel("transactions");
                    mPayload.setPayload(mSettingsHelper.getReportsPayload(mAppId, range, items));
                    mPayload.setDemo(false);
                    mPayload.setAppid(mAppId);
                }
                if(mPayload.isEmpty()){
                    Log.i(TAG, "fetchReport: Some payloads fields are empty" );
                } else {
                    if(mGenericRepository!=null){
                        mView.setLoadingIndicator(true);
                        mGenericRepository.postData(mPayload, new GenericDataSource.LoadDataCallBack() {
                            @Override
                            public void onDataLoaded(String data) {
                                mView.setLoadingIndicator(false);
                               // Log.i(TAG, "onDataLoaded: " + mSettingsHelper.getList(data));
                                List<HashMap<String, String>> report = mSettingsHelper.getList(data);
                                mView.showQuantity(report.size());
                                getTotal(report);
                            }

                            @Override
                            public void onDataNotLoaded() {
                                mView.setLoadingIndicator(false);
                                Log.i(TAG, "onDataNotLoaded: fetchReport");
                            }
                        });
                    }
                }
            }
        }
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
    public void getTotal(List<HashMap<String, String>> report) {
        new CalculateTotal().execute(report);
    }

    @Override
    public String getFormattedDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
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
                mPayload.setPayload(mSettingsHelper.getFetchPayload(mActions.FETCH, mAppId));
                mPayload.setAction(mActions.FETCH);
                mPayload.setDemo(false);
                mPayload.setAppid(mAppId);
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

    private class CalculateTotal extends AsyncTask<List<HashMap<String, String>>, Void, Double>{

        @Override
        protected Double doInBackground(List<HashMap<String, String>>... params) {
            List<HashMap<String, String>> reports = params[0];
            Double total = 0d;
            for(HashMap<String, String> item: reports){
                for(HashMap.Entry<String, String> entry: item.entrySet()){
                    if(entry.getKey().equalsIgnoreCase("total")){
                        double value = Double.parseDouble(entry.getValue());
                        total = total + value;
                    }
                }
            }
            return total;
        }

        @Override
        protected void onPostExecute(Double aDouble) {
            if(aDouble!=null){
                NumberFormat formatter = NumberFormat.getCurrencyInstance();
                DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) formatter).getDecimalFormatSymbols();
                decimalFormatSymbols.setCurrencySymbol("");
                ((DecimalFormat) formatter).setDecimalFormatSymbols(decimalFormatSymbols);
                String totalString = formatter.format(aDouble);
                mView.showTotal(totalString);
            }
        }


    }

}
