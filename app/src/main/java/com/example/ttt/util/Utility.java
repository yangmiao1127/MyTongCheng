package com.example.ttt.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 1 on 2015/9/30.
 */
public class Utility {
    public static void handleEvent(Context context, String response) {
        try {
            JSONObject jsonObject=new JSONObject(response);
            String title=jsonObject.getString("title");
            Integer participant_count = jsonObject.getInt("participant_count");
            Integer wisher_count = jsonObject.getInt("wisher_count");
            String content=jsonObject.getString("content");
            String address=jsonObject.getString("address");
            String imageId=jsonObject.getString("image");
            String beginTime=jsonObject.getString("begin_time");
            String geo=jsonObject.getString("geo");
            saveInfo(context,title,participant_count,wisher_count,content,address,imageId,beginTime,geo);
            } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public static void saveInfo(Context context,String title,Integer participant_count,Integer wisher_count,String content,
                                String address,String imageId,String beginTime,String geo){
        SharedPreferences.Editor editor= PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.clear();
        editor.putString("title",title);
        editor.putInt("participant_count", participant_count);
        editor.putInt("wisher_count",wisher_count);
        editor.putString("content", content);
        editor.putString("address",address);
        editor.putString("imageId",imageId);
        editor.putString("beginTime",beginTime);
        editor.putString("geo",geo);
        Log.d("imageIdU",imageId);
        editor.commit();
    }
}