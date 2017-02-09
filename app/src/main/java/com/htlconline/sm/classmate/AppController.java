package com.htlconline.sm.classmate;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Shikhar Garg on 31-12-2016.
 */
public class AppController extends Application {

    private static AppController instance;
    private RequestQueue requestQueue;

    private AppController(Context context){
        requestQueue= Volley.newRequestQueue(context);
    }

    public static AppController getInstance(Context context){
        if(instance==null){
            instance=new AppController(context);
        }
        return instance;
    }
    public RequestQueue getRequestQueue(){

        return requestQueue;
    }
}
