package com.htlconline.sm.classmate.Batch.BatchFragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.ImageButton;

import com.htlconline.sm.classmate.Batch.Adapters.BatchAttendanceAdapter;
import com.htlconline.sm.classmate.Batch.Adapters.BatchMarksAdapter;
import com.htlconline.sm.classmate.Batch.BatchActivity;
import com.htlconline.sm.classmate.Batch.CircleView;
import com.htlconline.sm.classmate.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class BatchMarksFragment extends Fragment implements View.OnClickListener{


    private CheckedTextView mToolbarToggle;
    private CircleView circleView1;
    private BatchMarksAdapter batchmarksAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager manager;
    private ImageButton imageButton1;
    private ImageButton imageButton2;

    public BatchMarksFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("views","onActivityCreated");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(BatchActivity.title);
        mToolbarToggle = (CheckedTextView) (getActivity()).findViewById(R.id.toolbar_toggle);
        mToolbarToggle.setVisibility(View.GONE);

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        circleView1 = (CircleView) view.findViewById(R.id.circle_1);
        imageButton1= (ImageButton) view.findViewById(R.id.batch_attendance_button_left);
        imageButton2 = (ImageButton) view.findViewById(R.id.dbatch_attendance_button_right);
        recyclerView = (RecyclerView) view.findViewById(R.id.batch_marks);
        circleView1.setOnClickListener(BatchMarksFragment.this);
        batchmarksAdapter = new BatchMarksAdapter(getActivity());
        recyclerView.setAdapter(batchmarksAdapter);
        manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
    }


    @Override
    public void onClick(View view) {

    }
}
