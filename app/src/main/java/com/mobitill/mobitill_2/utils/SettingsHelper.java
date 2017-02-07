package com.mobitill.mobitill_2.utils;

import android.util.Log;

import org.apache.commons.lang3.math.NumberUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by james on 10/31/2016.
 */

public class SettingsHelper {

    private static final String TAG = SettingsHelper.class.getSimpleName();

    public ArrayList<String> getModels(String settings){
        ArrayList<String> modelsList = new ArrayList<>();
        try {
            if(settings!=null){
                JSONObject settingsObject = new JSONObject(settings);
                if(settingsObject.has("models")){
                    JSONObject models = settingsObject.getJSONObject("models");
                    Iterator<String> iterator = models.keys();
                    while(iterator.hasNext()){
                        modelsList.add(iterator.next());
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();

        }
        return modelsList;
    }

    public String getFetchPayload(String action, String appid){
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

    public String getInventoryPayload(String appid){
        String payload = null;


        if(appid!=null){
            JSONObject params = new JSONObject();
            JSONObject appIdObject = new JSONObject();
            try {
                appIdObject.put("appid", appid);
                params.put("params", appIdObject);
                payload = params.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return payload;
    }

    public String getInsertPayLoad(HashMap<String, String> params, String appId) throws JSONException {
        String payload = null;

        JSONObject fieldsObject = new JSONObject();
        for(HashMap.Entry<String, String> entry : params.entrySet()) {
            Log.i(TAG, "getInsertPayLoad: " + entry.getKey() + " : " + entry.getValue());
            fieldsObject.put(entry.getKey(), entry.getValue());
        }

        fieldsObject.put("appid", appId);
        JSONObject paramsObject = new JSONObject();
        paramsObject.put("params", fieldsObject);
        payload = paramsObject.toString();
        Log.i(TAG, "getInsertPayLoad: " + payload);

        return payload;
    }

    public String getUpdatePayLoad(HashMap<String, String> params, String appId, String itemid) throws JSONException {
        String payload = null;

        JSONObject fieldsObject = new JSONObject();
        for(HashMap.Entry<String, String> entry : params.entrySet()) {
            Log.i(TAG, "getInsertPayLoad: " + entry.getKey() + " : " + entry.getValue());
            fieldsObject.put(entry.getKey(), entry.getValue());
        }

        fieldsObject.put("appid", appId);
        fieldsObject.put("id", itemid);
        JSONObject paramsObject = new JSONObject();
        paramsObject.put("params", fieldsObject);
        payload = paramsObject.toString();
        Log.i(TAG, "getInsertPayLoad: " + payload);

        return payload;
    }

    public String getDeletePayload(HashMap<String, String> params, String appId){
        String payload = null;

        JSONObject paramsObject = new JSONObject();
        try {
            paramsObject.put("appid", appId);
            paramsObject.put("itemid", params.get("id"));

            JSONObject parentObject = new JSONObject();
            parentObject.put("params", paramsObject);

            payload = parentObject.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return payload;
    }

    public String getReportsPayload(String appId, List<Long> range, HashMap<String, String> filterItems){
        String payload = null;

        JSONObject paramsObject = new JSONObject();
        JSONObject fetchObject = new JSONObject();
        JSONObject items = new JSONObject();

        try {
            items.put("range", new JSONArray(range));
            items.put("appid", appId);

            for(HashMap.Entry<String, String> entry: filterItems.entrySet()){
                items.put(entry.getKey(), entry.getValue());
            }

            fetchObject.put("fetch", items);
            paramsObject.put("params", fetchObject);



            payload = paramsObject.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return payload;
    }

    public List<String> getReportFilterItems(String settings){
        List<String> filterItems = new ArrayList<>();

        try {
            if(settings!=null){
                JSONObject settingsObject = new JSONObject(settings);
                JSONObject reports = settingsObject.getJSONObject("reports");
                JSONArray filterItemsArray = reports.getJSONArray("filterItems");
                for(int i = 0; i < filterItemsArray.length(); i++){
                    filterItems.add(filterItemsArray.getString(i));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return filterItems;
    }

    public List<HashMap<String, String>> getList(String data){
        List<HashMap<String, String>> list = new ArrayList<>();

        try {
            JSONObject dataObject = new JSONObject(data);
            if(dataObject.has("data")){
                JSONArray jsonArray = dataObject.getJSONArray("data");
                //loop through json array to create  a hashmap
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject item;
                    if(jsonArray.get(i) instanceof String){
                       item = new JSONObject(jsonArray.getString(i));
                    } else {
                        item = jsonArray.getJSONObject(i);
                    }
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
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

    public HashMap<String, String[]> getSchema(String settings, String model){
        HashMap<String, String[]> schema = new HashMap<>();
        try{

            JSONObject settingsObject = new JSONObject(settings);
            JSONObject modelsObject = settingsObject.getJSONObject("models");
            JSONObject modelObject = modelsObject.getJSONObject(model);
            JSONObject schemaObject = modelObject.getJSONObject("schema");

            Iterator<String> iterator = schemaObject.keys();

            while (iterator.hasNext()){
                String key = iterator.next();

                JSONArray jsonArray = schemaObject.getJSONArray(key);
                String[] values = new String[jsonArray.length()];

                for(int i = 0; i < values.length; i++){
                    values[i] = jsonArray.getString(i);
                }
                schema.put(key, values);
            }

        } catch (JSONException e){
            e.printStackTrace();
       }

        return schema;
    }



    public HashMap<String,String[]> getInventorySchema(String settings, String model) {
        HashMap<String, String[]> schema = new HashMap<>();

        try {
            JSONObject settingsObject = new JSONObject(settings);

            if(settingsObject.has("showInventory")){
                if(settingsObject.has(model)){
                    JSONObject inventoryObject = settingsObject.getJSONObject(model);
                    JSONObject schemaObject = inventoryObject.getJSONObject("schema");

                    Iterator<String> iterator = schemaObject.keys();

                    while (iterator.hasNext()){
                        String key = iterator.next();

                        JSONArray jsonArray = schemaObject.getJSONArray(key);
                        String[] values = new String[jsonArray.length()];

                        for(int i = 0; i < values.length; i++){
                            values[i] = jsonArray.getString(i);
                        }
                        schema.put(key, values);
                    }

                } else {
                    String[] values = new String[1];
                    values[0] = "number";
                    schema.put("identifier", values);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return schema;
    }

    public boolean isInventory(String settings){
        boolean isInventory = false;
        try {
            if(settings!=null){
                JSONObject jsonObject = new JSONObject(settings);
                if(jsonObject.has("showInventory")){
                    isInventory = jsonObject.getBoolean("showInventory");
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  isInventory;
    }

    public boolean isDemo(String settings){
        boolean isDemo = false;

        if(settings!=null){
            try {
                JSONObject jsonObject = new JSONObject(settings);
                if(jsonObject.has("demo")){
                    isDemo = jsonObject.getBoolean("demo");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return isDemo;
    }


    public String getInsertInventoryPayload(String appId, String productId, HashMap<String, String> createData) {
        String payload = null;

        JSONObject paramsObject = new JSONObject();
        JSONObject parentObject = new JSONObject();

        try {
            paramsObject.put("appid", appId);
            paramsObject.put("productid", productId);

            for(HashMap.Entry<String, String> entry: createData.entrySet()){
                if(!entry.getKey().equalsIgnoreCase("identifier")){
                    // check value is int if it is convert it
                    if(NumberUtils.isNumber(entry.getValue())){
                        paramsObject.put(entry.getKey(), Integer.parseInt(entry.getValue()));
                    } else {
                        paramsObject.put(entry.getKey(), entry.getValue());
                    }
                }
            }

            parentObject.put("params", paramsObject);
            payload = parentObject.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return payload;
    }

}
