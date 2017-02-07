package com.mobitill.mobitill_2.reports;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.mobitill.mobitill_2.data.models.generic.Actions;
import com.mobitill.mobitill_2.data.models.generic.GenericDataSource;
import com.mobitill.mobitill_2.data.models.generic.GenericRepository;
import com.mobitill.mobitill_2.data.models.generic.Payload;
import com.mobitill.mobitill_2.menu.MenuAppSettings;
import com.mobitill.mobitill_2.utils.SettingsHelper;

import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

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

    //private List<HashMap<String, String>>
    private HashMap<String, List<HashMap<String, String>>> mFilterItems;

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
        mFilterItems = new HashMap<String, List<HashMap<String, String>>>();
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

                                if(!report.isEmpty()){
                                    mView.removeChartLayoutViews();
                                    createCharts(report);
                                    drawLineChart(report);
                                }

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

    private void drawLineChart(List<HashMap<String, String>> report) {
        HashMap<String, Float> totals = getDayAndTotal(report);
        Log.i(TAG, "drawLineChart: " + totals.toString());
    }

    @NonNull
    private HashMap<String, Float> getDayAndTotal(List<HashMap<String, String>> report) {
        String[] daysOfWeek =   {"monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday"};
        HashMap<String, Float> totals = new HashMap<>();
        // fill map with each day of week
        for(String day: daysOfWeek){
            totals.put(day, 0f);
        }

        for(HashMap<String, String> item: report){
            if(item.containsKey("timestamp")){
                String dateString = item.get("timestamp");
                SimpleDateFormat formatter  = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
                formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
                try {

                    Date date = formatter.parse(dateString);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
                    String day = simpleDateFormat.format(date).toLowerCase();

                    if(totals.containsKey(day)){
                        float total = 0f;
                        total = totals.get(day);
                        if(item.containsKey("total")){
                            //Log.i(TAG, "drawLineChart: " + totals.get(day));
                            total = totals.get(day.toLowerCase()) + Float.parseFloat(item.get("total"));
                            totals.put(day, total);
                        }

                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        }
        return totals;
    }

    /**
     * Creates charts
     * @param  report for the report that is going to be used to generate the report
     * */
    private void createCharts(List<HashMap<String, String>> report) {

        //List<String> items = mSettingsHelper.getReportFilterItems(mMenuAppSettings.getSettings());

        // loop through through each filter category e.g cashiers, products
        // for each category create a chart
        for(HashMap.Entry<String, List<HashMap<String, String>>> item: mFilterItems.entrySet()){


            List<String> xData  = new ArrayList<>();
            List<Float> yData = new ArrayList<>();

            // loop through each item inside the category
            List<HashMap<String, String>> itemList = item.getValue();
            for(HashMap<String, String> listItem : itemList){
                // create the x data
                xData.add(listItem.get("name"));
                yData.add(calculateTotal(listItem.get("id"), report));


            }

            addData(StringUtils.capitalize(item.getKey()),xData, yData);
            // mView.createChart(StringUtils.capitalize(item.getKey()), /*pie data comes here*/);
        }

    }


    private void addData(String chartTitle, List<String> xData, List<Float> yData) {
        ArrayList<BarEntry> yVals1 = new ArrayList<>();

        for(int i = 0; i < yData.size(); i++){
            yVals1.add(new BarEntry((float)i, yData.get(i)));
        }

        ArrayList<String> xVals = new ArrayList<>();
        for(int i = 0; i < xData.size(); i++){
            xVals.add(xData.get(i));
        }

        // create a pie data set
        BarDataSet dataSet = new BarDataSet(yVals1, chartTitle);

        BarData data = new BarData(dataSet);

        //data.setValueFormatter(new DefaultValueFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.LTGRAY);
        data.setBarWidth(0.9f);

        mView.createChart(chartTitle, dataSet, data, xVals);

    }

    /**
     * Returns total for each entry in PieChart
     *
     * @param id the id of the item
     *
     * @param report List of HashMap of each transaction item
     * @return  total for each id
     * */
    private float calculateTotal(String id, List<HashMap<String, String>> report) {

        float total = 0;

        for(HashMap<String, String> item: report){
            if(item.containsValue(id)){
                for(HashMap.Entry<String, String> entry: item.entrySet()){
                    if(entry.getKey().equalsIgnoreCase("total")){
                        float value = Float.parseFloat(entry.getValue());
                        total = total + value;
                    }
                }
            }
        }
        return total;
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

        mFilterItems = new HashMap<>();

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
                            mFilterItems.put(item, mSettingsHelper.getList(data));
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

        mView.setUpFilterView(mFilterItems);
    }

    //==========================================================================================
    //CHARTS
    //=========================================================================================

    //==========================================================================================
    //END CHARTS
    //=========================================================================================

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
