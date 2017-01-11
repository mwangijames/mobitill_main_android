package com.mobitill.mobitill_2.apps;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mobitill.mobitill_2.MobitillApplication;
import com.mobitill.mobitill_2.R;
import com.mobitill.mobitill_2.data.models.apps.models.Datum;
import com.mobitill.mobitill_2.data.models.apps.models.RealmApp;
import com.mobitill.mobitill_2.menu.MenuAppSettings;
import com.mobitill.mobitill_2.net.ConnectivityReceiver;
import com.mobitill.mobitill_2.reports.ReportsActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class AppsFragment extends Fragment implements AppsContract.View,
        ConnectivityReceiver.ConnectivityReceiverListener{

    private static final String TAG = AppsFragment.class.getSimpleName();

    private AppsContract.Presenter  mAppsPresenter;

    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.progress_bar) ProgressBar mProgressBar;
    @BindView(R.id.no_network) TextView mNoNetworkTextView;
    @BindView(R.id.no_products) TextView mNoAppsTextView;

    private RecyclerView.LayoutManager mLayoutManager;
    private AppAdapter mAppAdapter;
    private LocalAppAdapter mLocalAppAdapter;
    private int mTransactions;
    private double mTotal;


    private Unbinder mUnbinder;

    public AppsFragment() {
        // Required empty public constructor
    }

    public static AppsFragment newInstance() {
        Bundle args = new Bundle();
        AppsFragment fragment = new AppsFragment();
        fragment.setArguments(args);
        return fragment;
    }

//    @OnClick(R.id.sync)
//    public void sync(View view){
//        MobitillSyncAdapter.syncImmediately(getActivity());
//    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_apps, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        mAppsPresenter.fetchApps(false);

        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);
        mAppsPresenter.start();
        mAppsPresenter.performSync();
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
        mAppsPresenter.start();
        MobitillApplication.getInstance().setConnectivityListener(this);
    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showRemoteApps(List<Datum> apps) {
        if(isAdded()){
            if(mAppAdapter == null){
                mAppAdapter = new AppAdapter(apps);
                mRecyclerView.setAdapter(mAppAdapter);
            } else {
                mAppAdapter.setApps(apps);
                mAppAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void showLocalApps(List<RealmApp> apps) {
        if(isAdded()){
            if(mLocalAppAdapter == null){
                mLocalAppAdapter = new LocalAppAdapter(apps);
                mRecyclerView.setAdapter(mLocalAppAdapter);
            } else {
                mLocalAppAdapter.setApps(apps);
                mLocalAppAdapter.notifyDataSetChanged();
            }
        }
    }


    @Override
    public void showAppDetails(String appId, MenuAppSettings menuAppSettings) {
        //startActivity(MenuActivity.newIntent(getActivity(), appId, menuAppSettings));
        startActivity(ReportsActivity.newIntent(getActivity(), appId, menuAppSettings));
    }

    @Override
    public void showLoadingAppsError() {

    }

    @Override
    public void showNoApps(boolean show) {
        mNoAppsTextView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showNoNetwork(boolean show) {
        mNoNetworkTextView.setVisibility(show ? View.VISIBLE : View.GONE);
    }


    @Override
    public void onResponse(int code) {
        Toast.makeText(getActivity(), Integer.toString(code), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showTransactions(int transactions) {
        Log.i(TAG, "showTransactions: " + transactions);

        mTransactions = transactions;
    }

    @Override
    public void showTotal(double total) {
        Log.i(TAG, "showTotal: " + total);
        mTotal = total;
    }

    private double getTotal(){
        return mTotal;
    }

    private int getTransactions(){
        return mTransactions;
    }


    @Override
    public void showLoadingIndicator(boolean show) {
        mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }


    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(@NonNull AppsContract.Presenter presenter) {
        mAppsPresenter = checkNotNull(presenter);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showProductsOnConnection(isConnected);
    }

    private void showProductsOnConnection(boolean isConnected){
        if(isConnected){
            showNoNetwork(false);
        } else {
            showNoNetwork(true);
        }
    }


    /*
    *
    * show remote applications
    *
    * */
    class AppHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @Nullable  @BindView(R.id.app_name) TextView mAppName;


        Datum mDatum = new Datum();

        public AppHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);

        }

        public void bindAppName(Datum datum){
            mAppName.setText(datum.getApp().getName());
            mDatum = datum;
        }

        @Override
        public void onClick(View v) {
            mAppsPresenter.openAppDetails(mDatum);
        }
    }

    private class AppAdapter extends RecyclerView.Adapter<AppHolder>{

        private List<Datum> mApps;


        public AppAdapter(List<Datum> apps){
            mApps = apps;

        }

        @Override
        public AppHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.item_app,parent, false);
            return new AppHolder(view);
        }

        @Override
        public void onBindViewHolder(AppHolder holder, int position) {
            Datum datum = mApps.get(position);
            holder.bindAppName(datum);
        }

        @Override
        public int getItemCount() {
            return mApps.size();
        }

        public void setApps(List<Datum> apps){
            mApps = apps;
        }
    }

    /*
    *
    * show local applications
    *
    * */
    class LocalAppHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.app_name) TextView mAppName;
        RealmApp mRealmApp = new RealmApp();

        public LocalAppHolder(View itemView) {
            super(itemView);
            try{
                ButterKnife.bind(this, itemView);
            } catch (Exception e){
                e.printStackTrace();
            }

            itemView.setOnClickListener(this);
        }

        public void bindAppName(RealmApp app, int transactions, double total){
            mAppName.setText(app.getName());
            mRealmApp = app;

        }

        @Override
        public void onClick(View v) {
            mAppsPresenter.openAppDetails(mRealmApp);
        }
    }

    private class LocalAppAdapter extends RecyclerView.Adapter<LocalAppHolder>{

        private List<RealmApp> mApps;

        public LocalAppAdapter(List<RealmApp> apps){
            mApps = apps;
        }

        @Override
        public LocalAppHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.item_app ,parent, false);
            return new LocalAppHolder(view);
        }

        @Override
        public void onBindViewHolder(LocalAppHolder holder, int position) {
            RealmApp realmApp = mApps.get(position);
            holder.bindAppName(realmApp, mTransactions, mTotal);
        }

        @Override
        public int getItemCount() {
            return mApps.size();
        }

        public void setApps(List<RealmApp> apps){
            mApps = apps;
        }
    }
}
