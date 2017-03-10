package com.htlconline.sm.classmate.Attendance;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.htlconline.sm.classmate.CustomRequests.CustomStringGetRequest;
import com.htlconline.sm.classmate.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Shikhar Garg on 02-02-2017.
 */
public class StudentBatchListFragment extends Fragment {
    private RecyclerView rv;
    private RecyclerView.LayoutManager manager;
    private String student_id,url;
    private static final String KEY="student_id";
    private List<StudentBatchListModel> slist;
    private StudentBatchListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.student_batch_list,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        student_id=getArguments().getString("student_id");
        url="http://www.htlconline.com/api/student/batches/?"+KEY+"="+student_id;
        Log.i("Hello", student_id + " " + url);
        rv=(RecyclerView) view.findViewById(R.id.rv);
        manager=new LinearLayoutManager(getActivity());
        rv.setLayoutManager(manager);
        request(url);

    }

    private void request(String url) {
//        CustomStringGetRequest customGetRequest=new CustomStringGetRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.i("SBLF", response.toString());
//                Gson gson=new Gson();
//                StudentBatchListModel[] slm=gson.fromJson(String.valueOf(response),StudentBatchListModel[].class);
//                slist= Arrays.asList(slm);
//                adapter=new StudentBatchListAdapter(slist,getActivity());
//                rv.setAdapter(adapter);
//            }
//
//
//        },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.i("SBLF","error");
//                        error.printStackTrace();
//                    }
//                },getActivity());
//        AppControllerOld.getInstance(getActivity()).getRequestQueue().add(customGetRequest);
    }
}
