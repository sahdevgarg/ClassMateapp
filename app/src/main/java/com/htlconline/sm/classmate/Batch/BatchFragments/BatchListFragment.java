package com.htlconline.sm.classmate.Batch.BatchFragments;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.htlconline.sm.classmate.AppController;
import com.htlconline.sm.classmate.Batch.BatchActivity;
import com.htlconline.sm.classmate.Batch.BatchListAdapter;
import com.htlconline.sm.classmate.Batch.TestClass;
import com.htlconline.sm.classmate.CustomRequests.CustomGetRequest;
import com.htlconline.sm.classmate.R;
import com.htlconline.sm.classmate.Schedule.widget.Title;
import com.htlconline.sm.classmate.Student.StudentListingAdapter;
import com.htlconline.sm.classmate.Student.StudentListingModel;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static android.content.Context.SEARCH_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class BatchListFragment extends Fragment implements BatchListAdapter.OnClickListItem {

    private static TestClass list;
    private String json;
    private RecyclerView recyclerView;
    private BatchListAdapter listAdapter;
    private LinearLayoutManager manager;
    private Context context;
    private List<TestClass.Results> results;
    private SearchView searchView;
    private EditText searchPlate;

    public BatchListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        json = loadJSONFromAsset();
//        Gson gson = new Gson();
//        this.list = gson.fromJson(json, TestClass.class);
        request();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_batch_list, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_batch_list, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);

        if (searchItem != null) {
            searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
            searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    //some operation
                    return false;
                }
            });
            searchView.setOnSearchClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //some operation
                }
            });
            searchPlate = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
            searchPlate.setHint("Search");
            View searchPlateView = searchView.findViewById(android.support.v7.appcompat.R.id.search_plate);
            searchPlateView.setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.transparent));
            // use this method for search process
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    // use this method when query submitted
                    //Toast.makeText(context, query, Toast.LENGTH_SHORT).show();
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    // use this method for auto complete search process

                    newText = newText.toLowerCase();
                    //Log.d("Test",newText);

                    final List<TestClass.Results> filteredList = new ArrayList<>();

                    for (int i = 0; i < results.size(); i++) {

                        final String text = results.get(i).getDisplay_name().toLowerCase();

                        if (text.contains(newText)) {
                            // Log.d("Test",text);
                            filteredList.add(results.get(i));
                        }
                    }
                    // Log.d("test", filteredList.size()+"");
                    listAdapter = new BatchListAdapter(getActivity(), filteredList);
                    listAdapter.setClickListener(BatchListFragment.this);
                    recyclerView.setAdapter(listAdapter);
                    manager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(manager);
                    listAdapter.notifyDataSetChanged();
                    return true;
                }
            });
            SearchManager searchManager = (SearchManager) getActivity().getSystemService(SEARCH_SERVICE);
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

        }

    }


    @Override
    public void onResume() {

        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                    if (!searchView.isIconified()) {
                        searchView.setIconified(true);

                        return true;
                    }


                }

                return false;
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        return true;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.custom_recycler_view);



        manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);

    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getContext().getAssets().open("test.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            // Log.d("Test" , json);
        } catch (IOException ex) {
            Log.d("Test error", String.valueOf(ex));
            return null;
        }
        return json;
    }

    @Override
    public void onClick(View view, String title) {
        Intent i = new Intent(context,BatchActivity.class);
        Title.title=title;
        i.putExtra("title" ,title);
        startActivity(i);
    }

    private void request() {
        String url = "http://www.htlconline.com/api/batch_listing/";
        //RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
        CustomGetRequest customGetRequest=new CustomGetRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Log.i("Student",response.toString());
                Gson gson=new Gson();
                list = gson.fromJson(String.valueOf(response), TestClass.class);
                results = list.getResults();
                listAdapter = new BatchListAdapter(getActivity(), results);
                listAdapter.setClickListener(BatchListFragment.this);
                recyclerView.setAdapter(listAdapter);
            }


        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Log.i("Student","Student7");
                        error.printStackTrace();
                    }
                },getActivity());
        //mRequestQueue.add(customGetRequest);
        AppController.getInstance(getActivity()).getRequestQueue().add(customGetRequest);

    }
}
