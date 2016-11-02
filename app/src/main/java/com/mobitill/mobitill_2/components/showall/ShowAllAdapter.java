package com.mobitill.mobitill_2.components.showall;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mobitill.mobitill_2.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by james on 11/2/2016.
 */

public class ShowAllAdapter extends RecyclerView.Adapter<ShowAllHolder> {

    private List<HashMap<String, String>> mItems;
    private Context mContext;

    ShowAllAdapter(List<HashMap<String, String>> items,
                   Context context){
        mItems = items;
        mContext = context;
    }

    @Override
    public ShowAllHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.item_show_all, parent, false);
        return new ShowAllHolder(view);
    }

    @Override
    public void onBindViewHolder(ShowAllHolder holder, int position) {
        HashMap<String, String> item = mItems.get(position);
        holder.bindItem(item);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setItems(List<HashMap<String, String>> items){
        mItems = items;
    }
}
