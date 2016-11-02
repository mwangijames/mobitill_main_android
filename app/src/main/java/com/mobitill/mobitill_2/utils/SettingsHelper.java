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

    public String getPayload(String action, String appid){
        String payload = null;
        try {
            JSONObject params = new JSONObject();
            JSONObject actionObject = new JSONObject();
            actionObject.put("appid", appid);
            params.put(action, actionObject);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("params", params);
            payload = jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return payload;
    }

}
