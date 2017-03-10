package com.htlconline.sm.classmate.volley;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.toolbox.HttpHeaderParser;

import java.util.Map;

public class MyHttpHeaderParser extends HttpHeaderParser {

	
	/**
	 * Extracts a {@link com.android.volley.Cache.Entry} from a {@link com.android.volley.NetworkResponse}.
	 * Cache-control headers are ignored. SoftTtl == 3 mins, ttl == 24 hours.
	 * @param response The network response to parse headers from
	 * @return a cache entry for the given response, or null if the response is not cacheable.
	 */
	public static Cache.Entry parseIgnoreCacheHeaders(NetworkResponse response) {
	    long now = System.currentTimeMillis();

	    Map<String, String> headers = response.headers;
	    long serverDate = 0;
	    String serverEtag = null;
	    String headerValue;

	    headerValue = headers.get("Date");
	    if (headerValue != null) {
	        serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
	    }

	    serverEtag = headers.get("ETag");

	    final long cacheHitButRefreshed = 1 *60* 1000; // in 10 minutes cache will be hit, but also refreshed on background
	    final long cacheExpired = 365*24 * 60 * 60 * 1000; // in One year this cache entry expires completely
	    final long softExpire = now + cacheHitButRefreshed;
	    final long ttl = now + cacheExpired;

	    Cache.Entry entry = new Cache.Entry();
	    entry.data = response.data;
	    entry.etag = serverEtag;
	    entry.softTtl = softExpire;
	    entry.ttl = ttl;
	    entry.serverDate = serverDate;
	    entry.responseHeaders = headers;
	    return entry;
	}
	
	
	
}
