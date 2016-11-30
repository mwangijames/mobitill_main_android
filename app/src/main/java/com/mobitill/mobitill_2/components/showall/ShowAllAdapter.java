package com.mobitill.mobitill_2.components.showall;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.mobitill.mobitill_2.R;
import com.mobitill.mobitill_2.utils.DpPixelsConversion;

import java.util.HashMap;
import java.util.List;

/**
 * Created by james on 11/2/2016.
 */

public class ShowAllAdapter extends RecyclerView.Adapter<ShowAllHolder> {

    private List<HashMap<String, String>> mItems;
    private Context mContext;
    private SparseBooleanArray mSelectedItemsIds;
    private Boolean mIsSelectable;

    ShowAllAdapter(List<HashMap<String, String>> items,
                   Context context, boolean isSelectable){
        mItems = items;
        mContext = context;
        mSelectedItemsIds = new SparseBooleanArray();
        mIsSelectable = isSelectable;
    }

    @Override
    public ShowAllHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.item_show_all, parent, false);
        return new ShowAllHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(ShowAllHolder holder, int position) {
        HashMap<String, String> item = mItems.get(position);

        //LinearLayout mLinearLayout = new LinearLayout(mContext);

        LinearLayout.LayoutParams linLayoutParams = new LinearLayout
                .LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        LinearLayout rootLayout = new LinearLayout(mContext);
        rootLayout.setOrientation(LinearLayout.HORIZONTAL);
        rootLayout.setLayoutParams(linLayoutParams);

        holder.mRootLayout.removeAllViews();
        for(HashMap.Entry<String, String> entry : item.entrySet()){

            String key = entry.getKey();
            String value = entry.getValue();

            int padding = mContext.getResources().getDimensionPixelOffset(R.dimen.padding_16dp);

            ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(DpPixelsConversion.pxToDp(1400),
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            LinearLayout linearLayout = new LinearLayout(mContext);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setLayoutParams(layoutParams);
            linearLayout.setPadding(0, 0, padding, 0);
            //linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
            linearLayout.removeAllViews();

//            TextView keyTextView = new TextView(mContext);
//            keyTextView.setText(entry.getKey());
//            keyTextView.setTextColor(mContext.getResources().getColor(R.color.colorTextBlack));
//            keyTextView.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//            linearLayout.addView(keyTextView);

            if(!entry.getKey().equalsIgnoreCase("id")){
                TextView valueTextView = new TextView(mContext);
                valueTextView.setText(entry.getValue());
                valueTextView.setTextColor(mContext.getResources().getColor(R.color.colorTextDark));
                valueTextView.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                valueTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                linearLayout.addView(valueTextView);
            }

            rootLayout.addView(linearLayout);

        }

        holder.mRootLayout.addView(rootLayout);

        // set the selected items to checked
        if(!mIsSelectable){
            holder.itemView.setSelected(mSelectedItemsIds.get(position) ? true : false);
        }

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setItems(List<HashMap<String, String>> items){
        mItems = items;
        notifyDataSetChanged();
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
