package com.mobitill.mobitill_2.components.showall;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.HashMap;

/**
 * Created by james on 11/2/2016.
 */

public class ShowAllHolder extends RecyclerView.ViewHolder {

    public static final String TAG = ShowAllHolder.class.getSimpleName();

    public ShowAllHolder(View itemView) {
        super(itemView);
    }

    void bindItem(HashMap<String, String> item){
        Log.i(TAG, "bindItem: "  + item.toString());
    }
}
