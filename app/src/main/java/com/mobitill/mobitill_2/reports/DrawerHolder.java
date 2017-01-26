package com.mobitill.mobitill_2.reports;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mobitill.mobitill_2.R;

import org.apache.commons.lang3.text.WordUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by james on 11/9/2016.
 */

public class DrawerHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.model) TextView mTextView;


    public DrawerHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setClickable(true);
        itemView.setFocusable(true);
    }

    public void bindView(String model){
        mTextView.setText(WordUtils.capitalizeFully(model));
    }
}
