package com.mobitill.mobitill_2.components.showall;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.common.io.LineReader;
import com.mobitill.mobitill_2.R;

import java.util.HashMap;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by james on 11/2/2016.
 */

public class ShowAllHolder extends RecyclerView.ViewHolder {

    private static final String TAG = ShowAllHolder.class.getSimpleName();
    private Context mContext;
    public LinearLayout mRootLayout;

    public ShowAllHolder(View itemView, Context context) {
        super(itemView);
        mContext = context;
        mRootLayout = (LinearLayout) itemView.findViewById(R.id.root);
        itemView.setClickable(true);
        itemView.setFocusable(true);
    }

    void bindItem(HashMap<String, String> item, LinearLayout rootLayout){

        Log.i(TAG, "bindItem: "  + item.toString());

    }

}
