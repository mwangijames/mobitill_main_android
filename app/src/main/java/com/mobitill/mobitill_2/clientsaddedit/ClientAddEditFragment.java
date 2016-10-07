package com.mobitill.mobitill_2.clientsaddedit;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobitill.mobitill_2.R;
import com.mobitill.mobitill_2.cashiersaddedit.AddEditCashierFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClientAddEditFragment extends Fragment implements ClientAddEditContract.View {

    private static final String TAG = ClientAddEditFragment.class.getSimpleName();
    private static final String ARGS_APP_ID = "args_app_id";

    public ClientAddEditFragment() {
        // Required empty public constructor
    }

    public static ClientAddEditFragment newInstance(String appId) {

        Bundle args = new Bundle();

        ClientAddEditFragment fragment = new ClientAddEditFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_client_add_edit, container, false);
    }

    @Override
    public void showNoNetwork(boolean show) {

    }

    @Override
    public void showTitle() {

    }

    @Override
    public void showClientsList() {

    }

    @Override
    public void showClientCreatedFailed() {

    }

    @Override
    public void showNoApplicationId() {

    }

    @Override
    public void showNoFields() {

    }

    @Override
    public void setEmail(String email) {

    }

    @Override
    public void setName(String name) {

    }

    @Override
    public void setPhone(String phone) {

    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void setPresenter(ClientAddEditContract.Presenter presenter) {

    }
}
