package com.mobitill.mobitill_2.fleetaddedit;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobitill.mobitill_2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FleetAddEditFragment extends Fragment implements FleetAddEditContract.View {


    public FleetAddEditFragment() {
        // Required empty public constructor
    }

    public static FleetAddEditFragment newInstance(String appId) {

        Bundle args = new Bundle();

        FleetAddEditFragment fragment = new FleetAddEditFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fleet_add_edit, container, false);
    }

    @Override
    public void showNoNetwork(boolean show) {

    }

    @Override
    public void showTitle() {

    }

    @Override
    public void showFleetList() {

    }

    @Override
    public void showFleetCreateFailed() {

    }

    @Override
    public void showNoFields() {

    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void setPresenter(FleetAddEditContract.Presenter presenter) {

    }
}
