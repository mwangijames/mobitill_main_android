package com.mobitill.mobitill_2.reports;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mobitill.mobitill_2.R;

import java.util.List;

/**
 * Created by james on 11/11/2016.
 */

public class FilterItemsAdapter extends ArrayAdapter<FilterItem> {

    private Context mContext;
    private List<FilterItem>mFilterItems;

    public FilterItemsAdapter(Context context, int resource, List<FilterItem> objects) {
        super(context, resource, objects);
        mContext = context;
        mFilterItems = objects;
    }

    public int getCount(){
        return mFilterItems.size();
    }

    public FilterItem getFilterItem(int position){
        return mFilterItems.get(position);
    }

    public int getFilterItemPosition(int position){
        return position;
    }

    public String getFilterItemId(int position){
        return mFilterItems.get(position).getId();
    }

    public String getFilterItemName(int position){
        return mFilterItems.get(position).getName();
    }

    // this is for the passive state of the spinner


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // create a dynamic textview
        TextView label = new TextView(mContext);
        label.setPadding(30, 30, 30 ,30);
        label.setTextColor(mContext.getResources().getColor(R.color.colorTextDark));
        label.setText(mFilterItems.get(position).getName());
        return label;
    }

    // and here is when the chooser is popped up
    // normally is the same view, but you can customize it if you want

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(mContext);
        // label.getLayoutParams().height = mContext.getResources().getDimensionPixelSize(R.dimen.text_view_height);
        label.setPadding(30, 30, 30 ,30);
        label.setTextColor(mContext.getResources().getColor(R.color.colorTextDark));
        label.setText(mFilterItems.get(position).getName());
        return label;
    }
}
