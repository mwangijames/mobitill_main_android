package com.mobitill.mobitill_2.fleet;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mobitill.mobitill_2.MobitillApplication;
import com.mobitill.mobitill_2.R;
import com.mobitill.mobitill_2.cashiers.CashiersActionBarCallBack;
import com.mobitill.mobitill_2.data.models.fleet.models.FleetItem;
import com.mobitill.mobitill_2.fleetaddedit.FleetAddEditActivity;
import com.mobitill.mobitill_2.net.ConnectivityReceiver;
import com.mobitill.mobitill_2.utils.RecyclerClickListener;
import com.mobitill.mobitill_2.utils.RecyclerTouchListener;

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
    private ActionMode mActionMode;
    private List<FleetItem> mFleetItems;

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
        implementRecyclerViewClickListeners();
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
    public void showFleetItemDeleted(FleetItem fleetItem) {
        Toast.makeText(getActivity(), fleetItem.getFleetno() + " deleted", Toast.LENGTH_SHORT).show();
        mFleetItems.remove(fleetItem);
        mFleetAdapter.notifyDataSetChanged();
    }

    @Override
    public void showFleetItemDeletedFailed(String fleetno) {
        Toast.makeText(getActivity(), fleetno + " not deleted", Toast.LENGTH_SHORT).show();
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
                mFleetAdapter = new FleetAdapter(fleetItems, getActivity());
                mRecyclerView.setAdapter(mFleetAdapter);
                mFleetItems = fleetItems;
            } else {
                mFleetAdapter.setFleetItems(fleetItems);
                mFleetAdapter.notifyDataSetChanged();
                mFleetItems = fleetItems;
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
        mFleetAdapter.toggleSelection(position); // toggle the selection
        boolean hasCheckedItems = mFleetAdapter.getSelectedCount() > 0; // Check if any items are already selected or not

        if(hasCheckedItems && mActionMode == null){
            // there are some selected items start the action mode
            mActionMode = ((AppCompatActivity) getActivity()).
                    startSupportActionMode(new FleetActionBarCallBack(getActivity(),
                            mFleetAdapter, this));

        } else if(!hasCheckedItems && mActionMode != null){
            // there are no selected items, finish  the action mode
            mActionMode.finish();

        }

        if(mActionMode != null){
            // set the action mode title on item selection
            mActionMode.setTitle(String.valueOf(mFleetAdapter.getSelectedCount()) + " selected");
        }
    }

    public void deleteFleetItem(){
        SparseBooleanArray selected = mFleetAdapter.getSelectedIds();
        //loop all selected cashiers
        for(int i = (selected.size() -1); i>=0; i--){
            if(selected.valueAt(i)){
                mPresenter.deleteFleetItem(mAppId, mFleetItems.get(selected.keyAt(i)));
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
