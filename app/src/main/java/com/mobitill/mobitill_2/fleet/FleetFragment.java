package com.mobitill.mobitill_2.fleet;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mobitill.mobitill_2.MobitillApplication;
import com.mobitill.mobitill_2.R;
import com.mobitill.mobitill_2.data.models.fleet.models.FleetItem;
import com.mobitill.mobitill_2.fleetaddedit.FleetAddEditActivity;
import com.mobitill.mobitill_2.net.ConnectivityReceiver;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class FleetFragment extends Fragment implements FleetContract.View, ConnectivityReceiver.ConnectivityReceiverListener{

    public static final String ARGS_APP_ID = "args_app_id";

    private String mAppId;
    private Unbinder mUnbinder;

    private FleetContract.Presenter mPresenter;

    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.progress_bar) ProgressBar mProgressBar;
    @BindView(R.id.no_network) TextView mNoNetworkTextView;
    @BindView(R.id.no_fleet) TextView mNoFleetTextView;
    FloatingActionButton mOpenFleetAddEditActivityFab;

    private RecyclerView.LayoutManager mLayoutManager;
    private FleetAdapter mFleetAdapter;

    public FleetFragment() {
        // Required empty public constructor
    }

    public static FleetFragment newInstance(String appId) {
        Bundle args = new Bundle();
        FleetFragment fragment = new FleetFragment();
        args.putString(ARGS_APP_ID, appId);
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
        View view =  inflater.inflate(R.layout.fragment_fleet, container, false);

        mUnbinder = ButterKnife.bind(this, view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // FAB to open FleetAddEdit
        mOpenFleetAddEditActivityFab = (FloatingActionButton) getActivity().findViewById(R.id.fab_add_fleet);
        mOpenFleetAddEditActivityFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(FleetAddEditActivity.newIntent(getActivity(), mAppId));
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
    public void showNoFleet(boolean show) {
        mNoFleetTextView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showNoNetwork(boolean show) {
        mNoNetworkTextView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void hideTitle() {

    }

    @Override
    public void showTitle() {

    }

    @Override
    public void showFleet(List<FleetItem> fleetItems) {
        if(isAdded()){
            if(mFleetAdapter == null){
                mFleetAdapter = new FleetAdapter(fleetItems);
                mRecyclerView.setAdapter(mFleetAdapter);
            } else {
                mFleetAdapter.setFleetItems(fleetItems);
                mFleetAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void setPresenter(@NonNull FleetContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showFleetOnConnection(isConnected);
    }

    private void showFleetOnConnection(boolean isConnected){
        if(isConnected){
            mPresenter.start();
            showNoNetwork(false);
        } else {
            showNoNetwork(true);
        }
    }

    // RecyclerView ViewHolder and Adapter
    class FleetHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.fleet) TextView mFleetTextView;

        public FleetHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void bindFleetItem(FleetItem fleet){
            mFleetTextView.setText(fleet.getFleetno());
        }

        @Override
        public void onClick(View v) {

        }
    }


    private class FleetAdapter extends RecyclerView.Adapter<FleetHolder>{

        private List<FleetItem> mFleetItems;

        public FleetAdapter(List<FleetItem> fleetItems){
            mFleetItems = fleetItems;
        }

        @Override
        public FleetHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.item_fleet, parent, false);
            return new FleetHolder(view);
        }

        @Override
        public void onBindViewHolder(FleetHolder holder, int position) {
            FleetItem fleetItem = mFleetItems.get(position);
            holder.bindFleetItem(fleetItem);
        }

        @Override
        public int getItemCount() {
            return mFleetItems.size();
        }

        public void setFleetItems(List<FleetItem> fleetItems){
            mFleetItems = fleetItems;
        }
    }
}
