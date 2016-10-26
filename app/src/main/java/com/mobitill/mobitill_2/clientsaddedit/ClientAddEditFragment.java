package com.mobitill.mobitill_2.clientsaddedit;


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
import com.mobitill.mobitill_2.cashiersaddedit.AddEditCashierContract;
import com.mobitill.mobitill_2.cashiersaddedit.AddEditCashierFragment;
import com.mobitill.mobitill_2.clients.ClientsActivity;
import com.mobitill.mobitill_2.clientsdetail.ClientsAppId;
import com.mobitill.mobitill_2.clientsdetail.ClientsJson;
import com.mobitill.mobitill_2.data.models.clients.models.Client;
import com.mobitill.mobitill_2.data.models.clients.models.create.ClientCreateResponseData;
import com.mobitill.mobitill_2.net.ConnectivityReceiver;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClientAddEditFragment extends Fragment implements ClientAddEditContract.View, ConnectivityReceiver.ConnectivityReceiverListener {

    private static final String TAG = ClientAddEditFragment.class.getSimpleName();
    private static final String ARGS_APP_ID = "args_app_id";
    private static final String ARGS_CLIENT_JSON = "args_client_json";

    private ClientsAppId mAppId;
    private ClientsJson mClientsJson;
    private Unbinder mUnbinder;
    private ClientAddEditContract.Presenter mPresenter;

    @BindView(R.id.progress_bar) ProgressBar mProgressBar;
    @BindView(R.id.no_network) LinearLayout mNoNetworkLayout;
    @BindView(R.id.content) LinearLayout mContentLayout;

    @BindView(R.id.add_client_name) EditText mNameEditText;
    @BindView(R.id.add_client_email) EditText mEmailEditText;
    @BindView(R.id.add_client_phone) EditText mPhoneEditText;
    FloatingActionButton mSaveClientFab;

    public ClientAddEditFragment() {
        // Required empty public constructor
    }

    public static ClientAddEditFragment newInstance(ClientsAppId appId) {

        Bundle args = new Bundle();
        args.putSerializable(ARGS_APP_ID, appId);
        ClientAddEditFragment fragment = new ClientAddEditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static ClientAddEditFragment newInstance(ClientsAppId appId, ClientsJson clientsJson) {

        Bundle args = new Bundle();
        args.putSerializable(ARGS_APP_ID, appId);
        args.putSerializable(ARGS_CLIENT_JSON, clientsJson);
        ClientAddEditFragment fragment = new ClientAddEditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            if(savedInstanceState == null){
                mAppId = (ClientsAppId) getArguments().getSerializable(ARGS_APP_ID);
                mClientsJson = (ClientsJson) getArguments().getSerializable(ARGS_CLIENT_JSON);
            } else {
                mAppId = (ClientsAppId) savedInstanceState.getSerializable(ARGS_APP_ID);
                mClientsJson = (ClientsJson) savedInstanceState.getSerializable(ARGS_CLIENT_JSON);
            }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(ARGS_APP_ID, mAppId);
        outState.putSerializable(ARGS_CLIENT_JSON, mClientsJson);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //create new client
        mSaveClientFab = (FloatingActionButton) getActivity().findViewById(R.id.fab_add_client_done);
        mSaveClientFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.saveClient(mAppId, mEmailEditText.getText().toString(),
                        mNameEditText.getText().toString(),
                        mPhoneEditText.getText().toString());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_client_add_edit, container, false);
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
        mSaveClientFab.setVisibility(!show ? View.VISIBLE: View.INVISIBLE);
    }

    @Override
    public void showTitle() {

    }

    @Override
    public void showClientsList() {
        startActivity(ClientsActivity.newIntent(getActivity(), mAppId.getAppId()));
    }

    @Override
    public void showClientCreatedFailed() {
        Toast.makeText(getActivity(), "Client creation failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showClientCreated(ClientCreateResponseData clientCreateResponseData) {
        Toast.makeText(getActivity(), clientCreateResponseData.getName() + " Created", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNoApplicationId() {

    }

    @Override
    public void showNoFields() {
        Snackbar.make(mNameEditText, getString(R.string.empty_field_message), Snackbar.LENGTH_LONG).show();
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
