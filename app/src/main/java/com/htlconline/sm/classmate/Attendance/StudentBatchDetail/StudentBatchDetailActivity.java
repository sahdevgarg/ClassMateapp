package com.htlconline.sm.classmate.Attendance.StudentBatchDetail;

import android.app.Activity;
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
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.htlconline.sm.classmate.AppController;
import com.htlconline.sm.classmate.CustomRequests.CustomGetRequest;
import com.htlconline.sm.classmate.R;
import com.htlconline.sm.classmate.Student.StudentListingAdapter;
import com.htlconline.sm.classmate.Student.StudentListingModel;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Shikhar Garg on 02-02-2017.
 */
public class StudentBatchDetailActivity extends Fragment {
    private RecyclerView rv;
    private RecyclerView.LayoutManager manager;
    private StudentBatchDetailAdapter adapter;
    private List<StudentBatchDetailModel.Results> slist;
    private Button next,prev;
    private String curr_url,next_url,prev_url;
    private static final String STUDENTKEY="student_id";
    private static final String BATCHKEY="batch_id";
    private String studentid,batchid;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.student_batch_detail,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        batchid = getArguments().getString("batch_id");
        studentid = getArguments().getString("student_id");

        rv=(RecyclerView) view.findViewById(R.id.rv);
        manager=new LinearLayoutManager(getActivity());
        rv.setLayoutManager(manager);


        next = (Button) view.findViewById(R.id.next_button);
        prev = (Button) view.findViewById(R.id.prev_button);


        curr_url="http://www.htlconline.com/api/student/attendance/?&no_records=6&page=1&"+STUDENTKEY+"="+studentid+"&"+BATCHKEY+"="+batchid;
        next_url=null;
        prev_url=null;

        request(curr_url);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(next_url!=null)
                    request(next_url);
            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(prev_url!=null)
                    request(prev_url);
            }
        });

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void request(String url) {
        CustomGetRequest customGetRequest=new CustomGetRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("Student", response.toString());
                Gson gson=new Gson();
                StudentBatchDetailModel slm=gson.fromJson(String.valueOf(response), StudentBatchDetailModel.class);
                slist = Arrays.asList(slm.getResults());
                next_url=slm.getNext();
                prev_url=slm.getPrev();
                adapter=new StudentBatchDetailAdapter(slist,getActivity());
                rv.setAdapter(adapter);
            }


        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Student","Student7");
                        error.printStackTrace();
                    }
                },getActivity());
        //mRequestQueue.add(customGetRequest);
        AppController.getInstance(getActivity()).getRequestQueue().add(customGetRequest);


    }

}
