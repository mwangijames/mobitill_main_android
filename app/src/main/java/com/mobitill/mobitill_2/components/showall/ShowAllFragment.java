package com.mobitill.mobitill_2.components.showall;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobitill.mobitill_2.MobitillApplication;
import com.mobitill.mobitill_2.R;
import com.mobitill.mobitill_2.clientsdetail.ClientsDetailFragment;
import com.mobitill.mobitill_2.components.ShowAllUtils;
import com.mobitill.mobitill_2.net.ConnectivityReceiver;

import javax.inject.Inject;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_all, container, false);
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
    public void setPresenter(ShowAllContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

    }

}
