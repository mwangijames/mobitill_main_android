package com.mobitill.mobitill_2.menu;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mobitill.mobitill_2.R;
import com.mobitill.mobitill_2.components.ShowAllUtils;
import com.mobitill.mobitill_2.components.showall.ShowAllActivity;
import com.mobitill.mobitill_2.fleetaddedit.FleetAddEditFragment;
import com.mobitill.mobitill_2.utils.RecyclerClickListener;
import com.mobitill.mobitill_2.utils.RecyclerTouchListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment implements MenuContract.View{

    private static final String TAG = FleetAddEditFragment.class.getSimpleName();
    private static final String ARGS_APP_ID = "args_app_id";
    private static final String ARGS_APP_SETTINGS = "args_app_settings";

    private MenuContract.Presenter mPresenter;

    private Unbinder mUnbinder;
    private String mAppId;
    private MenuAppSettings mMenuAppSettings;
    private List<String> mModels;

    @BindView(R.id.recycler_view)RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private MenuAdapter mMenuAdapter;

    public MenuFragment() {
        // Required empty public constructor
    }

    public static MenuFragment newInstance(String appId, MenuAppSettings menuAppSettings) {

        Bundle args = new Bundle();
        args.putString(ARGS_APP_ID, appId);
        args.putSerializable(ARGS_APP_SETTINGS, menuAppSettings);
        MenuFragment fragment = new MenuFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null){
            mAppId = getArguments().getString(ARGS_APP_ID);
            mMenuAppSettings = (MenuAppSettings) getArguments().getSerializable(ARGS_APP_SETTINGS);
        } else {
            mAppId = savedInstanceState.getString(ARGS_APP_ID);
            mMenuAppSettings = (MenuAppSettings) savedInstanceState.getSerializable(ARGS_APP_SETTINGS);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(ARGS_APP_ID, mAppId);
        outState.putSerializable(ARGS_APP_SETTINGS, mMenuAppSettings);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        mUnbinder = ButterKnife.bind(this, view);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        implementRecyclerViewClickListeners();
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
        mPresenter.start();
    }

    @Override
    public void setPresenter(MenuContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void showMenuItems(List<String> models) {
        if(isAdded()){
            if(mMenuAdapter == null){
                mMenuAdapter = new MenuAdapter(models, getActivity());
                mModels = models;
                mRecyclerView.setAdapter(mMenuAdapter);
            } else {
                mMenuAdapter.setModels(models);
                mModels = models;
                mMenuAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void showAllActivity(ShowAllUtils showAllUtils) {
        startActivity(ShowAllActivity.newIntent(getActivity(), showAllUtils, mMenuAppSettings));
    }

    private void implementRecyclerViewClickListeners(){
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), mRecyclerView, new RecyclerClickListener() {
            @Override
            public void onClick(View view, int position) {
                if(mModels != null && !mModels.isEmpty()){
                    Toast.makeText(getActivity(), "openShowAll", Toast.LENGTH_SHORT).show();
                    mPresenter.openShowAll(mModels.get(position));
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }


}
