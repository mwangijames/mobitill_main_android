package com.mobitill.mobitill_2.cashiers;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobitill.mobitill_2.R;
import com.mobitill.mobitill_2.data.models.cashiers.models.Cashier;

import java.util.ArrayList;
import java.util.List;

class CashiersAdapter extends RecyclerView.Adapter<CashierHolder>{

    private List<Cashier> mCashiers;
    private Context mContext;
    private SparseBooleanArray mSelectedItemsIds;

    public CashiersAdapter(List<Cashier> cashiers, Context context){
        mCashiers = cashiers;
        mContext = context;
        mSelectedItemsIds = new SparseBooleanArray();
    }

    @Override
    public CashierHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.item_cashier, parent, false);
        return new CashierHolder(view);
    }

    @Override
    public void onBindViewHolder(CashierHolder holder, int position) {
        Cashier cashier = mCashiers.get(position);
        holder.bindCashierName(cashier);

        // set the selected items to checked
        holder.itemView.setSelected(mSelectedItemsIds.get(position) ? true : false);
    }

    @Override
    public int getItemCount() {
        return mCashiers.size();
    }

    public void setCashiers(List<Cashier> cashiers){
        mCashiers = cashiers;
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