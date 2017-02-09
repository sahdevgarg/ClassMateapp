package com.htlconline.sm.classmate.handler;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.Fragment;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.htlconline.sm.classmate.CustomRequests.CustomPostRequest;
import com.htlconline.sm.classmate.LoginModule.Login;
import com.htlconline.sm.classmate.LoginModule.LoginResponse;
import com.htlconline.sm.classmate.interfaces.DataHandlerCallBack;

import org.json.JSONObject;

import java.net.Authenticator;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Shikhar Garg on 23-12-2016.
 */
public class UserActivityHandler {
    Activity mActivity;
    DataHandlerCallBack mDataHandlerCallBack;
    private HashMap<String, Object> map;

    public UserActivityHandler(Activity activity, DataHandlerCallBack dataHandlerCallback) {
        mDataHandlerCallBack = (DataHandlerCallBack) dataHandlerCallback;
        mActivity = activity;
    }

    public UserActivityHandler(Fragment fragment, Activity activity) {
        mDataHandlerCallBack = (DataHandlerCallBack) fragment;
        mActivity = activity;
    }

    public UserActivityHandler(DataHandlerCallBack dataHandlerCallback) {
        mDataHandlerCallBack = dataHandlerCallback;
    }

    public void postUserLogin(String url, HashMap<String,String> params){
        RequestQueue mRequestQueue= Volley.newRequestQueue(mActivity);
        CustomPostRequest mCustomPostRequest=new CustomPostRequest(Request.Method.POST, url, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("Login_checking_UAH","response is true");
                        Gson gson=new Gson();
                        Log.i("Login_checking_UAH","gson_initialised");
                        LoginResponse loginResponse=gson.fromJson(response.toString(),LoginResponse.class);
                        Log.i("Login_checking_UAH","loginresponse object created");

                        map.put("LoginResponse", loginResponse);
                        Log.i("Login_checking_UAH", "data is map<String,Object>");
                        mDataHandlerCallBack.onSuccess(map);
                        Log.i("Login_checking_UAH", "called succesfully");
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        error.printStackTrace();
                    }
                });
        mRequestQueue.add(mCustomPostRequest);
    }
}
