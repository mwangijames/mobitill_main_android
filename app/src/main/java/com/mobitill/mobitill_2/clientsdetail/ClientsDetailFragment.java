package com.mobitill.mobitill_2.clientsdetail;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mobitill.mobitill_2.MobitillApplication;
import com.mobitill.mobitill_2.R;
import com.mobitill.mobitill_2.cashiersdetail.CashierDetailContract;
import com.mobitill.mobitill_2.cashiersdetail.CashierDetailFragment;
import com.mobitill.mobitill_2.clientsaddedit.ClientAddEditActivity;
import com.mobitill.mobitill_2.data.models.clients.models.Client;
import com.mobitill.mobitill_2.net.ConnectivityReceiver;


import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.google.common.base.Preconditions.checkNotNull;
/**
 * A simple {@link Fragment} subclass.
 */
public class ClientsDetailFragment extends Fragment implements ClientsDetailContract.View, ConnectivityReceiver.ConnectivityReceiverListener{

    private static final String TAG = ClientsDetailFragment.class.getSimpleName();
    private static final String ARGS_APP_ID = "args_app_id";
    private static final String ARGS_CLIENTS_JSON = "args_clients_json";

    private ClientsDetailContract.Presenter mPresenter;
    private ClientsJson mClientsJson;
    private ClientsAppId mAppId;
    Unbinder mUnbinder;

    FloatingActionButton mEditClientFAB;

    public ClientsDetailFragment() {
        // Required empty public constructor
    }

    public static ClientsDetailFragment newInstance(ClientsAppId appId, ClientsJson clientsJson) {

        Bundle args = new Bundle();
        args.putSerializable(ARGS_APP_ID, appId);
        args.putSerializable(ARGS_CLIENTS_JSON, clientsJson);
        ClientsDetailFragment fragment = new ClientsDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null){
            mAppId = (ClientsAppId) getArguments().getSerializable(ARGS_APP_ID);
            mClientsJson = (ClientsJson) getArguments().getSerializable(ARGS_CLIENTS_JSON);
        } else {
            mAppId = (ClientsAppId) savedInstanceState.getSerializable(ARGS_APP_ID);
            mClientsJson = (ClientsJson) savedInstanceState.getSerializable(ARGS_CLIENTS_JSON);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(ARGS_APP_ID,mAppId);
        outState.putSerializable(ARGS_CLIENTS_JSON, mClientsJson);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mEditClientFAB = (FloatingActionButton) getActivity().findViewById(R.id.fab_edit_client);
        mEditClientFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.editClient();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_clients_detail, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MobitillApplication.getInstance().setConnectivityListener(this);
        mPresenter.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
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
        startActivity(ClientAddEditActivity.newIntent(getActivity(), mAppId, mClientsJson));
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if(isConnected){
            showNoNetwork(false);
        } else {
            showNoNetwork(true);
        }
    }
}
