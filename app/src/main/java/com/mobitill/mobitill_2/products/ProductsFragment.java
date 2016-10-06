package com.mobitill.mobitill_2.products;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mobitill.mobitill_2.MobitillApplication;
import com.mobitill.mobitill_2.R;
import com.mobitill.mobitill_2.data.models.products.models.Product;
import com.mobitill.mobitill_2.net.ConnectivityReceiver;
import com.mobitill.mobitill_2.reports.ReportsContract;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindDimen;
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

    private RecyclerView.LayoutManager mLayoutManager;
    private ProductsAdapter mProductsAdapter;

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
    public void hideTitle() {

    }

    @Override
    public void showTitle() {

    }

    @Override
    public void showProducts(List<Product> products) {
        if(isAdded()){
            if(mProductsAdapter == null){
                mProductsAdapter = new ProductsAdapter(products);
                mRecyclerView.setAdapter(mProductsAdapter);
            } else {
                mProductsAdapter.setProducts(products);
                mProductsAdapter.notifyDataSetChanged();
            }
        }
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


    // RecyclerView adapter and holder
    class ProductHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.product) TextView mProductTextView;
        Product mProduct;

        public ProductHolder(View itemView) {
            super(itemView);
            mProduct = new Product();
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void bindProductName(Product product){
            mProductTextView.setText(product.getName());
        }

        @Override
        public void onClick(View v) {

        }
    }

    private class ProductsAdapter extends RecyclerView.Adapter<ProductHolder>{

        private List<Product> mProducts;

        public ProductsAdapter(List<Product> products){
            mProducts = products;
        }

        @Override
        public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.item_product, parent, false);
            return new ProductHolder(view);
        }

        @Override
        public void onBindViewHolder(ProductHolder holder, int position) {
            Product product = mProducts.get(position);
            holder.bindProductName(product);
        }

        @Override
        public int getItemCount() {
            return mProducts.size();
        }

        public void setProducts(List<Product> products){
            mProducts = products;
        }

    }
}
