package com.mobitill.mobitill_2.utils;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by james on 10/31/2016.
 */

public class SettingsHelper {

    public ArrayList<String> getModels(String settings){
        ArrayList<String> modelsList = new ArrayList<>();
        try {
            JSONObject settingsObject = new JSONObject(settings);
            if(settingsObject.has("models")){
                JSONObject models = settingsObject.getJSONObject("models");
                Iterator<String> iterator = models.keys();
                while(iterator.hasNext()){
                    modelsList.add(iterator.next());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();

        }
        return modelsList;
    }

}
