package com.htlconline.sm.classmate.Schedule.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.htlconline.sm.classmate.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MonthDetailFragment extends Fragment {


    public MonthDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_month_detail, container, false);
    }

}
