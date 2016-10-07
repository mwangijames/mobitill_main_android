package com.mobitill.mobitill_2.cashiers;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.multiselector.MultiSelector;
import com.bignerdranch.android.multiselector.SingleSelector;
import com.mobitill.mobitill_2.MobitillApplication;
import com.mobitill.mobitill_2.R;
import com.mobitill.mobitill_2.cashiersaddedit.AddEditCashierActivity;
import com.mobitill.mobitill_2.data.models.cashiers.models.Cashier;
import com.mobitill.mobitill_2.net.ConnectivityReceiver;
import com.mobitill.mobitill_2.utils.RecyclerClickListener;
import com.mobitill.mobitill_2.utils.RecyclerTouchListener;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class CashiersFragment extends Fragment implements CashiersContract.View, ConnectivityReceiver.ConnectivityReceiverListener{

    public static final String ARGS_APP_ID = "args_app_id";
    public static final String TAG = CashiersFragment.class.getSimpleName();
    private String mAppId;
    private Unbinder mUnbinder;

    private CashiersContract.Presenter mPresenter;
    static List<Cashier>  mCashiers = new ArrayList<>();


    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.progress_bar) ProgressBar mProgressBar;
    @BindView(R.id.no_network) TextView mNoNetworkTextView;
    @BindView(R.id.no_cashiers) TextView mNoCashiersTextView;

    private int mSelectedItem = 0;

    FloatingActionButton mOpenAddEditActivity;
    private SparseBooleanArray mSelectedItems;
    private RecyclerView.LayoutManager mLayoutManager;
    private CashiersAdapter mCashiersAdapter;

    private ActionMode mActionMode;

    public CashiersFragment() {
        // Required empty public constructor
    }

    public static CashiersFragment newInstance(String appId) {
        Bundle args = new Bundle();
        args.putString(ARGS_APP_ID, appId);
        CashiersFragment fragment = new CashiersFragment();
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
        View view = inflater.inflate(R.layout.fragment_cashiers, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        implementRecyclerViewClickListeners();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //FAB to add new user
        mOpenAddEditActivity = (FloatingActionButton) getActivity().findViewById(R.id.fab_add_cashier);
        mOpenAddEditActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.addNewCashier(mAppId);
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
    public void showLoadingIndicator(boolean show) {
        mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showNoCashiers(boolean show) {
        mNoCashiersTextView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showNoNetwork(boolean show) {
        mNoNetworkTextView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showAddCashier(String appId) {
        startActivity(AddEditCashierActivity.newIntent(getActivity(), appId));
    }

    @Override
    public void showCashierDeleted(Cashier cashier) {
        Toast.makeText(getActivity(), cashier.getName() + " deleted", Toast.LENGTH_SHORT).show();
        mCashiers.remove(cashier);
        mCashiersAdapter.notifyDataSetChanged();
    }

    @Override
    public void showCashierDeleteFailed(String name) {
       Toast.makeText(getActivity(), name + " not deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideTitle() {

    }

    @Override
    public void showTitle() {

    }

    @Override
    public void showCashiers(List<Cashier> cashiers) {
        if(isAdded()) {
            if (mCashiersAdapter == null) {
                mCashiersAdapter = new CashiersAdapter(cashiers, getActivity());
                mRecyclerView.setAdapter(mCashiersAdapter);
                mCashiers = cashiers;
            } else {
                mCashiersAdapter.setCashiers(cashiers);
                mCashiersAdapter.notifyDataSetChanged();
                mCashiers = cashiers;
            }
        }
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void setPresenter(@NonNull CashiersContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showCashiersOnConnection(isConnected);
    }

    private void showCashiersOnConnection(boolean isConnected){
        if(isConnected){
            mPresenter.start();
            showNoNetwork(false);
        } else {
            showNoNetwork(true);
        }
    }


    private void implementRecyclerViewClickListeners(){
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), mRecyclerView, new RecyclerClickListener() {
            @Override
            public void onClick(View view, int position) {
                //if ActionMode is not null select item
                if(mActionMode!=null){
                    onListItemSelect(position);
                }
            }

            @Override
            public void onLongClick(View view, int position) {
                onListItemSelect(position);
            }
        }));
    }

    private void onListItemSelect(int position){
        mCashiersAdapter.toggleSelection(position); // toggle the selection
        boolean hasCheckedItems = mCashiersAdapter.getSelectedCount() > 0; // Check if any items are already selected or not

        if(hasCheckedItems && mActionMode == null){
            // there are some selected items start the action mode
            mActionMode = ((AppCompatActivity) getActivity()).
                            startSupportActionMode(new CashiersActionBarCallBack(getActivity(),
                                    mCashiersAdapter, mCashiers, this));

        } else if(!hasCheckedItems && mActionMode != null){
            // there are no selected items, finish  the action mode
            mActionMode.finish();

        }

        if(mActionMode != null){
            // set the action mode title on item selection
            mActionMode.setTitle(String.valueOf(mCashiersAdapter.getSelectedCount()) + " selected");
        }
    }

    public void deleteRows(){

        SparseBooleanArray selected = mCashiersAdapter.getSelectedIds();

        // loop all selected ids
        for(int i = (selected.size() - 1); i >= 0; i--){
            if(selected.valueAt(i)){
                mCashiers.remove(selected.keyAt(i));
                mCashiersAdapter.notifyDataSetChanged();
            }
        }

        Toast.makeText(getActivity(), selected.size() + " item deleted.", Toast.LENGTH_SHORT).show();
        mActionMode.finish();
    }

    public void deleteCashiers(){
        SparseBooleanArray selected = mCashiersAdapter.getSelectedIds();
        //loop all selected cashiers
        for(int i = (selected.size() -1); i>=0; i--){
            if(selected.valueAt(i)){
                mPresenter.deleteCashier(mAppId, mCashiers.get(selected.keyAt(i)));
            }
        }
        mActionMode.finish();
    }



    public void setNullToActionMode(){
        if(mActionMode!=null){
            mActionMode = null;
        }
    }
}
