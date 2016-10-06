package com.mobitill.mobitill_2.cashiers;

import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.TextView;

import com.mobitill.mobitill_2.R;
import com.mobitill.mobitill_2.data.models.cashiers.models.Cashier;

import butterknife.BindView;
import butterknife.ButterKnife;

// setup recyclerview adapter and holder
class CashierHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.cashier)
    TextView mCashierTextView;
    Cashier mCashier;


    public CashierHolder(View itemView) {
        super(itemView);
        itemView.setClickable(true);
        itemView.setFocusable(true);
        mCashier = new Cashier();
        ButterKnife.bind(this, itemView);
    }

    public void bindCashierName(Cashier cashier){
        mCashierTextView.setText(cashier.getName());
        mCashier = cashier;
    }
}