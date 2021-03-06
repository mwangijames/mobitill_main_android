package com.mobitill.mobitill_2.cashiersaddedit;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mobitill.mobitill_2.MobitillApplication;
import com.mobitill.mobitill_2.R;
import com.mobitill.mobitill_2.cashiers.CashiersActivity;
import com.mobitill.mobitill_2.net.ConnectivityReceiver;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.google.common.base.Preconditions.checkNotNull;


public class AddEditCashierFragment extends Fragment implements AddEditCashierContract.View, ConnectivityReceiver.ConnectivityReceiverListener {

    private static final String TAG = AddEditCashierFragment.class.getSimpleName();
    private static final String ARGS_APP_ID = "args_app_id";

    private String mAppId;
    private Unbinder mUnbinder;
    private AddEditCashierContract.Presenter mPresenter;

    @BindView(R.id.progress_bar) ProgressBar mProgressBar;
    @BindView(R.id.no_network) LinearLayout mNoNetworkLayout;
    @BindView(R.id.content) LinearLayout mContentLayout;

    @BindView(R.id.add_cashier_name) EditText mNameEditText;
    @BindView(R.id.add_cashier_password) EditText mPasswordEditText;
    @BindView(R.id.add_cashier_username) EditText mUsernameEditText;

    FloatingActionButton mSaveCashierFab;



    public AddEditCashierFragment() {
        // Required empty public constructor
    }

    public static AddEditCashierFragment newInstance(String appId) {
        AddEditCashierFragment fragment = new AddEditCashierFragment();
        Bundle args = new Bundle();
        args.putString(ARGS_APP_ID, appId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSaveCashierFab = (FloatingActionButton) getActivity().findViewById(R.id.fab_add_cashier_done);
        mSaveCashierFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mPresenter.saveCashier(mAppId, mNameEditText.getText().toString(),
                       mUsernameEditText.getText().toString(),
                       mPasswordEditText.getText().toString());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_edit_cashier, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
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
        mSaveCashierFab.setVisibility(!show ? View.VISIBLE: View.INVISIBLE);
    }

    @Override
    public void showTitle() {

    }

    @Override
    public void showEmptyCashierError() {

    }

    @Override
    public void showCashiersList() {
        startActivity(CashiersActivity.newIntent(getActivity(), mAppId));
    }

    @Override
    public void showCashierCreateFailed() {
        Toast.makeText(getActivity(), "Cashier creation failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNoApplicationId() {

    }

    @Override
    public void showNoFields() {
        Snackbar.make(mNameEditText, getString(R.string.empty_field_message), Snackbar.LENGTH_LONG).show();
    }


    @Override
    public void setName(String name) {

    }

    @Override
    public void setUsername(String username) {

    }

    @Override
    public void setPassword(String password) {

    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void setPresenter(AddEditCashierContract.Presenter presenter) {
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
