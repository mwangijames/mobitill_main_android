package com.mobitill.mobitill_2.products;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobitill.mobitill_2.R;
import com.mobitill.mobitill_2.data.models.products.models.Product;

import java.util.List;

class ProductsAdapter extends RecyclerView.Adapter<ProductsHolder>{

    private List<Product> mProducts;
    private Context mContext;
    private SparseBooleanArray mSelectedItemsIds;

    public ProductsAdapter(List<Product> products, Context context){
        mProducts = products;
        mContext = context;
        mSelectedItemsIds = new SparseBooleanArray();
    }

    @Override
    public ProductsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.item_product, parent, false);
        return new ProductsHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductsHolder holder, int position) {
        Product product = mProducts.get(position);
        holder.bindProductName(product);

        //set the selected items to checked
        holder.itemView.setSelected(mSelectedItemsIds.get(position) ? true: false);
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    public void setProducts(List<Product> products){
        mProducts = products;
    }

        /*
    *Methods required for do selections, remove selections etc
    * */

    //Toggle selection methods
    public void toggleSelection(int position){
        selectView(position, !mSelectedItemsIds.get(position));
    }

    // Remove selected selections
    public void removeSelection(){
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    // Put or delete selected position into SparseBooleanArray
    public void selectView(int position, boolean value){
        if(value){
            mSelectedItemsIds.put(position, value);
        } else {
            mSelectedItemsIds.delete(position);
        }
        notifyDataSetChanged();
    }

    // Get total selected count
    public int getSelectedCount(){
        return mSelectedItemsIds.size();
    }

    // Return all selected ids
    public SparseBooleanArray getSelectedIds(){
        return mSelectedItemsIds;
    }

}