package com.mobitill.mobitill_2.productsaddedit;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mobitill.mobitill_2.MobitillApplication;
import com.mobitill.mobitill_2.R;
import com.mobitill.mobitill_2.fleetaddedit.FleetAddEditContract;
import com.mobitill.mobitill_2.fleetaddedit.FleetAddEditFragment;
import com.mobitill.mobitill_2.net.ConnectivityReceiver;
import com.mobitill.mobitill_2.products.ProductsActivity;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductAddEditFragment extends Fragment implements ProductAddEditContract.View, ConnectivityReceiver.ConnectivityReceiverListener {

    private static final String TAG = ProductAddEditFragment.class.getSimpleName();
    private static final String ARGS_APP_ID = "args_app_id";

    private String mAppId;
    private Unbinder mUnbinder;
    private ProductAddEditContract.Presenter mPresenter;

    @BindView(R.id.progress_bar) ProgressBar mProgressBar;
    @BindView(R.id.no_network) LinearLayout mNoNetworkLayout;
    @BindView(R.id.content) LinearLayout mContentLayout;

    @BindView(R.id.add_product_name) EditText mProductNameEditText;
    @BindView(R.id.add_product_identifier) EditText mProductIdentifierEditText;
    @BindView(R.id.add_product_categories) EditText mProductCategoriesEditText;
    @BindView(R.id.add_product_price) EditText mProductPriceEditText;
    @BindView(R.id.add_product_description) EditText mProductDescriptionEditText;
    @BindView(R.id.add_product_size) EditText mProductSizeEditText;
    @BindView(R.id.add_product_vat) EditText mProductVATEditText;

    FloatingActionButton mSaveProductFAB;

    public ProductAddEditFragment() {
        // Required empty public constructor
    }

    public static ProductAddEditFragment newInstance(String appId) {
        Bundle args = new Bundle();
        args.putString(ARGS_APP_ID, appId);
        ProductAddEditFragment fragment = new ProductAddEditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null){
            mAppId = getArguments().getString(ARGS_APP_ID);
        } else {
            mAppId = savedInstanceState.getString(ARGS_APP_ID);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(ARGS_APP_ID, mAppId);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_add_edit, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSaveProductFAB = (FloatingActionButton) getActivity().findViewById(R.id.fab_add_product_done);
        mSaveProductFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = mProductNameEditText.getText().toString();
                String identifier = mProductIdentifierEditText.getText().toString();
                List<String> categories = Arrays.asList(mProductCategoriesEditText.getText().toString().split("\\s*,\\s*"));
                String price = mProductPriceEditText.getText().toString();
                String description = mProductDescriptionEditText.getText().toString();
                String size = mProductSizeEditText.getText().toString();
                String vat = mProductVATEditText.getText().toString();

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        MobitillApplication.getInstance().setConnectivityListener(this);
        mPresenter.start();
    }

    @Override
    public void showNoNetwork(boolean show) {
        mNoNetworkLayout.setVisibility(show ? View.VISIBLE: View.GONE);
        mContentLayout.setVisibility(!show ? View.VISIBLE: View.INVISIBLE);
        mSaveProductFAB.setVisibility(!show ? View.VISIBLE: View.INVISIBLE);
    }

    @Override
    public void showProductList() {
        startActivity(ProductsActivity.newIntent(getActivity(), mAppId));
    }

    @Override
    public void showProductCreateFailed() {
        Toast.makeText(getActivity(), "Product item creation failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNoFields() {
        Snackbar.make(mContentLayout, getString(R.string.empty_field_message), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void setPresenter(ProductAddEditContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showNetworkConnectionNotice(isConnected);
    }

    private void showNetworkConnectionNotice(boolean isConnected){
        if(isConnected){
            showNoNetwork(false);
        } else {
            showNoNetwork(true);
        }
    }
}
