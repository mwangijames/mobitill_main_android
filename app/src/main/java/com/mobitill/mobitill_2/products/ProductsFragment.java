package com.mobitill.mobitill_2.products;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mobitill.mobitill_2.MobitillApplication;
import com.mobitill.mobitill_2.R;
import com.mobitill.mobitill_2.data.models.products.models.Product;
import com.mobitill.mobitill_2.fleet.FleetActionBarCallBack;
import com.mobitill.mobitill_2.net.ConnectivityReceiver;
import com.mobitill.mobitill_2.productsaddedit.ProductAddEditActivity;
import com.mobitill.mobitill_2.utils.RecyclerClickListener;
import com.mobitill.mobitill_2.utils.RecyclerTouchListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkPositionIndex;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductsFragment extends Fragment implements ProductsContract.View ,
        ConnectivityReceiver.ConnectivityReceiverListener{

    public static final String ARGS_APP_ID = ProductsFragment.class.getSimpleName() + ".app_id";

    private ProductsContract.Presenter mPresenter;

    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.progress_bar) ProgressBar mProgressBar;
    @BindView(R.id.no_network) TextView mNoNetworkTextView;
    @BindView(R.id.no_products) TextView mNoProductsTextView;
    FloatingActionButton mOpenProductAddEditFab;

    private RecyclerView.LayoutManager mLayoutManager;
    private ProductsAdapter mProductsAdapter;
    private ActionMode mActionMode;
    private List<Product> mProducts;

    private Unbinder mUnbinder;

    private String mAppId;

    public ProductsFragment() {
        // Required empty public constructor
    }

    public static ProductsFragment newInstance(String appId) {
        
        Bundle args = new Bundle();
        args.putString(ARGS_APP_ID, appId);
        ProductsFragment fragment = new ProductsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null){
            mAppId = getArguments().getString(ARGS_APP_ID);
        } else {
            mAppId = savedInstanceState.getString(ARGS_APP_ID);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mOpenProductAddEditFab = (FloatingActionButton) getActivity().findViewById(R.id.fab_add_product);
        mOpenProductAddEditFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.createProduct(mAppId);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        MobitillApplication.getInstance().setConnectivityListener(this);
        mPresenter.start();
    }

    @Override
    public void setPresenter(@NonNull ProductsContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_products, container, false);

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
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(ARGS_APP_ID, mAppId);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        mProgressBar.setVisibility(active ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showNoProducts(boolean show) {
        mNoProductsTextView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showNoNetwork(boolean show) {
        mNoNetworkTextView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showProductDeleted(Product product) {
        Toast.makeText(getActivity(), product.getName() + " deleted", Toast.LENGTH_SHORT).show();
        mProducts.remove(product);
        mProductsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProductNotDeleted(Product product) {
        Toast.makeText(getActivity(), product.getName() + " not deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideTitle() {

    }

    @Override
    public void showTitle() {

    }

    @Override
    public void showProducts(List<Product> products) {
        if(isAdded()){
            if(mProductsAdapter == null){
                mProductsAdapter = new ProductsAdapter(products, getActivity());
                mRecyclerView.setAdapter(mProductsAdapter);
                mProducts = products;
            } else {
                mProductsAdapter.setProducts(products);
                mProductsAdapter.notifyDataSetChanged();
                mProducts = products;
            }
        }
    }

    @Override
    public void showAddEditProduct() {
        startActivity(ProductAddEditActivity.newIntent(getActivity(), mAppId));
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showProductsOnConnection(isConnected);
    }

    private void showProductsOnConnection(boolean isConnected){
        if(isConnected){
            mPresenter.start();
            showNoNetwork(false);
        } else {
            showNoNetwork(true);
        }
    }

    private void implementRecyclerViewClickListeners(){
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), mRecyclerView, new RecyclerClickListener() {
            @Override
            public void onClick(View view, int position) {
                //if ActionMode is not null select item
                if(mActionMode!=null){
                    onListItemSelect(position);
                }
            }

            @Override
            public void onLongClick(View view, int position) {
                onListItemSelect(position);
            }
        }));
    }

    private void onListItemSelect(int position){
        mProductsAdapter.toggleSelection(position);// toggle the selection

        boolean hasCheckedItems = mProductsAdapter.getSelectedCount() > 0; // Check if any items are already selected or not

        if(hasCheckedItems && mActionMode == null){

            // there are some selected items start the action mode
            mActionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(
                    new ProductsActionBarCallBack(getActivity(), mProductsAdapter, this)
            );

        } else if(!hasCheckedItems && mActionMode != null){
            // there are no selected items, finish  the action mode
            mActionMode.finish();

        }
        if(mActionMode != null){
            // set the action mode title on item selection
            mActionMode.setTitle(String.valueOf(mProductsAdapter.getSelectedCount()) + " selected");
            Toast.makeText(getActivity(), String.valueOf(mProductsAdapter.getSelectedCount()) + " selected", Toast.LENGTH_SHORT).show();

        }
    }

    public void deleteProduct(){

        SparseBooleanArray selected = mProductsAdapter.getSelectedIds();
        //loop all selected cashiers
        for(int i = (selected.size() -1); i>=0; i--){
            if(selected.valueAt(i)){
                mPresenter.deleteProduct(mAppId, mProducts.get(selected.keyAt(i)));
            }
        }
        mActionMode.finish();
    }

    public void setNullToActionMode(){
        if(mActionMode!=null){
            mActionMode = null;
        }
    }

}
