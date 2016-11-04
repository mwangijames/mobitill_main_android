package com.mobitill.mobitill_2.components.addedit;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobitill.mobitill_2.R;
import com.mobitill.mobitill_2.net.ConnectivityReceiver;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddEditFragment extends Fragment implements AddEditContract.View, ConnectivityReceiver.ConnectivityReceiverListener {


    public AddEditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_edit, container, false);
    }

    @Override
    public void setPresenter(AddEditContract.Presenter presenter) {

    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

    }
}
