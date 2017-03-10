package com.htlconline.sm.classmate;

import android.app.Application;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.htlconline.sm.classmate.volley.ImageLoader;
import com.htlconline.sm.classmate.volley.LruBitmapCache;
import com.htlconline.sm.classmate.volley.Volley;


public class AppController extends Application {

	public static final String TAG = AppController.class.getSimpleName();
	//private Tracker mTracker;

	private RequestQueue mRequestQueue;
	private ImageLoader mImageLoader;
	LruBitmapCache mLruBitmapCache;

	private static AppController mInstance;

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		MultiDex.install(this);

		// comscore tags
		/*comScore.setAppContext(this.getApplicationContext());
		comScore.setCustomerC2("6035286");
		comScore.setPublisherSecret("3695b3821f55beb7d505a066dc7f391d");
		comScore.enableAutoUpdate(120, false);
*/

	}

	public static synchronized AppController getInstance() {
		return mInstance;
	}

	public RequestQueue getRequestQueue() {
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		}

		return mRequestQueue;
	}

	public ImageLoader getImageLoader() {
		getRequestQueue();
		if (mImageLoader == null) {
			getLruBitmapCache();
			mImageLoader = new ImageLoader(this.mRequestQueue, mLruBitmapCache);//new ImageLoader(this.mRequestQueue, mLruBitmapCache);
		}

		return this.mImageLoader;
	}

	public LruBitmapCache getLruBitmapCache() {
		if (mLruBitmapCache == null)
			mLruBitmapCache = new LruBitmapCache();
		return this.mLruBitmapCache;
	}

	public <T> void addToRequestQueue(Request<T> req, String tag) {
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
		getRequestQueue().add(req);
	}

	public <T> void addToRequestQueue(Request<T> req) {
		req.setTag(TAG);
		getRequestQueue().add(req);
	}

	public void cancelPendingRequests(Object tag) {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}

	/**
	 * Gets the default {@link Tracker} for this {@link Application}.
	 * @return tracker
	 */
	/*synchronized public Tracker getDefaultTracker() {
		if (mTracker == null) {
			GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
			// To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
			mTracker = analytics.newTracker(R.xml.global_tracker);
		}
		return mTracker;
	}
*/
}
