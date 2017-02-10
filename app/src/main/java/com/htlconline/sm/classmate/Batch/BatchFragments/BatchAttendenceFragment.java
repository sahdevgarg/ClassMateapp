package com.htlconline.sm.classmate.Batch.BatchFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.htlconline.sm.classmate.Batch.Adapters.BatchAttendanceAdapter;
import com.htlconline.sm.classmate.Batch.BatchActivity;
import com.htlconline.sm.classmate.Batch.CircleView;
import com.htlconline.sm.classmate.R;


public class BatchAttendenceFragment extends Fragment implements View.OnClickListener {


    private CircleView circleView1, circleView2, circleView3, circleView4;
    private int selection;
    private int current_index = 0;
    private ImageButton imageButton1,imageButton2;
    private static final String Color_Type_1 = "#039be5";
    private static final String Color_Type_2 = "#DD3333";
    private static final String Color_Type_3 = "#11e578";
    private static final String Color_Type_4 = "#FFA500";
    private static final String Color_Type_5 = "#ffccbd";


    private static final String CLASS_TYPE_1 = "Regular Class";
    private static final String CLASS_TYPE_2 = "Assessment Class";
    private static final String CLASS_TYPE_3 = "Extra Class";
    private static final String CLASS_TYPE_4 = "Doubt Class";
    private static final String CLASS_TYPE_5 = "Holiday";

    private RecyclerView recyclerView;
    private LinearLayoutManager manager;
    private BatchAttendanceAdapter batchAttendanceAdapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_batch_attendence, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        circleView1 = (CircleView) view.findViewById(R.id.circle_1);
        circleView2 = (CircleView) view.findViewById(R.id.circle_2);
        circleView3 = (CircleView) view.findViewById(R.id.circle_3);
        circleView4 = (CircleView) view.findViewById(R.id.circle_4);
        imageButton1= (ImageButton) view.findViewById(R.id.batch_attendance_button_left);
        imageButton2 = (ImageButton) view.findViewById(R.id.dbatch_attendance_button_right);
        recyclerView = (RecyclerView) view.findViewById(R.id.batch_attendance_summary);
        circleView1.setOnClickListener(BatchAttendenceFragment.this);
        circleView2.setOnClickListener(BatchAttendenceFragment.this);
        circleView3.setOnClickListener(BatchAttendenceFragment.this);
        circleView4.setOnClickListener(BatchAttendenceFragment.this);
        batchAttendanceAdapter = new BatchAttendanceAdapter(getActivity());
        recyclerView.setAdapter(batchAttendanceAdapter);
        manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(BatchActivity.title);
        CheckedTextView mToolbarToggle = (CheckedTextView) getActivity().findViewById(R.id.toolbar_toggle);
        mToolbarToggle.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View view) {
        if (view == circleView1) {
            selection = 1;
        } else if (view == circleView2) {
            selection = 2;

        } else if (view == circleView3) {
            selection = 3;

        } else if (view == circleView4) {
            selection = 4;

        }
        else  if(view == imageButton1)
        {
            current_index += 4;

        }
        else if(view == imageButton2)
        {
            current_index-= 4;
        }
    }
}
