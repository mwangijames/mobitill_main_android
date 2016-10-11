package com.mobitill.mobitill_2.fleet;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobitill.mobitill_2.R;
import com.mobitill.mobitill_2.data.models.fleet.models.FleetItem;

import java.util.List;

class FleetAdapter extends RecyclerView.Adapter<FleetHolder>{

    private List<FleetItem> mFleetItems;
    private Context mContext;
    private SparseBooleanArray mSelectedItemsIds;

    public FleetAdapter(List<FleetItem> fleetItems, Context context){
        mFleetItems = fleetItems;
        mContext = context;
        mSelectedItemsIds = new SparseBooleanArray();
    }

    @Override
    public FleetHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.item_fleet, parent, false);
        return new FleetHolder(view);
    }

    @Override
    public void onBindViewHolder(FleetHolder holder, int position) {
        FleetItem fleetItem = mFleetItems.get(position);
        holder.bindFleetItem(fleetItem);

        //set the selected items to checked
        holder.itemView.setSelected(mSelectedItemsIds.get(position) ? true: false);
    }

    @Override
    public int getItemCount() {
        return mFleetItems.size();
    }

    public void setFleetItems(List<FleetItem> fleetItems){
        mFleetItems = fleetItems;
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