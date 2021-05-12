package com.example.smartship.until.net.http;


import android.app.Activity;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;



public class GsonUtil {
    private static Gson gson;

    //是否合法json
    public static boolean isRightJson(Activity activity,String t){
        boolean isRight =false;
        try {
            JSONObject jo = new JSONObject(t);
            if (jo.getString("state").equals("true"))
                isRight =true;
            else
                isRight =false;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return isRight;
    }

    public static Gson getInstance(){
        if(null==gson){
            return new Gson();
        }
        return gson;
    }
}

