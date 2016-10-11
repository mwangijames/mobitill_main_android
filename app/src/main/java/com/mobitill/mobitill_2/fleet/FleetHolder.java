package com.mobitill.mobitill_2.fleet;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mobitill.mobitill_2.R;
import com.mobitill.mobitill_2.data.models.fleet.models.FleetItem;

import butterknife.BindView;
import butterknife.ButterKnife;

// RecyclerView ViewHolder and Adapter
class FleetHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    @BindView(R.id.fleet)
    TextView mFleetTextView;

    public FleetHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    public void bindFleetItem(FleetItem fleet){
        mFleetTextView.setText(fleet.getFleetno());
    }

    @Override
    public void onClick(View v) {

    }
}