package com.mobitill.mobitill_2.utils;

import android.content.res.Resources;

/**
 * Created by james on 11/8/2016.
 */

public class DpPixelsConversion {

    public static int pxToDp(int px)
    {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static int dpToPx(int dp)
    {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

}
