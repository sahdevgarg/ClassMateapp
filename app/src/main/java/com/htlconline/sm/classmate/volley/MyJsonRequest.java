package com.htlconline.sm.classmate.volley;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Cache.Entry;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.htlconline.sm.classmate.AppController;
import com.htlconline.sm.classmate.Config;
import com.htlconline.sm.classmate.R;
import com.htlconline.sm.classmate.SessionManagement;
import com.htlconline.sm.classmate.SplashScreen;
import com.htlconline.sm.classmate.utils.ConnectionDetector;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class MyJsonRequest {


	private final SessionManagement session;
	OnServerResponse onServerResponse;
	ProgressDialog mProgressDialog;
	Context context;
	public MyJsonRequest(Context context, OnServerResponse onServerResponse) {
		this.context = context;
		this.onServerResponse = onServerResponse;
		session = new SessionManagement(context);
	}
	
	
	private void showProgressDialog() {
		mProgressDialog = new ProgressDialog(context);
		mProgressDialog.setMessage(context.getString(R.string.pleaseWait));
		mProgressDialog.setIndeterminate(false);
		mProgressDialog.setCancelable(false);
		mProgressDialog.show();
	}
	
	private void dissmisProgressDialog() {
		if(mProgressDialog != null){
			mProgressDialog.dismiss();
		}
	}



    public void getJsonFromLocal(final String tag, final String url){
        try{
        if(onServerResponse != null && onServerResponse instanceof OnServerResponse){
            onServerResponse.getJsonFromServer(true, url,new JSONObject(readFile(url)), null);
        }
        }
        catch (Exception e){
            System.out.println("---Ec--"+e);
        }
    }




	public void getJsonFromServer(final String tag, final String url, final boolean isProgressDialog, final boolean isCacheEnable){
		Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Entry entryForCheck = cache.get(url);
        if(entryForCheck != null && ConnectionDetector.isConnectingToInternet(context) && !tag.trim().equalsIgnoreCase("BookmarkFragment")){
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(entryForCheck.softTtl);
			System.out.println("---entryForCheck.softTtl--"+calendar.getTime());
            if(entryForCheck.softTtl< System.currentTimeMillis() || !isCacheEnable){
                cache.remove(url);
            }
        }

		Entry entry = cache.get(url);
		if (entry != null) {
			// fetch the data from cache
			Map<String, String> map = entry.responseHeaders;
			System.out.println("-----map--->"+url);
			try {
				String data = new String(entry.data, "UTF-8");
				try {
                    if(isProgressDialog)
					dissmisProgressDialog();
					if(onServerResponse != null && onServerResponse instanceof OnServerResponse){
						onServerResponse.getJsonFromServer(true, tag,new JSONObject(data), null);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}


		} else {
			// making fresh volley request and getting json
			MyJsonObjectRequest jsonReq = new MyJsonObjectRequest(Method.GET,
					url, null, new Response.Listener<JSONObject>() {
						@Override
						public void onResponse(JSONObject response) {
							VolleyLog.d(tag, "Response: " + response.toString());
                            if(isProgressDialog)
							dissmisProgressDialog();
							if (response != null) {
								if(onServerResponse != null && onServerResponse instanceof OnServerResponse){
									onServerResponse.getJsonFromServer(true,tag, response, null);
								}
							}

						}
					}, new Response.ErrorListener() {

						@Override
						public void onErrorResponse(VolleyError error) {
							VolleyLog.d(tag, "Error: " + error.getMessage());
							dissmisProgressDialog();
							if(onServerResponse != null && onServerResponse instanceof OnServerResponse){
								String strError = "";
								if(error.getMessage()==null){
									strError = Config.SERVER_NOT_RESPOND;
								}else{
									strError = Config.NETWORK_NOT_AVAILABLE;
								}
								onServerResponse.getJsonFromServer(false,tag, "", strError);
							}

						}

					})
			{
				@Override
				public Map<String, String> getHeaders() throws AuthFailureError {
					//return super.getHeaders();

					Map<String, String> params = new HashMap<>();
					//params.put("Authorization", "Token " + token);
					params.put("Cookie",session.get_cookie());
					return params;

				}

			};

			// Adding request to volley request queue
			AppController.getInstance().addToRequestQueue(jsonReq);
            if(isProgressDialog)
			showProgressDialog();
		}

	}

	public void postRequest(final String tag, final String url, final boolean isProgressDialog, final Map<String,String> params, final String token)
	{
		final CookieManager manager = new CookieManager();
		CookieHandler.setDefault(manager);

		StringRequest sr = new StringRequest(Request.Method.POST, url , new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				VolleyLog.d(tag, "Response: " + response.toString());
				new SessionManagement(context).setcookie(manager.getCookieStore().getCookies().toString());
				if(isProgressDialog)
					dissmisProgressDialog();
				if (response != null) {
					if(onServerResponse != null && onServerResponse instanceof OnServerResponse){
						onServerResponse.getJsonFromServer(true,tag, response, null);
					}
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Log.d("Error", ""+error.getMessage()+","+error.toString());
				if(onServerResponse != null && onServerResponse instanceof OnServerResponse){
					String strError = "";
					if(error.getMessage()==null){
						strError = Config.SERVER_NOT_RESPOND;
					}else{
						strError = Config.NETWORK_NOT_AVAILABLE;
					}
					onServerResponse.getJsonFromServer(false,tag, "", strError);
				}
			}
		}){
			@Override
			protected Map<String,String> getParams(){
				return params;
			}


			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				if(!getUrl().contains("api/users/login/")){
					Map<String,String> params = new HashMap<>();
					params.put("Authorization","Token "+token);
					return params;
				}
				return super.getHeaders();
			}
		};


		AppController.getInstance().addToRequestQueue(sr);
	}


	private String readFile(String fileName){
		String response = "";
		try{
		AssetManager assetManager = context.getAssets();
		InputStream inputStream = assetManager.open(fileName);
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		StringBuilder stringBuilder = new StringBuilder();
		String string = bufferedReader.readLine();
		while (string != null) {
			stringBuilder.append(string+"\n");
			string = bufferedReader.readLine();
			
		}
		 response = stringBuilder.toString();
		}catch(Exception e){
			System.out.println("-------execption------>"+e);
		}
		System.out.println("------response---->"+response);
		return response;
	}
	
	public interface OnServerResponse{
		public void getJsonFromServer(boolean flag, String tag, JSONObject jsonObject, String error);
		public void getJsonFromServer(boolean flag, String tag, String stringObject, String error);
	}


}
