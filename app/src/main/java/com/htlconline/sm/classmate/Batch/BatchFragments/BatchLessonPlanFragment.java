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
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.htlconline.sm.classmate.Batch.Adapters.BatchFilterListAdapter;
import com.htlconline.sm.classmate.Batch.Adapters.BatchLessonPlanAdapter;
import com.htlconline.sm.classmate.Batch.Adapters.BatchLessonPlanCustomListAdapter;
import com.htlconline.sm.classmate.Batch.BatchActivity;
import com.htlconline.sm.classmate.Batch.Data.BatchFilterData;
import com.htlconline.sm.classmate.Batch.Data.BatchLessonPlanData;
import com.htlconline.sm.classmate.Batch.Data.BatchListData;
import com.htlconline.sm.classmate.Config;
import com.htlconline.sm.classmate.ExpandableListAdapter;
import com.htlconline.sm.classmate.R;
import com.htlconline.sm.classmate.Schedule.Fragment.MainFragment;
import com.htlconline.sm.classmate.Schedule.widget.Model;
import com.htlconline.sm.classmate.pojo.LessionPlanPojo;
import com.htlconline.sm.classmate.volley.MyJsonRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BatchLessonPlanFragment extends Fragment implements BatchLessonPlanCustomListAdapter.onClickList, MyJsonRequest.OnServerResponse {


    private CheckedTextView mToolbarToggle;
    private List<BatchLessonPlanData> batchLessonPlanDatas;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private BatchLessonPlanAdapter lessonPlanAdapter;
    private PopupWindow pw;
    private String lessionplanUrl;
    private ArrayList<LessionPlanPojo.Result> lessionPlanArrayList;
    private ExpandableListView expandableList;

    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    private ExpandableListAdapter listAdapter;

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
//        recyclerView = (RecyclerView) view.findViewById(R.id.batch_lesson_plan_recycler);
//        layoutManager = new LinearLayoutManager(getActivity());
//        recyclerView.setLayoutManager(layoutManager);
        expandableList = (ExpandableListView)view.findViewById(R.id.lvExp);
        //prepareListData();

        getLessionPlanData();
    }

    public void getLessionPlanData(){
        Model.setLessionPlanUrl();
        lessionplanUrl = Model.getLessionPlanUrl();

        MyJsonRequest lessionPlanRequest = new MyJsonRequest(getActivity(),this);
        lessionPlanRequest.getJsonFromServer(Config.LESSION_PLAN_URL,lessionplanUrl,true,false);
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

    @Override
    public void getJsonFromServer(boolean flag, String tag, JSONObject jsonObject, String error) {
        try {
            if(flag){
                Gson gson = new Gson();
                LessionPlanPojo lessionPlanPojo = gson.fromJson(jsonObject.toString(), LessionPlanPojo.class);
                lessionPlanArrayList = lessionPlanPojo.getResults();

                listAdapter = new ExpandableListAdapter(getActivity(),lessionPlanArrayList);
                expandableList.setAdapter(listAdapter);
//                lessonPlanAdapter = new BatchLessonPlanAdapter(getActivity(), lessionPlanArrayList, recyclerView, BatchLessonPlanFragment.this);
//                recyclerView.setAdapter(lessonPlanAdapter);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void getJsonFromServer(boolean flag, String tag, String stringObject, String error) {

    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Top 250");
        listDataHeader.add("Now Showing");
        listDataHeader.add("Coming Soon..");

        // Adding child data
        List<String> top250 = new ArrayList<String>();
        top250.add("The Shawshank Redemption");
        top250.add("The Godfather");
        top250.add("The Godfather: Part II");
        top250.add("Pulp Fiction");
        top250.add("The Good, the Bad and the Ugly");
        top250.add("The Dark Knight");
        top250.add("12 Angry Men");

        List<String> nowShowing = new ArrayList<String>();
        nowShowing.add("The Conjuring");
        nowShowing.add("Despicable Me 2");
        nowShowing.add("Turbo");
        nowShowing.add("Grown Ups 2");
        nowShowing.add("Red 2");
        nowShowing.add("The Wolverine");

        List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("2 Guns");
        comingSoon.add("The Smurfs 2");
        comingSoon.add("The Spectacular Now");
        comingSoon.add("The Canyons");
        comingSoon.add("Europa Report");

        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(1), nowShowing);
        listDataChild.put(listDataHeader.get(2), comingSoon);
    }
}
