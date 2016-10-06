package com.mobitill.mobitill_2.utils;

/**
 * Created by james on 10/5/2016.
 */

import android.view.View;

/**
 * Created by SONU on 15/03/16.
 */
public interface RecyclerClickListener {

    /**
     * Interface for Recycler View Click listener
     **/

    void onClick(View view, int position);

    void onLongClick(View view, int position);
}