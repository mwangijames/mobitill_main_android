package com.mobitill.mobitill_2.clients;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mobitill.mobitill_2.MobitillApplication;
import com.mobitill.mobitill_2.R;
import com.mobitill.mobitill_2.clientsaddedit.ClientAddEditActivity;
import com.mobitill.mobitill_2.data.models.clients.models.Client;
import com.mobitill.mobitill_2.data.models.clients.models.Clients;
import com.mobitill.mobitill_2.net.ConnectivityReceiver;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClientsFragment extends Fragment implements ClientsContract.View, ConnectivityReceiver.ConnectivityReceiverListener {

    public static final String ARGS_APP_ID = "args_app_id";

    private String mAppId;
    private Unbinder mUnbinder;


    private ClientsContract.Presenter mPresenter;

    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.progress_bar) ProgressBar mProgressBar;
    @BindView(R.id.no_network) TextView mNoNetworkTextView;
    @BindView(R.id.no_clients) TextView mNoClientsTextView;
    FloatingActionButton mOpenClientAddEditActivity;

    private RecyclerView.LayoutManager mLayoutManager;
    private ClientsAdapter mClientsAdapter;

    public ClientsFragment() {
        // Required empty public constructor
    }

    public static ClientsFragment newInstance(String appId) {
        Bundle args = new Bundle();
        ClientsFragment fragment = new ClientsFragment();
        args.putString(ARGS_APP_ID, appId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null) {
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
        View view = inflater.inflate(R.layout.fragment_clients, container, false);

        mUnbinder = ButterKnife.bind(this, view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //FAB To add new Client
        mOpenClientAddEditActivity = (FloatingActionButton) getActivity().findViewById(R.id.fab_add_client);
        mOpenClientAddEditActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.addNewClient(mAppId);
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
    public void showNoClients(boolean show) {
        mNoClientsTextView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showNoNetwork(boolean show) {
        mNoNetworkTextView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showAddClient(String appId) {
        startActivity(ClientAddEditActivity.newIntent(getActivity(), mAppId));
    }

    @Override
    public void hideTitle() {

    }

    @Override
    public void showTitle() {

    }

    @Override
    public void showClients(List<Client> clients) {
        if(isAdded()){
            if(mClientsAdapter == null){
                mClientsAdapter = new ClientsAdapter(clients);
                mRecyclerView.setAdapter(mClientsAdapter);
            } else {
                mClientsAdapter.setClients(clients);
                mClientsAdapter.notifyDataSetChanged();
            }
        }
    }


    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void setPresenter(@NonNull ClientsContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showClientsOnConnection(isConnected);
    }

    private void showClientsOnConnection(boolean isConnected){
        if(isConnected){
            mPresenter.start();
            showNoNetwork(false);
        } else {
            showNoNetwork(true);
        }
    }

    //RecyclerView adapter and holder

    class ClientsHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.clients) TextView mClientsTextView;
        Client mClient;

        public ClientsHolder(View itemView) {
            super(itemView);
            mClient = new Client();
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void bindClientName(Client client){
            mClientsTextView.setText(client.getName());
        }

        @Override
        public void onClick(View v) {

        }
    }

    private class ClientsAdapter extends RecyclerView.Adapter<ClientsHolder>{

        private List<Client> mClients;

        public ClientsAdapter(List<Client> clients){
            mClients = clients;
        }

        @Override
        public ClientsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.item_clients, parent, false);
            return new ClientsHolder(view);
        }

        @Override
        public void onBindViewHolder(ClientsHolder holder, int position) {
            Client client = mClients.get(position);
            holder.bindClientName(client);
        }

        @Override
        public int getItemCount() {
            return mClients.size();
        }

        public void setClients(List<Client> clients){
            mClients = clients;
        }
    }



}
