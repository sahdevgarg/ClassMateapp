package com.htlconline.sm.classmate.CustomRequests;

import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.htlconline.sm.classmate.SessionManagement;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Shikhar Garg on 02-02-2017.
 */
public class CustomStringGetRequest extends Request<String> {

    private Response.Listener<String> listener;
    private SessionManagement session;
    public CustomStringGetRequest(int method, String url, Response.Listener<String> listener1, Response.ErrorListener listener,Context context) {
        super(method, url, listener);
        this.listener = listener1;
        session=new SessionManagement(context);
    }
    @Override
    public Map getHeaders() {
        HashMap<String, String> headers = new HashMap<String, String>();
        Log.i("customgetcookie", session.get_cookie());
        headers.put("Cookie", session.get_cookie());
        return headers;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String jsonString = null;
        try {
            jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return Response.success(new String(jsonString), HttpHeaderParser.parseCacheHeaders(response));

    }

    @Override
    protected void deliverResponse(String response) {
        listener.onResponse(response);
    }
}
