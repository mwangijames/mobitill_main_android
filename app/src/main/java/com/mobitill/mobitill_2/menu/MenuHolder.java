package com.mobitill.mobitill_2.menu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mobitill.mobitill_2.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by james on 10/31/2016.
 */

public class MenuHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.model) TextView mTextView;
    Context mContext;
    String mModel;

    public MenuHolder(View itemView, Context context) {
        super(itemView);
        itemView.setClickable(true);
        itemView.setFocusable(true);
        ButterKnife.bind(this, itemView);
        mContext = context;
    }

    void bindView(String model){
        mModel = model;
        mTextView.setText(model);
    }

}
