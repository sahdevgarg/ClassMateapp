package com.htlconline.sm.classmate.interfaces;

import java.util.HashMap;

/**
 * Created by Shikhar Garg on 23-12-2016.
 */
public interface DataHandlerCallBack {
   void onSuccess(HashMap<String, Object> map);void onFailure(HashMap<String, Object> map);

}
