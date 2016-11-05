package com.mobitill.mobitill_2.utils;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by james on 11/4/2016.
 */

public class UIHelper {

    public static final String TAG = UIHelper.class.getSimpleName();

    public LinearLayout getView(Context context, HashMap<String, String[]> schema){

        LinearLayout linearLayout = new LinearLayout(context);

        for(HashMap.Entry<String, String[]> entry : schema.entrySet()){
            Log.i(TAG, "generateUI: " + entry.getKey() + " : " + Arrays.toString(entry.getValue()));
        }

        return linearLayout;

    }

}
