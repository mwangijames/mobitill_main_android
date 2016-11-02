package com.mobitill.mobitill_2.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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

    public List<HashMap<String, String>> getList(String data){

        List<HashMap<String, String>> list = new ArrayList<>();

        try {
            JSONObject dataObject = new JSONObject(data);
            JSONArray jsonArray = dataObject.getJSONArray("data");

            //loop through each json array to create  a hashmap
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject item = jsonArray.getJSONObject(i);

                //put the items in the hash map
                HashMap<String, String> itemMap = new HashMap<>();
                Iterator<String> iterator = item.keys();

                while(iterator.hasNext()){
                   String key = iterator.next();
                   String val = item.getString(key);
                    itemMap.put(key, val);
                }

                list.add(itemMap);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

}
