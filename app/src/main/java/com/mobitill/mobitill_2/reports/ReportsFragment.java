package com.mobitill.mobitill_2.reports;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mobitill.mobitill_2.MobitillApplication;
import com.mobitill.mobitill_2.R;
import com.mobitill.mobitill_2.net.ConnectivityReceiver;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportsFragment extends Fragment implements ReportsContract.View, ConnectivityReceiver.ConnectivityReceiverListener, FilterDialogFragment.FilterDialogListener{

    private static final String TAG = ReportsFragment.class.getSimpleName();
    private static final String DATES_LIST = "dates_list";
    private static final String PRODUCT_ID = "product_id";
    private static final String CASHIER_ID = "cashier_id";
    private static final String APP_ID = "app_id";

    private static final String ARG_APPID = "appId";
    private static final String DIALOG_DATE = " DialogDate";
    private static final int REQUEST_FROM_DATE = 0;
    private static final int REQUEST_TO_DATE = 1;

    private static final int REQUEST_FILTER_DATA=2;

    private ReportsContract.Presenter mPresenter;
    private String mAppId = null;
    private List<Long> mDates = new ArrayList<>();

    private int mLastSpinnerPosition;
    private int mLastCashierSpinnerPosition;
    private String mProductId;
    private String mCashierId;
    private boolean isHideFilter = true;


    @BindView(R.id.quantity) TextView mQuantityTextView;
    @BindView(R.id.total) TextView mTotalTextView;
    @BindView(R.id.report_layout) LinearLayout mReportLayout;
    @BindView(R.id.no_network) TextView mNoNetworkTextView;
    @BindView(R.id.progress_bar) ProgressBar mProgressBar;
    @BindView(R.id.from) TextView mFromTextView;
    @BindView(R.id.to) TextView mToTextView;
    @BindView(R.id.filter_items) LinearLayout mFilterItemsLinearLayout;

    // create new spinnerList to pass to FilterDialogFragment
    List<Spinner> mFilterSpinners = new ArrayList<>();

    private List<String> mModels;
    HashMap<String, List<HashMap<String, String>>> mFilterModels;
    private DrawerAdapter mDrawerAdapter;

    private RecyclerView mDrawerRecyclerView;
    boolean mIsLargeLayout;
    MenuItem mFilterItem;


    private Unbinder unbinder;

    public ReportsFragment() {
        // Required empty public constructor
    }

    public static ReportsFragment newInstance(String appId) {
        Bundle args = new Bundle();
        args.putString(ARG_APPID, appId);
        ReportsFragment fragment = new ReportsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mAppId = getArguments().getString(ARG_APPID);

        mIsLargeLayout = getResources().getBoolean(R.bool.large_layout);

        if(savedInstanceState == null){
            mProductId = "";
            mCashierId = "";
        } else {
            mProductId = savedInstanceState.getString(PRODUCT_ID);
            mCashierId = savedInstanceState.getString(CASHIER_ID);
            mAppId = savedInstanceState.getString(APP_ID);
        }

        mLastSpinnerPosition = 0;
        mLastCashierSpinnerPosition = 0;

        Calendar date = new GregorianCalendar();
        // reset hour, minutes, seconds and millis to get midnight
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);

        mDates.add( date.getTime().getTime());
        mDates.add(new Date().getTime());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reports, container, false);
        unbinder = ButterKnife.bind(this, view);
        mFromTextView.setText(mPresenter.getFormattedDate(new Date()));
        mToTextView.setText(mPresenter.getFormattedDate(new Date()));
        mPresenter.start();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDrawerRecyclerView = (RecyclerView) getActivity().findViewById(R.id.drawer_recycler_view);
        mDrawerRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(PRODUCT_ID, mProductId);
        outState.putString(CASHIER_ID, mCashierId);
        outState.putString(APP_ID, mAppId);
        super.onSaveInstanceState(outState);

    }


    // When binding a fragment in onCreateView, set the views to null in onDestroyView.
    // ButterKnife returns an Unbinder on the initial binding that has an unbind method to do this automatically.
    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }




    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.reports_fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter:
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FilterDialogFragment filterDialogFragment = FilterDialogFragment.newInstance(mFilterModels);
                filterDialogFragment.setTargetFragment(ReportsFragment.this, REQUEST_FILTER_DATA);

                    // the device is smaller, so show the fragment fullscreen
                    FragmentTransaction  transaction = fm.beginTransaction();
                    // for a little polish specify the transition animation
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    //To make it full screen, use the 'content' root view as the container
                    // for the fragment, which is always the root for the activity
                    transaction.add(android.R.id.content, filterDialogFragment)
                            .addToBackStack(null).commit();


                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void setLoadingIndicator(boolean active) {
        if(mProgressBar!=null){
            mProgressBar.setVisibility(active ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void showMenuItems(List<String> models) {
        if(isAdded()){
            if(mDrawerAdapter == null){
                mDrawerAdapter = new DrawerAdapter(models);
                mModels = models;
                mDrawerRecyclerView.setAdapter(mDrawerAdapter);
            } else {
                mDrawerAdapter.setModels(models);
                mModels = models;
                mDrawerAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void setUpFilterView(HashMap<String, List<HashMap<String, String>>> filterItems) {
        mFilterModels = filterItems;
    }


    @Override
    public void showNoReports() {
        Toast.makeText(getActivity(), "No Data Retrieved", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNoNetwork(boolean show) {
        mNoNetworkTextView.setVisibility(show ? View.VISIBLE : View.GONE);
        mReportLayout.setVisibility(!show ? View.VISIBLE : View.INVISIBLE);
    }


    @Override
    public void hideTitle() {

    }

    @Override
    public void showTitle() {

    }

    @Override
    public void showReports() {
       // mPresenter.fetchReports(mAppId, mDates);
    }


    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void showTotal(String total) {
        if(mTotalTextView!=null){
            mTotalTextView.setText(total);
        }
    }

    @Override
    public void showQuantity(int quantity) {
        if(mQuantityTextView!=null){
            mQuantityTextView.setText(Integer.toString(quantity));
        }
    }

    @Override
    public void setPresenter(@NonNull ReportsContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobitillApplication.getInstance().setConnectivityListener(this);
        mPresenter.start();
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showReportLayoutOnConnection(isConnected);
    }

    private void showReportLayoutOnConnection(boolean isConnected){
        if(isConnected){
            showNoNetwork(false);
        } else {
            showNoNetwork(true);
        }
    }

    @Override
    public void onFinishedFiltering(List<Long> range, HashMap<String, String> items,
                                    HashMap<String, String> displayItems) {
         mPresenter.fetchReport(range, items);

        mFromTextView.setText(mPresenter.getFormattedDate(new Date(range.get(0))));
        mToTextView.setText(mPresenter.getFormattedDate(new Date(range.get(1))));

        mFilterItemsLinearLayout.removeAllViews();
        for(HashMap.Entry<String, String> entry: displayItems.entrySet()){
            LinearLayout linearLayout = new LinearLayout(getActivity());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            linearLayout.setLayoutParams(layoutParams);
            linearLayout.setGravity(Gravity.CENTER);
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            ViewGroup.LayoutParams textViewLayoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            TextView label = new TextView(getActivity());
            label.setLayoutParams(textViewLayoutParams);
            label.setTextColor(getResources().getColor(R.color.colorTextLight));
            label.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.report_small_text));
            label.setText(entry.getKey());
            linearLayout.addView(label);

            TextView name = new TextView(getActivity());
            name.setLayoutParams(textViewLayoutParams);
            name.setTextColor(getResources().getColor(R.color.colorTextLight));
            name.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.report_large_text));
            name.setTypeface(null, Typeface.BOLD);
            name.setText(entry.getValue());
            linearLayout.addView(name);

            mFilterItemsLinearLayout.addView(linearLayout);
        }

    }


}
