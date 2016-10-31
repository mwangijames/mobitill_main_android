package com.mobitill.mobitill_2.fleetdetail;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobitill.mobitill_2.R;
import com.mobitill.mobitill_2.data.models.fleet.models.FleetItem;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class FleetDetailFragment extends Fragment implements FleetDetailContract.View {

    private FleetDetailContract.Presenter mPresenter;

    public FleetDetailFragment() {
        // Required empty public constructor
    }

    public static FleetDetailFragment newInstance(FleetAppId appId, FleetJson fleetJson) {

        Bundle args = new Bundle();

        FleetDetailFragment fragment = new FleetDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_fleet_detail, container, false);

        return view;
    }



    @Override
    public void setPresenter(FleetDetailContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void showNoNetwork(Boolean show) {

    }

    @Override
    public void showNoFleetItem() {

    }

    @Override
    public void showFleetItem(FleetItem fleetItem) {

    }

    @Override
    public void showEditFleetItem(FleetJson fleetJson) {

    }

    @Override
    public boolean isActive() {
        return false;
    }
}
