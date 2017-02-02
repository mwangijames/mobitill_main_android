package com.mobitill.mobitill_2.utils;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;

/**
 * Created by james on 2/2/2017.
 */

public class LabelFormatter implements IAxisValueFormatter {

    private final ArrayList<String> mLabels;

    public LabelFormatter(ArrayList<String> labels){
        mLabels = labels;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return mLabels.get((int) value);
    }
}
