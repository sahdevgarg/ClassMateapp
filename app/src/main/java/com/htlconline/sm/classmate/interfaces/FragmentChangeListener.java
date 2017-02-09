package com.htlconline.sm.classmate.interfaces;

import android.support.v4.app.FragmentManager;

/**
 * Created by Shikhar Garg on 05-01-2017.
 */
public interface FragmentChangeListener  {
    void OnSwitchToNextFragment(FragmentManager fm);
    void OnSwitchToNextFragment(int id);
}
