package com.mobitill.mobitill_2.components.showall;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobitill.mobitill_2.R;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by james on 11/2/2016.
 */

public class ShowAllAdapter extends RecyclerView.Adapter<ShowAllHolder> {

    private List<HashMap<String, String>> mItems;
    private Context mContext;

    ShowAllAdapter(List<HashMap<String, String>> items,
                   Context context){
        mItems = items;
        mContext = context;
    }

    @Override
    public ShowAllHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.item_show_all, parent, false);
        return new ShowAllHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(ShowAllHolder holder, int position) {
        HashMap<String, String> item = mItems.get(position);

        //LinearLayout mLinearLayout = new LinearLayout(mContext);

        LinearLayout.LayoutParams linLayoutParams = new LinearLayout
                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        LinearLayout rootLayout = new LinearLayout(mContext);
        rootLayout.setOrientation(LinearLayout.VERTICAL);
        rootLayout.setLayoutParams(linLayoutParams);

        holder.mRootLayout.removeAllViews();
        for(HashMap.Entry<String, String> entry : item.entrySet()){

            String key = entry.getKey();
            String value = entry.getValue();

            LinearLayout linearLayout = new LinearLayout(mContext);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setLayoutParams(linLayoutParams);

            linearLayout.removeAllViews();

            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            TextView keyTextView = new TextView(mContext);
            keyTextView.invalidate();
            keyTextView.setText(entry.getKey());
            keyTextView.setLayoutParams(layoutParams);
            linearLayout.addView(keyTextView);

            TextView valueTextView = new TextView(mContext);
            valueTextView.invalidate();
            valueTextView.setText(entry.getValue());
            valueTextView.setLayoutParams(layoutParams);
            linearLayout.addView(valueTextView);

            rootLayout.addView(linearLayout);

        }


        holder.mRootLayout.addView(rootLayout);

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setItems(List<HashMap<String, String>> items){
        mItems = items;
        notifyDataSetChanged();
    }
}
