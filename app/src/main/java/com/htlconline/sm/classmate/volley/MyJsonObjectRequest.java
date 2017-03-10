package com.htlconline.sm.classmate.volley;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class MyJsonObjectRequest extends JsonObjectRequest {

	public MyJsonObjectRequest(int method, String url, JSONObject jsonRequest,
							   Listener<JSONObject> listener, ErrorListener errorListener) {
		super(method, url, jsonRequest, listener, errorListener);
		// TODO Auto-generated constructor stub
	}

	public MyJsonObjectRequest(String url, JSONObject jsonRequest,
							   Listener<JSONObject> listener, ErrorListener errorListener) {
		super(url, jsonRequest, listener, errorListener);
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
		  try {
			  
	            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
	            return Response.success(new JSONObject(jsonString),MyHttpHeaderParser.parseIgnoreCacheHeaders(response));
//	            return Response.success(new JSONObject(jsonString),HttpHeaderParser.parseCacheHeaders(response));
	        } catch (UnsupportedEncodingException e) {
	            return Response.error(new ParseError(e));
	        } catch (JSONException je) {
	            return Response.error(new ParseError(je));
	        }
	}
	
//	 @Override
//	    protected Response<MyResponse> parseNetworkResponse(NetworkResponse response) {
//	        String jsonString = new String(response.data);
//	        MyResponse MyResponse = gson.fromJson(jsonString, MyResponse.class);
//	        return Response.success(MyResponse, HttpHeaderParser.parseIgnoreCacheHeaders(response));
//	    }

}
