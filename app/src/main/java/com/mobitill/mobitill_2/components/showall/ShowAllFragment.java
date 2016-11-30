package com.mobitill.mobitill_2.components.showall;


import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.mobitill.mobitill_2.MobitillApplication;
import com.mobitill.mobitill_2.R;
import com.mobitill.mobitill_2.components.ShowAllUtils;
import com.mobitill.mobitill_2.components.addedit.AddEditActivity;
import com.mobitill.mobitill_2.data.models.generic.Actions;
import com.mobitill.mobitill_2.net.ConnectivityReceiver;
import com.mobitill.mobitill_2.utils.DpPixelsConversion;
import com.mobitill.mobitill_2.utils.RecyclerClickListener;
import com.mobitill.mobitill_2.utils.RecyclerTouchListener;
import com.mobitill.mobitill_2.utils.SettingsHelper;

import org.apache.commons.lang3.text.WordUtils;

import java.util.HashMap;
import java.util.List;

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
    @BindView(R.id.header_layout) LinearLayout mHeaderLayout;
    FloatingActionButton addFAB;

    private List<HashMap<String, String>> mItems;
    private ShowAllAdapter mShowAllAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ActionMode mActionMode;
    private Boolean mIsInventory;
    /** to prevent multiple calls to inflate menu */
    private boolean mMenuIsInflated;


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
        setHasOptionsMenu(true);
        if(savedInstanceState == null){
            mShowAllUtils = (ShowAllUtils) getArguments().getSerializable(ARGS_SHOW_ALL_UTILS);
        } else {
            mShowAllUtils = (ShowAllUtils) savedInstanceState.getSerializable(ARGS_SHOW_ALL_UTILS);
        }

        mIsInventory = mShowAllUtils.getModel().equalsIgnoreCase("inventory");
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
        //addFAB.setVisibility(mShowAllUtils.getModel().equalsIgnoreCase("inventory") ? View.GONE : View.VISIBLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_all, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        implementRecyclerViewClickListeners();
        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        if(!mMenuIsInflated){
            inflater.inflate(R.menu.show_all_menu, menu);

            hideLogItem(menu);
            mMenuIsInflated = true;
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    private void hideLogItem(Menu menu) {
        SettingsHelper settingsHelper = new SettingsHelper();
        MenuItem menuItem = menu.findItem(R.id.action_logs);
        if(!mShowAllUtils.getModel().equalsIgnoreCase("inventory")){
            getActivity().invalidateOptionsMenu();
            menuItem.setVisible(false);
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_logs:
                Actions actions = new Actions();
                if (item.isChecked()) {
                    mPresenter.fetch();
                    item.setChecked(false);
//                    item.setIcon(R.drawable.ic_logs);
                    item.setTitle(R.string.action_logs);
                } else {
                    mPresenter.fetch(actions.LOG);
                    item.setChecked(true);
//                    item.setIcon(R.drawable.ic_list);
                    item.setTitle(R.string.action_list);
                }

                //Toast.makeText(getActivity(), "logs", Toast.LENGTH_SHORT).show();
                return true;
        }

        return super.onOptionsItemSelected(item);

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
        if(mProgressBar!=null){
            mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void showEmpty(boolean show) {
        mEmptyTextView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void show(List<HashMap<String, String>> items) {
        if(isAdded()){
            if(mShowAllAdapter == null){
                mShowAllAdapter = new ShowAllAdapter(items, getActivity(), mShowAllUtils.getModel().equalsIgnoreCase("inventory"));
                mRecyclerView.setAdapter(mShowAllAdapter);
                mItems = items;
            }
            else {
                mShowAllAdapter.setItems(items);
                mItems = items;
                mShowAllAdapter.notifyDataSetChanged();
            }
        }

    }

    @Override
    public void showNetworkError(boolean show) {
        if(mNetworkTextView!=null){
            mNetworkTextView.setVisibility(show ? View.VISIBLE : View.GONE);
        }

    }

    @Override
    public void showDataError(boolean show) {
        if(mErrorTextView!=null){
            mErrorTextView.setVisibility(show ? View.VISIBLE : View.GONE);
        }

    }



    @Override
    public void showItemDeleted(HashMap<String, String> item) {
        Toast.makeText(getActivity(), R.string.delete_success, Toast.LENGTH_SHORT).show();
        mItems.remove(item);
        mShowAllAdapter.notifyDataSetChanged();
    }

    @Override
    public void showItemDeleteFailed() {
        Toast.makeText(getActivity(), R.string.delete_fail, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showEdit(ShowAllUtils showAllUtils, HashMap<String ,String> item) {
        startActivity(AddEditActivity.newIntent(getActivity(),
                showAllUtils, item));
    }

    @Override
    public void showHeader(HashMap<String, String> item) {

        ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        int padding = getActivity().getResources().getDimensionPixelOffset(R.dimen.padding_8dp);


        mHeaderLayout.setLayoutParams(layoutParams);
        mHeaderLayout.setPadding(0, 0, padding, 0);
        mHeaderLayout.removeAllViews();

        for(HashMap.Entry<String, String> entry : item.entrySet()){
            if(!entry.getKey().equalsIgnoreCase("id")){
                TextView keyTextView = new TextView(getActivity());
                keyTextView.setText(WordUtils.capitalizeFully(entry.getKey()));
                // keyTextView.setPadding(padding, 0, 0, 0);
                keyTextView.setTextColor(getActivity().getResources().getColor(R.color.colorTextBlack));
                keyTextView.setLayoutParams(new TableRow.LayoutParams(DpPixelsConversion.pxToDp(1400), ViewGroup.LayoutParams.WRAP_CONTENT));
                keyTextView.setPaintFlags(keyTextView.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
                keyTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                mHeaderLayout.addView(keyTextView);
            }
        }


    }

    private void implementRecyclerViewClickListeners(){
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), mRecyclerView, new RecyclerClickListener() {
            @Override
            public void onClick(View view, int position) {
                //if ActionMode is not null select item
                if(mActionMode!=null){
                    if(!mIsInventory){
                        onListItemSelect(position);
                        showEditActionItem();
                    }
                } else {
                    //mPresenter.showClientDetailView(mClients.get(position));
                }
            }

            @Override
            public void onLongClick(View view, int position) {
                if(!mIsInventory){
                    onListItemSelect(position);
                    showEditActionItem();
                }
            }
        }));
    }

    private void showEditActionItem() {
        if(mActionMode != null){
            SparseBooleanArray selected = mShowAllAdapter.getSelectedIds();
            Menu menu = mActionMode.getMenu();
            MenuItem updateItem = (MenuItem) menu.findItem(R.id.update_item);
            if(selected.size()> 1){
                updateItem.setVisible(false);
            } else {
                updateItem.setVisible(true);
            }

        }
    }

    private void onListItemSelect(int position){
        mShowAllAdapter.toggleSelection(position); // toggle the selection
        boolean hasCheckedItems = mShowAllAdapter.getSelectedCount() > 0; // Check if any items are already selected or not

        if(hasCheckedItems && mActionMode == null){
            // there are some selected items start the action mode
            mActionMode = ((AppCompatActivity) getActivity()).
                    startSupportActionMode(new AddEditActionBarCallBack(getActivity(),
                            mShowAllAdapter, this));

        } else if(!hasCheckedItems && mActionMode != null){
            // there are no selected items, finish  the action mode
            mActionMode.finish();

        }

        if(mActionMode != null){
            // set the action mode title on item selection
            mActionMode.setTitle(String.valueOf(mShowAllAdapter.getSelectedCount()) + " selected");
        }
    }

    @Override
    public void delete() {
        SparseBooleanArray selected = mShowAllAdapter.getSelectedIds();

        // loop all selected items
        for(int i = (selected.size() -1); i>=0; i--){
            if(selected.valueAt(i)){
                mPresenter.delete(mItems.get(selected.keyAt(i)));
            }
        }

        mActionMode.finish();
    }

    public void setNullToActionMode(){
        if(mActionMode!=null){
            mActionMode = null;
        }
    }

    public void openEdit(){
        SparseBooleanArray selected = mShowAllAdapter.getSelectedIds();
        // loop all selected items
        for(int i = (selected.size() -1); i>=0; i--){
            if(selected.valueAt(i)){
                mPresenter.openEdit(mItems.get(selected.keyAt(i)));
            }
        }
    }


}
