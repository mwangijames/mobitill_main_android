package com.mobitill.mobitill_2.components.addedit;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mobitill.mobitill_2.MobitillApplication;
import com.mobitill.mobitill_2.R;
import com.mobitill.mobitill_2.components.ShowAllUtils;
import com.mobitill.mobitill_2.components.showall.ShowAllFragment;
import com.mobitill.mobitill_2.net.ConnectivityReceiver;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddEditFragment extends Fragment implements AddEditContract.View,
        ConnectivityReceiver.ConnectivityReceiverListener {

    private AddEditContract.Presenter mPresenter;

    private static final String TAG = AddEditFragment.class.getSimpleName();
    private static final String ARGS_SHOW_ALL_UTILS = "args_show_all_utils";

    private ShowAllUtils mShowAllUtils;
    private Unbinder mUnbinder;

    @BindView(R.id.progress_bar) ProgressBar mProgressBar;
    @BindView(R.id.no_products) TextView mEmptyTextView;
    @BindView(R.id.error) TextView mErrorTextView;
    @BindView(R.id.no_network) TextView mNetworkTextView;

    FloatingActionButton mAddDoneFAB;

    public AddEditFragment() {
        // Required empty public constructor
    }

    public static AddEditFragment newInstance(ShowAllUtils showAllUtils) {

        Bundle args = new Bundle();
        args.putSerializable(ARGS_SHOW_ALL_UTILS, showAllUtils);
        AddEditFragment fragment = new AddEditFragment();
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
        mAddDoneFAB = (FloatingActionButton) getActivity().findViewById(R.id.fab_add_done);
        mAddDoneFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Add", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_edit, container, false);
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
    public void setPresenter(AddEditContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

    }
}
