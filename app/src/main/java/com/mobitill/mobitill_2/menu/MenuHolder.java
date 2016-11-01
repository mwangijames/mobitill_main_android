package com.mobitill.mobitill_2.menu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mobitill.mobitill_2.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by james on 10/31/2016.
 */

public class MenuHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    @BindView(R.id.model) TextView mTextView;
    Context mContext;

    public MenuHolder(View itemView, Context context) {
        super(itemView);
        itemView.setOnClickListener(this);
        ButterKnife.bind(this, itemView);
        mContext = context;
    }

    void bindView(String model){
        mTextView.setText(model);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(mContext, "Open app detail view", Toast.LENGTH_SHORT).show();
    }
}
