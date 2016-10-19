package com.mobitill.mobitill_2.cashiersdetail;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mobitill.mobitill_2.MobitillApplication;
import com.mobitill.mobitill_2.R;
import com.mobitill.mobitill_2.cashiersaddedit.AddEditCashierActivity;
import com.mobitill.mobitill_2.cashiersaddedit.AddEditCashierFragment;
import com.mobitill.mobitill_2.data.models.cashiers.models.Cashier;
import com.mobitill.mobitill_2.net.ConnectivityReceiver;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class CashierDetailFragment extends Fragment implements CashierDetailContract.View, ConnectivityReceiver.ConnectivityReceiverListener {

    private static final String TAG = CashierDetailFragment.class.getSimpleName();
    private static final String ARGS_APP_ID = "args_app_id";
    private static final String ARGS_CASHIER_GSON = "args_cashier_gson";
    public static final int REQUEST_EDIT_CASHIER = 1;

    private AppId mAppId;
    private CashierGson mCashierGson;
    private CashierDetailContract.Presenter mPresenter;
    Unbinder mUnbinder;

    FloatingActionButton mEditCashierFAB;

    public CashierDetailFragment() {
        // Required empty public constructor
    }

    public static CashierDetailFragment newInstance(AppId appId, CashierGson cashierGson) {

        Bundle args = new Bundle();
        args.putSerializable(ARGS_APP_ID, appId);
        args.putSerializable(ARGS_CASHIER_GSON, cashierGson);
        CashierDetailFragment fragment = new CashierDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null){
            mAppId = (AppId) getArguments().getSerializable(ARGS_APP_ID);
            mCashierGson = (CashierGson) getArguments().getSerializable(ARGS_CASHIER_GSON);
        } else {
            mAppId = (AppId) savedInstanceState.getSerializable(ARGS_APP_ID);
            mCashierGson = (CashierGson) savedInstanceState.getSerializable(ARGS_CASHIER_GSON);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(ARGS_APP_ID, mAppId);
        outState.putSerializable(ARGS_CASHIER_GSON, mCashierGson);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mEditCashierFAB = (FloatingActionButton) getActivity().findViewById(R.id.fab_edit_cashier);
        mEditCashierFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.editCashier();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cashier_detail, container, false);
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
    public void showLoadingIndicator(boolean show) {

    }

    @Override
    public void showNoNetwork(boolean show) {

    }

    @Override
    public void showMissingCashier() {
        Toast.makeText(getActivity(), "Error displaying cashier", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCashier(Cashier cashier) {
        Toast.makeText(getActivity(), cashier.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showEditCashier(String cashierGson) {
        startActivityForResult(AddEditCashierActivity.newIntent(getActivity(), mAppId.getAppId(),
                cashierGson), REQUEST_EDIT_CASHIER);
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void setPresenter(CashierDetailContract.Presenter presenter) {
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
