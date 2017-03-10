//package com.htlconline.sm.classmate.handler;
//import android.app.Fragment;
//import android.app.ProgressDialog;
//import android.support.v7.app.AppCompatActivity;
//
//import com.android.volley.Request;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.google.gson.Gson;
//import com.htlconline.sm.classmate.Config;
//import com.htlconline.sm.classmate.CustomRequests.CustomPostRequest;
//import com.htlconline.sm.classmate.LoginModule.LoginResponse;
//import com.htlconline.sm.classmate.SessionManagement;
//import com.htlconline.sm.classmate.SplashScreen;
//import com.htlconline.sm.classmate.interfaces.DataHandlerCallBack;
//import org.json.JSONObject;
//
//import java.net.CookieHandler;
//import java.net.CookieManager;
//import java.util.HashMap;
//
///**
// * Created by Shikhar Garg on 23-12-2016.
// */
//public class UserActivityHandler {
//    AppCompatActivity mActivity;
//    DataHandlerCallBack mDataHandlerCallBack;
//    private HashMap<String, Object> map;
//    ProgressDialog dialog;
//    public UserActivityHandler(AppCompatActivity activity, DataHandlerCallBack dataHandlerCallback) {
//        mDataHandlerCallBack = (DataHandlerCallBack) dataHandlerCallback;
//        mActivity = activity;
//    }
//
//    public UserActivityHandler(Fragment fragment, AppCompatActivity activity) {
//        mDataHandlerCallBack = (DataHandlerCallBack) fragment;
//        mActivity = activity;
//    }
//
//    public UserActivityHandler(DataHandlerCallBack dataHandlerCallback) {
//        mDataHandlerCallBack = dataHandlerCallback;
//    }
//
//    public void postUserLogin(String url, HashMap<String,String> params){
//        final CookieManager manager = new CookieManager();
//        CookieHandler.setDefault(manager);
//
//        //RequestQueue mRequestQueue= Volley.newRequestQueue((Login) mDataHandlerCallBack);
//        CustomPostRequest mCustomPostRequest=new CustomPostRequest(Request.Method.POST, url, params,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        ///og.i("Cookies_handler:",manager.getCookieStore().getCookies().toString());
//                        new SessionManagement((SplashScreen)mDataHandlerCallBack).setcookie(manager.getCookieStore().getCookies().toString());
//                        //Log.i("json", response.toString());
//                        Gson gson=new Gson();
//                        map=new HashMap<String,Object>();
//                        LoginResponse loginResponse=gson.fromJson(response.toString(),LoginResponse.class);
//                        map.put("LoginResponse", loginResponse);
//                        dialog.dismiss();
//                        mDataHandlerCallBack.onSuccess(map);
//                    }
//                },
//                new Response.ErrorListener(){
//                    @Override
//                    public void onErrorResponse(VolleyError error){
//                        error.printStackTrace();
//                    }
//                }, Config.REQUEST_LOG_IN);
//        //mRequestQueue.add(mCustomPostRequest);
//
//        AppControllerOld.getInstance((SplashScreen) mDataHandlerCallBack).getRequestQueue().add(mCustomPostRequest);
//
//        dialog=new ProgressDialog((SplashScreen)mDataHandlerCallBack);
//        dialog.setMessage("Logging in..");
//        dialog.setTitle("Please wait");
//        dialog.setCancelable(true);
//        dialog.show();
//
//    }
//}
