package com.htlconline.sm.classmate.Batch.BatchFragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.htlconline.sm.classmate.Batch.BatchActivity;
import com.htlconline.sm.classmate.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class BatchMarksFragment extends Fragment {


    private CheckedTextView mToolbarToggle;

    public BatchMarksFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("views","onActivityCreated");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(BatchActivity.title);
        mToolbarToggle = (CheckedTextView) (getActivity()).findViewById(R.id.toolbar_toggle);
        mToolbarToggle.setVisibility(View.INVISIBLE);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("views","onCreateView");
        return inflater.inflate(R.layout.fragment_batch_marks, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("views","onCreate");

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("views","onResume");


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("views","onAttach");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("views","onDetach");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("views","onViewCreated");


    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.d("views","onViewStateRestored");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("views","onsave instance");
    }
}
