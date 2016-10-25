package com.mobitill.mobitill_2.clientsdetail;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mobitill.mobitill_2.R;
import com.mobitill.mobitill_2.cashiersdetail.CashierDetailContract;
import com.mobitill.mobitill_2.data.models.clients.models.Client;


import static com.google.common.base.Preconditions.checkNotNull;
/**
 * A simple {@link Fragment} subclass.
 */
public class ClientsDetailFragment extends Fragment implements ClientsDetailContract.View{

    private ClientsDetailContract.Presenter mPresenter;

    public ClientsDetailFragment() {
        // Required empty public constructor
    }

    public static ClientsDetailFragment newInstance(ClientsAppId appId, ClientsJson clientsJson) {

        Bundle args = new Bundle();

        ClientsDetailFragment fragment = new ClientsDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_clients_detail, container, false);
    }

    @Override
    public void setPresenter(ClientsDetailContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void showNoNetwork(boolean show) {

    }

    @Override
    public void showMissingClient() {
        Toast.makeText(getActivity(), "No client loaded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showClient(Client client) {
        Toast.makeText(getActivity(), client.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showEditClient(ClientsJson clientsJson) {

    }

    @Override
    public boolean isActive() {
        return false;
    }
}
