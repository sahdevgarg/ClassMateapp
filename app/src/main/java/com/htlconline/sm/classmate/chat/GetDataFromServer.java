package com.htlconline.sm.classmate.chat;

import android.app.Activity;
import android.content.res.AssetManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class GetDataFromServer {


	public static String HTTP_POST = "POST";
	public static String HTTP_GET = "GET";
	public static String HTTP_PUT = "PUT";

	private static String getDataFromServerThroughPost(String url , JSONObject json, String accessToken) throws Exception {
		System.out.println("---Url---" + url);
		String response = null;
		DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(defaultHttpClient.getParams(), 10000);
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Content-type", "application/json");
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("charset", "utf-8");
		if(accessToken != null && !accessToken.equalsIgnoreCase(""))
			httpPost.setHeader("Access-Key", accessToken);
		StringEntity se = new StringEntity(json.toString());
		se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,"application/json"));

		httpPost.setEntity(se);
		System.out.println("----------Deepak-----"+json.toString());
		HttpResponse httpResponse = defaultHttpClient.execute(httpPost);
		int responseCode = httpResponse.getStatusLine().getStatusCode();
//		System.out.println("-----------------"+responseCode);
		HttpEntity entity = httpResponse.getEntity();
		InputStream inputStream = entity.getContent();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		StringBuilder stringBuilder = new StringBuilder();
		String string = bufferedReader.readLine();
		while (string != null) {
			stringBuilder.append(string+"\n");
			string = bufferedReader.readLine();

		}
		response = stringBuilder.toString();

		inputStream.close();
		inputStreamReader.close();
		bufferedReader.close();
		return response;
	}

	private static String getDataFromServerThroughPut(String url , JSONObject json, String accessToken) throws Exception {
		System.out.println("---Url---" + url);
		String response = null;
		DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(defaultHttpClient.getParams(), 10000);
		HttpPut httpPost = new HttpPut(url);
		httpPost.setHeader("Content-type", "application/json");
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("charset", "utf-8");
		if(accessToken != null && !accessToken.equalsIgnoreCase(""))
			httpPost.setHeader("Access-Key", accessToken);
		StringEntity se = new StringEntity(json.toString());
		se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,"application/json"));

		httpPost.setEntity(se);
		System.out.println("----------Deepak-----"+json.toString());
		HttpResponse httpResponse = defaultHttpClient.execute(httpPost);
		int responseCode = httpResponse.getStatusLine().getStatusCode();
//		System.out.println("-----------------"+responseCode);
		HttpEntity entity = httpResponse.getEntity();
		InputStream inputStream = entity.getContent();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		StringBuilder stringBuilder = new StringBuilder();
		String string = bufferedReader.readLine();
		while (string != null) {
			stringBuilder.append(string+"\n");
			string = bufferedReader.readLine();

		}
		response = stringBuilder.toString();

		inputStream.close();
		inputStreamReader.close();
		bufferedReader.close();
		return response;
	}


	private static String getDataFromServerThroughGet(String url,String accessToken) throws Exception {
		String response = null;
		DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		if(accessToken != null && !accessToken.equalsIgnoreCase(""))
			httpGet.setHeader("Access-Key", accessToken);
		HttpResponse httpResponse = defaultHttpClient.execute(httpGet);
		System.out.println("--------response code get-----------" + httpResponse.getStatusLine().getStatusCode());
		HttpEntity entity = httpResponse.getEntity();
		InputStream inputStream = entity.getContent();//getStreamFromServer(url);//
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		StringBuilder stringBuilder = new StringBuilder();
		String string = bufferedReader.readLine();
		while (string != null) {
			stringBuilder.append(string+"\n");
			string = bufferedReader.readLine();

		}
		response = stringBuilder.toString();
		inputStream.close();
		inputStreamReader.close();
		bufferedReader.close();
		return response;
	}

	private static InputStream getImageStreamFromServer(String url) throws Exception {
		InputStream response = null;
//	System.out.println("----url--------"+url);
		DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		HttpResponse httpResponse = defaultHttpClient.execute(httpGet);
		HttpEntity entity = httpResponse.getEntity();
		response = entity.getContent();
//		inputStream.close();
		return response;
	}




	public static String getDataFromServer(String requestType,String url , JSONObject json, String accessToken) throws Exception {
		String response = null;
		if(requestType.equalsIgnoreCase(HTTP_GET)){
			response = getDataFromServerThroughGet(url,accessToken);
		}else if(requestType.equalsIgnoreCase(HTTP_POST)){
			response = getDataFromServerThroughPost(url,json,accessToken);
		}else if(requestType.equalsIgnoreCase(HTTP_PUT)){
			response = getDataFromServerThroughPut(url,json,accessToken);
		}
		return response;

	}


	public static String readFile(Activity context,String fileName){
		String response = "";
		try{
			AssetManager assetManager = context.getResources().getAssets();//new FileInputStream(new File("file:///android_asset/"+fileName));//
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

}
