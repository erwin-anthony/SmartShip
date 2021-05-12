package com.example.smartship.until.net.http;


import android.content.Context;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;

import org.json.JSONException;

import java.util.Map;



public class HttpHelp {


    com.example.smartship.until.net.http.I_success i_success;
    com.example.smartship.until.net.http.I_failure i_failure;
    Context context;
    String url;


    public HttpHelp(com.example.smartship.until.net.http.I_success i_success, com.example.smartship.until.net.http.I_failure i_failure, Context context, String url) {
        this.i_failure = i_failure;
        this.i_success = i_success;
        this.context = context;
        this.url = url;
    }

    //普通请求 get
    public void getHttp2( ) {

        OkGo.<String>get(url).tag(context).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                Logger.v("-----------HttpURL----" + url);

                Logger.v("-----------json-------" + response.body());

                String s = response.body();
                try {
                    i_success.doSuccess(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }






}

