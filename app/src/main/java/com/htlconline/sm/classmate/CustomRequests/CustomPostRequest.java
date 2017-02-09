package com.htlconline.sm.classmate.CustomRequests;

import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Shikhar Garg on 23-12-2016.
 */
public class CustomPostRequest extends Request<JSONObject> {

    private Response.Listener<JSONObject> listener;
    private Map<String, String> params;

    public CustomPostRequest(String url, Response.ErrorListener listener, Response.Listener<JSONObject> listener1, Map<String, String> params) {
        super(url, listener);
        this.listener = listener1;
        this.params = params;
    }

    public CustomPostRequest(int method, String url, Map<String, String> params, Response.Listener<JSONObject> listener1, Response.ErrorListener listener) {
        super(method, url, listener);
        this.listener = listener1;
        this.params = params;
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONObject(jsonString), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        } catch (JSONException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }

    @Override
    public Map getHeaders() {
        HashMap<String, String> headers = new HashMap<String, String>();
//         System.out.println("Config.AUTH_TOKEN"+Config.AUTH_TOKEN);
//        if (IS_LOGIN_REQUEST) {
        Log.i("Login_checkingk",params.get("username") + " " + params.get("password"));
        headers.put("Authorization", "Basic " + Base64.encodeToString(new String(params.get("username") + ":" + params.get("password")).getBytes(), 0));
//        }
        return headers;
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        listener.onResponse(response);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Log.i("Login_checking_CPR", "get params() called ");
        return params;
    }
}
