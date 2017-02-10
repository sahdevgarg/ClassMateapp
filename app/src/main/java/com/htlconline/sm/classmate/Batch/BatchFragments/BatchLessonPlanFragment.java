package com.htlconline.sm.classmate.Batch.BatchFragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.htlconline.sm.classmate.Batch.Adapters.BatchFilterListAdapter;
import com.htlconline.sm.classmate.Batch.Adapters.BatchLessonPlanAdapter;
import com.htlconline.sm.classmate.Batch.Adapters.BatchLessonPlanCustomListAdapter;
import com.htlconline.sm.classmate.Batch.BatchActivity;
import com.htlconline.sm.classmate.Batch.Data.BatchFilterData;
import com.htlconline.sm.classmate.Batch.Data.BatchLessonPlanData;
import com.htlconline.sm.classmate.R;
import com.htlconline.sm.classmate.Schedule.Fragment.MainFragment;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BatchLessonPlanFragment extends Fragment implements BatchLessonPlanCustomListAdapter.onClickList {


    private CheckedTextView mToolbarToggle;
    private List<BatchLessonPlanData> batchLessonPlanDatas;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private BatchLessonPlanAdapter lessonPlanAdapter;
    private PopupWindow pw;

    public BatchLessonPlanFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("views", "onActivitycreated 1");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(BatchActivity.title
        );
        mToolbarToggle = (CheckedTextView) (getActivity()).findViewById(R.id.toolbar_toggle);
        mToolbarToggle.setVisibility(View.GONE);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("views", "onCreateView 1");
        return inflater.inflate(R.layout.fragment_batch_lesson_plan, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("views", "onCreate 1");
        batchLessonPlanDatas = BatchLessonPlanData.CreateDummyData();

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("views","onResume 1");

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("views","onAttach 1");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("views","onDetach 1");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("views", batchLessonPlanDatas.size() + "");
        recyclerView = (RecyclerView) view.findViewById(R.id.batch_lesson_plan_recycler);
        lessonPlanAdapter = new BatchLessonPlanAdapter(getActivity(), batchLessonPlanDatas, recyclerView, BatchLessonPlanFragment.this);
        recyclerView.setAdapter(lessonPlanAdapter);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
    }


    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.d("views","onViewStateRestored 1");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("views","onsave instance 1");
    }

    @Override
    public void onClick(View view, String s) {

    }
}
