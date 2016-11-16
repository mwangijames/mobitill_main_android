package com.mobitill.mobitill_2.fleetaddedit;


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
import android.widget.Toast;

import com.mobitill.mobitill_2.MobitillApplication;
import com.mobitill.mobitill_2.R;
import com.mobitill.mobitill_2.data.models.fleet.models.create.FleetCreateResponseData;
import com.mobitill.mobitill_2.fleet.FleetActivity;
import com.mobitill.mobitill_2.net.ConnectivityReceiver;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class FleetAddEditFragment extends Fragment implements FleetAddEditContract.View, ConnectivityReceiver.ConnectivityReceiverListener {

    private static final String TAG = FleetAddEditFragment.class.getSimpleName();
    private static final String ARGS_APP_ID = "args_app_id";

    private String mAppId;
    private Unbinder mUnbinder;
    private FleetAddEditContract.Presenter mPresenter;

    @BindView(R.id.progress_bar) ProgressBar mProgressBar;
    @BindView(R.id.no_network) LinearLayout mNoNetworkLayout;
    @BindView(R.id.content) LinearLayout mContentLayout;

    @BindView(R.id.add_fleet_no) EditText mFleetNoEditText;
    @BindView(R.id.add_plate_no) EditText mPlateNoEditText;
    FloatingActionButton mSaveFleetItemFab;

    public FleetAddEditFragment() {
        // Required empty public constructor
    }

    public static FleetAddEditFragment newInstance(String appId) {

        Bundle args = new Bundle();
        args.putString(ARGS_APP_ID, appId);
        FleetAddEditFragment fragment = new FleetAddEditFragment();
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSaveFleetItemFab = (FloatingActionButton) getActivity().findViewById(R.id.fab_add_fleet_done);
        mSaveFleetItemFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.saveFleetItem(mAppId, mPlateNoEditText.getText().toString(),
                        mFleetNoEditText.getText().toString());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fleet_add_edit, container, false);
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
        mSaveFleetItemFab.setVisibility(!show ? View.VISIBLE: View.INVISIBLE);
    }

    @Override
    public void showTitle() {

    }

    @Override
    public void showFleetList() {
        startActivity(FleetActivity.newIntent(getActivity(), mAppId));
    }

    @Override
    public void showFleetCreateFailed() {
        Toast.makeText(getActivity(), "Fleet item creation failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showFleetItemCreated(FleetCreateResponseData fleetCreateResponseData) {
        Toast.makeText(getActivity(), fleetCreateResponseData.getFleetno() + " Created", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNoFields() {
        Snackbar.make(mFleetNoEditText, getString(R.string.empty_field_message), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void setPresenter(FleetAddEditContract.Presenter presenter) {
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
