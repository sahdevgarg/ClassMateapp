package com.htlconline.sm.classmate.Batch.BatchFragments;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.htlconline.sm.classmate.AppController;
import com.htlconline.sm.classmate.Batch.BatchActivity;
import com.htlconline.sm.classmate.Batch.Adapters.BatchListingAdapter;
import com.htlconline.sm.classmate.Batch.Data.BatchListData;
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
public class BatchListFragment extends Fragment implements BatchListingAdapter.OnClickListItem {

    private static TestClass list;
    private String json;
    private RecyclerView recyclerView;
    private BatchListingAdapter listAdapter;
    private LinearLayoutManager manager;
    private Context context;
    private static List<BatchListData.Results> results = new ArrayList<>();
    private static List<BatchListData.Results> combined = new ArrayList<>();
    private SearchView searchView;
    private EditText searchPlate;
    private int pageCount = 1;
    private int rangeCount = 1;
    private int ResponseCount = 0;
    private int from;
    private int to;
    private ProgressBar progress;

    public BatchListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Test", "on create of list");
        results.clear();
        combined.clear();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Log.d("Test","on create view of list");
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

                    final List<BatchListData.Results> filteredList = new ArrayList<>();

                    for (int i = 0; i < combined.size(); i++) {

                        final String text = combined.get(i).getDisplay_name().toLowerCase();

                        if (text.contains(newText)) {
                            // Log.d("Test",text);
                            filteredList.add(combined.get(i));
                        }
                    }
                    // Log.d("test", filteredList.size()+"");
                    listAdapter = new BatchListingAdapter(getActivity(), filteredList);
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
        Log.d("Test", "on Resume list");
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
        listAdapter = new BatchListingAdapter(getActivity(), combined);
        listAdapter.setClickListener(BatchListFragment.this);
        recyclerView.setAdapter(listAdapter);
        manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Toast.makeText(getContext(),"Back button clicked", Toast.LENGTH_SHORT).show();
                if (!searchView.isIconified()) {
                    searchView.setIconified(true);
                    return true;
                } else {
                    //Toast.makeText(getContext(),"clicked here",Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                }

            case R.id.action_filter:
                FragmentManager manager = getActivity().getSupportFragmentManager();
                Fragment fragment = new BatchFilterFragment();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
                transaction.replace(R.id.main_batch_layout,fragment,"batch_filter_fragment");
                transaction.addToBackStack(null);
                transaction.commit();
                return true;
        }
        return true;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("Test", "onViewCreated of list");
        setHasOptionsMenu(true);
        progress = (ProgressBar) view.findViewById(R.id.progress_bar);
        fetchData();
        recyclerView = (RecyclerView) view.findViewById(R.id.custom_recycler_view);
        manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.hasFixedSize();
        //Log.d("Test size", "size"+combinedBatchLists.size());

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = manager.getChildCount();
                int totalItemCount = manager.getItemCount();
                int pastVisiblesItems = manager.findFirstVisibleItemPosition();
                if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {

                    if (rangeCount == 1) {
                        ResponseCount = 0;
                    }
                    Log.d("Test", "Last Item Wow !");
                    from = (pageCount - 1) * 20;
                    while (rangeCount <= 5) {
                        paginationRequest(pageCount);
                        rangeCount++;
                        pageCount++;
                    }
                    to = (pageCount - 1) * 20;

                    Log.d("Test range", String.valueOf(to - from));

                }
            }
        });


    }


    private void fetchData() {
        pageCount = rangeCount = 1;
        ResponseCount = 0;
        while (rangeCount <= 5) {
            request(pageCount);
            rangeCount++;
            pageCount++;
        }
        //Log.d("Test", pageCount + "");
        rangeCount = 1;
    }

    private void paginationRequest(int pageCount) {

        progress.setVisibility(View.VISIBLE);
        Log.d("Test","pagination request");
        String url = "http://www.htlconline.com/api/batch_listing/?page=" + pageCount;

        Log.d("Test url", url);
        //RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
        CustomGetRequest customGetRequest = new CustomGetRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                BatchListData list;
                list = gson.fromJson(String.valueOf(response), BatchListData.class);
                results = list.getResults();
                for (int j = 0; j < results.size(); j++) {
                    BatchListData.Results result = results.get(j);
                    combined.add(result);
                }
                Log.d("Test count", response.toString());
                ResponseCount++;
                if (ResponseCount == 5) {
                    rangeCount = 1;
                    ResponseCount = 0;
                    notifyChange(from, to);
                    progress.setVisibility(View.GONE);
                }

            }


        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Log.i("Student","Student7");
                        error.printStackTrace();
                    }
                },getActivity());
        AppController.getInstance(getActivity()).getRequestQueue().add(customGetRequest);

    }

    private void notifyChange(int from, int to) {
        listAdapter.notifyItemRangeInserted(from, to);
    }


    @Override
    public void onClick(View view, String title) {
        Intent i = new Intent(context, BatchActivity.class);

        i.putExtra("title", title);
        startActivity(i);
    }

    private void request(int pageCount) {
        Log.d("Test","request called");
        progress.setVisibility(View.VISIBLE);
        String url = "http://www.htlconline.com/api/batch_listing/?page=" + pageCount;
        Log.d("Test url", url);
        CustomGetRequest customGetRequest = new CustomGetRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                BatchListData list;
                list = gson.fromJson(String.valueOf(response), BatchListData.class);
                results = list.getResults();
                for (int j = 0; j < results.size(); j++) {
                    // Log.d("Test size",results.size()+"");
                    BatchListData.Results result = results.get(j);
                    combined.add(result);
                }

                Log.d("Test count", response.toString());
                ResponseCount++;
                if (ResponseCount == 5) {
                    Log.d("Test count", "completed");
                    listAdapter = new BatchListingAdapter(getActivity(), combined);
                    listAdapter.setClickListener(BatchListFragment.this);
                    recyclerView.setAdapter(listAdapter);
                    progress.setVisibility(View.GONE);

                }


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
