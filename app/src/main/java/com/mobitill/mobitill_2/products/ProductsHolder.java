package com.mobitill.mobitill_2.products;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mobitill.mobitill_2.R;
import com.mobitill.mobitill_2.data.models.products.models.Product;

import butterknife.BindView;
import butterknife.ButterKnife;

// RecyclerView adapter and holder
class ProductsHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.product)
    TextView mProductTextView;
    Product mProduct;

    public ProductsHolder(View itemView) {
        super(itemView);
        mProduct = new Product();
        ButterKnife.bind(this, itemView);
        itemView.setClickable(true);
        itemView.setFocusable(true);
    }

    public void bindProductName(Product product){
        mProductTextView.setText(product.getName());
    }


}