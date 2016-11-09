package com.mobitill.mobitill_2.reports;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobitill.mobitill_2.R;

import java.util.List;

/**
 * Created by james on 11/9/2016.
 */

public class DrawerAdapter extends RecyclerView.Adapter<DrawerHolder> {

    List<String> mModels;

    public DrawerAdapter(List<String> models){
        mModels = models;
    }

    @Override
    public DrawerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view =  layoutInflater.inflate(R.layout.item_drawer, parent, false);
        return new DrawerHolder(view);
    }

    @Override
    public void onBindViewHolder(DrawerHolder holder, int position) {
            String model = mModels.get(position);
            holder.bindView(model);
    }

    @Override
    public int getItemCount() {
        return mModels.size();
    }

    public void setModels(List<String> models){
        mModels = models;
    }

}
