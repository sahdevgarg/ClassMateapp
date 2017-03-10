//package com.htlconline.sm.classmate;
//
//import android.app.Application;
//import android.content.Context;
//
//import com.android.volley.RequestQueue;
//import com.android.volley.toolbox.Volley;
//
///**
// * Created by Shikhar Garg on 31-12-2016.
// */
//public class AppControllerOld extends Application {
//
//    private static AppControllerOld instance;
//    private RequestQueue requestQueue;
//
//    private AppControllerOld(Context context){
//        requestQueue= Volley.newRequestQueue(context);
//    }
//
//    public static AppControllerOld getInstance(Context context){
//        if(instance==null){
//            instance=new AppControllerOld(context);
//        }
//        return instance;
//    }
//    public RequestQueue getRequestQueue(){
//
//        return requestQueue;
//    }
//}
