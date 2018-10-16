package com.burhanrashid52.imageeditor.utils;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by oldma on 2017/12/12.
 */

public class JsonUtil {

    public static<T> T getBean(JSONObject json,Class<T> t1){
        try {
            if(200 == json.getInt("resultCode")){
                Gson gson = new Gson();
                T t2 =  gson.fromJson(json.getString("data"),t1);
                return t2;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static<T> ArrayList<T> getList(JSONObject json, Class<T> t1){
        try {
            if(200 == json.getInt("resultCode")){
                Gson gson = new Gson();
                ArrayList<T> list = new ArrayList<>();
                JSONArray array = json.getJSONArray("data");
                for (int i = 0; i < array.length(); i++) {
                    T t = gson.fromJson(array.getString(i),t1);
                    list.add(t);
                }
                return list;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static boolean isOk(JSONObject jsonObject){
        try {
            if(200 == jsonObject.getInt("resultCode")){
                return true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

}
