package com.mobitill.mobitill_2.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mobitill.mobitill_2.data.models.cashiers.models.Cashier;

import org.w3c.dom.Text;

/**
 * Created by james on 9/16/2016.
 */
public class CashiersAdapter extends ArrayAdapter<Cashier> {

    private Context mContext;
    private Cashier[] mCashiers;

    public CashiersAdapter(Context context, int textViewResource,
                           Cashier[] cashiers){
        super(context, textViewResource, cashiers);
        mContext  = context;
        mCashiers = cashiers;
    }

    public int getCount(){
        return mCashiers.length;
    }

    public Cashier getCashier(int position){
        return mCashiers[position];
    }

    public long getCashiersPosition(int position){
        return position;
    }

    public String getCashiersId(int position){
        return mCashiers[position].getId();
    }

    // this is for th passive state of the spinner

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // create a dynamic text view
        TextView label = new TextView(mContext);
        label.setPadding(30, 30, 30 ,30);
        label.setTextColor(Color.BLACK);
        label.setText(mCashiers[position].getName());
        return label;
    }

    // and this is where the chooser is popped up
    // normally this is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(mContext);
        label.setPadding(30, 30, 30 ,30);
        label.setTextColor(Color.BLACK);
        label.setText(mCashiers[position].getName());
        return label;
    }

}

