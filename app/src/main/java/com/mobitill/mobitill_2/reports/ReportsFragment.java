package com.mobitill.mobitill_2.reports;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Arrays.asList;

import com.mobitill.mobitill_2.MobitillApplication;
import com.mobitill.mobitill_2.R;
import com.mobitill.mobitill_2.data.models.cashiers.CashiersDataSource;
import com.mobitill.mobitill_2.data.models.cashiers.models.Cashier;
import com.mobitill.mobitill_2.data.models.products.models.Product;
import com.mobitill.mobitill_2.data.models.products.models.Products;
import com.mobitill.mobitill_2.net.ConnectivityReceiver;
import com.mobitill.mobitill_2.utils.CashiersAdapter;
import com.mobitill.mobitill_2.utils.DatePickerFragment;
import com.mobitill.mobitill_2.utils.ProductsAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportsFragment extends Fragment implements ReportsContract.View, ConnectivityReceiver.ConnectivityReceiverListener{

    private static final String DATES_LIST = "dates_list";
    private static final String PRODUCT_ID = "product_id";
    private static final String CASHIER_ID = "cashier_id";
    private static final String APP_ID = "app_id";

    private static final String ARG_APPID = "appId";
    private static final String DIALOG_DATE = " DialogDate";
    private static final int REQUEST_FROM_DATE = 0;
    private static final int REQUEST_TO_DATE = 1;

    private ReportsContract.Presenter mPresenter;
    private String mAppId = null;
    private List<Long> mDates = new ArrayList<>();
    private ProductsAdapter mProductsAdapter;
    private CashiersAdapter mCashiersAdapter;
    private int mLastSpinnerPosition;
    private int mLastCashierSpinnerPosition;
    private String mProductId;
    private String mCashierId;

    @BindView(R.id.product) Spinner mProductsSpinner;
    @BindView(R.id.cashier) Spinner mCashierSpinner;
    @BindView(R.id.from) Button mFromButton;
    @BindView(R.id.to) Button mToButton;
    @BindView(R.id.quantity) TextView mQuantityTextView;
    @BindView(R.id.total) TextView mTotalTextView;
    @BindView(R.id.report_layout) LinearLayout mReportLayout;
    @BindView(R.id.no_network) TextView mNoNetworkTextView;
    @BindView(R.id.progress_bar) ProgressBar mProgressBar;

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
        mAppId = getArguments().getString(ARG_APPID);

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

        mFromButton.setText("From: "  + mPresenter.getFormattedDate(new Date()));
        mToButton.setText("To: "  + mPresenter.getFormattedDate(new Date()));

        mPresenter.start();
        return view;
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


    @OnClick(R.id.from)
    public void fromDate(Button button) {
        FragmentManager manager = getFragmentManager();
        DatePickerFragment dialog = DatePickerFragment.newInstance(new Date());
        dialog.setTargetFragment(ReportsFragment.this,REQUEST_FROM_DATE);
        dialog.show(manager, DIALOG_DATE);
    }

    @OnClick(R.id.to)
    public void toDate(Button button) {
        FragmentManager manager = getFragmentManager();
        DatePickerFragment dialog = DatePickerFragment.newInstance(new Date());
        dialog.setTargetFragment(ReportsFragment.this, REQUEST_TO_DATE);
        dialog.show(manager, DIALOG_DATE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode != Activity.RESULT_OK){
            return;
        }

        if(requestCode  == REQUEST_FROM_DATE){
            Date date = (Date) data
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mDates.remove(0);
            mDates.add(0, date.getTime());
            mPresenter.fetchReports(mAppId, mDates, mProductId, mCashierId);
            mFromButton.setText("From: "  + mPresenter.getFormattedDate(date));
        }

        if(requestCode == REQUEST_TO_DATE){
            Date date = (Date) data
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mDates.remove(1);
            mDates.add(1, date.getTime());
            mPresenter.fetchReports(mAppId, mDates, mProductId, mCashierId);
            mToButton.setText("To: " + mPresenter.getFormattedDate(date));
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        mProgressBar.setVisibility(active ? View.VISIBLE : View.GONE);
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
    public void showTotal(int total) {
        mTotalTextView.setText(Integer.toString(total));
    }

    @Override
    public void showProducts(List<Product> products) {

        Product[] productsArray = mPresenter.getProductsArray(products);

        mProductsAdapter = new ProductsAdapter(getActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                productsArray);
        // Specify the layout to use when the list of choices appears
        mProductsSpinner.setAdapter(mProductsAdapter);
    }

    @OnItemSelected(R.id.product)
    public void spinnerProductItemSelected(int position){
        mLastSpinnerPosition = mLastSpinnerPosition + 1;
        if(mLastSpinnerPosition > 1){
            if(position > 0){
                Product product = mProductsAdapter.getProduct(position);
                mProductId = product.getId();
                mPresenter.fetchReports(mAppId, mDates, product.getId());
            } else {
                mProductId = "";
                mPresenter.fetchReports(mAppId, mDates, "");
            }
        }
    }

    @Override
    public void showCashiers(List<Cashier> cashiers) {
        Cashier[] cashiersArray = mPresenter.getCashiersArray(cashiers);
        mCashiersAdapter = new CashiersAdapter(getActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                cashiersArray);
        // specify the layout to use when the list of choices appears
        mCashierSpinner.setAdapter(mCashiersAdapter);
    }

    @OnItemSelected(R.id.cashier)
    public void spinnerCashierItemSelected(int position){
         mLastCashierSpinnerPosition = mLastCashierSpinnerPosition + 1;
        if(mLastCashierSpinnerPosition > 1){
            if(position > 0){
                Cashier cashier = mCashiersAdapter.getCashier(position);
                mPresenter.fetchReports(mAppId, mDates, mProductId, cashier.getId());
                mCashierId = cashier.getId();
            } else {
                mCashierId = "";
                mPresenter.fetchReports(mAppId, mDates, mProductId);
            }
        }
    }

    @Override
    public void showQuantity(int quantity) {
        mQuantityTextView.setText(Integer.toString(quantity));
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

}
