package com.mobitill.mobitill_2.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mobitill.mobitill_2.R;
import com.mobitill.mobitill_2.data.models.products.models.Product;

/**
 * Created by james on 9/14/2016.
 */
public class ProductsAdapter extends ArrayAdapter<Product> {

    private Context mContext;
    private Product[] mProducts;

    public ProductsAdapter(Context context, int textViewResourceId,
                           Product[] products){
        super(context, textViewResourceId, products);
        mContext = context;
        mProducts = products;
    }

    public  int getCount(){
        return mProducts.length;
    }

    public Product getProduct(int position){
        return mProducts[position];
    }

    public long getProductPosition(int position){
        return position;
    }

    public String getProductId(int position){
        return mProducts[position].getId();
    }


    // this is for the passive state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // create a dynamic textview
        TextView label = new TextView(mContext);
        label.setPadding(30, 30, 30 ,30);
        label.setTextColor(mContext.getResources().getColor(R.color.colorTextLight));
        label.setText(mProducts[position].getName());
        return label;
    }

    // and here is when the chooser is popped up
    // normally is the same view, but you can customize it if you want

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(mContext);
       // label.getLayoutParams().height = mContext.getResources().getDimensionPixelSize(R.dimen.text_view_height);
        label.setPadding(30, 30, 30 ,30);
        label.setTextColor(Color.BLACK);
        label.setText(mProducts[position].getName());
        return label;
    }
}
