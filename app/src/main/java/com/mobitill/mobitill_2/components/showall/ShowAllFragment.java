package com.mobitill.mobitill_2.components.showall;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mobitill.mobitill_2.MobitillApplication;
import com.mobitill.mobitill_2.R;
import com.mobitill.mobitill_2.clientsdetail.ClientsDetailFragment;
import com.mobitill.mobitill_2.components.ShowAllUtils;
import com.mobitill.mobitill_2.components.addedit.AddEditActivity;
import com.mobitill.mobitill_2.net.ConnectivityReceiver;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowAllFragment extends Fragment implements ShowAllContract.View, ConnectivityReceiver.ConnectivityReceiverListener{

    private ShowAllContract.Presenter mPresenter;

    private static final String TAG = ShowAllFragment.class.getSimpleName();
    private static final String ARGS_SHOW_ALL_UTILS = "args_show_all_utils";

    private ShowAllUtils mShowAllUtils;
    private Unbinder mUnbinder;

    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.progress_bar) ProgressBar mProgressBar;
    @BindView(R.id.no_products) TextView mEmptyTextView;
    @BindView(R.id.error) TextView mErrorTextView;
    @BindView(R.id.no_network) TextView mNetworkTextView;
    FloatingActionButton addFAB;

    private List<HashMap<String, String>> mItems;
    private ShowAllAdapter mShowAllAdapter;
    private RecyclerView.LayoutManager mLayoutManager;



    public ShowAllFragment() {
        // Required empty public constructor
    }

    public static ShowAllFragment newInstance(ShowAllUtils showAllUtils) {

        Bundle args = new Bundle();
        args.putSerializable(ARGS_SHOW_ALL_UTILS, showAllUtils);
        ShowAllFragment fragment = new ShowAllFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hasOptionsMenu();
        if(savedInstanceState == null){
            mShowAllUtils = (ShowAllUtils) getArguments().getSerializable(ARGS_SHOW_ALL_UTILS);
        } else {
            mShowAllUtils = (ShowAllUtils) savedInstanceState.getSerializable(ARGS_SHOW_ALL_UTILS);
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(ARGS_SHOW_ALL_UTILS, mShowAllUtils);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addFAB = (FloatingActionButton) getActivity().findViewById(R.id.fab_add);
        addFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(AddEditActivity.newIntent(getActivity(), mShowAllUtils));
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_all, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
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
    public void setPresenter(ShowAllContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if(isConnected){
            showNetworkError(false);
            showDataError(false);
            mPresenter.start();
        } else {
            showNetworkError(true);
        }
    }

    @Override
    public void showLoading(boolean show) {
        mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showEmpty(boolean show) {
        mEmptyTextView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void show(List<HashMap<String, String>> items) {
        if(isAdded()){
            if(mShowAllAdapter == null){
                mShowAllAdapter = new ShowAllAdapter(items, getActivity());
                mRecyclerView.setAdapter(mShowAllAdapter);
            }
            else {
                mShowAllAdapter.setItems(items);
                mShowAllAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void showNetworkError(boolean show) {
        mNetworkTextView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showDataError(boolean show) {
        mErrorTextView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

}
