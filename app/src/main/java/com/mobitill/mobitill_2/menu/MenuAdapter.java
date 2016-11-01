package com.mobitill.mobitill_2.menu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobitill.mobitill_2.R;

import java.util.List;

/**
 * Created by james on 10/31/2016.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuHolder> {

    private static final String TAG = MenuAdapter.class.getSimpleName();
    private List<String> mModels;
    private Context mContext;

    MenuAdapter(List<String> models,
                Context context){
        mModels = models;
        mContext = context;
    }

    @Override
    public MenuHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.item_menu, parent, false);
        return new MenuHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(MenuHolder holder, int position) {
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
