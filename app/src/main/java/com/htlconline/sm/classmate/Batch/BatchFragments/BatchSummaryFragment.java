package com.htlconline.sm.classmate.Batch.BatchFragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.htlconline.sm.classmate.Batch.BatchActivity;
import com.htlconline.sm.classmate.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BatchSummaryFragment extends Fragment {


    private CheckedTextView mToolbarToggle;

    public BatchSummaryFragment() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(BatchActivity.title);
        mToolbarToggle = (CheckedTextView) getActivity().findViewById(R.id.toolbar_toggle);
        mToolbarToggle.setVisibility(View.INVISIBLE);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_batch_summary, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null)
        {
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(BatchActivity.title);
        }
    }
}
