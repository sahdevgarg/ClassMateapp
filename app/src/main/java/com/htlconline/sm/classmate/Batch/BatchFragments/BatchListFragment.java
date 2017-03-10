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
import android.widget.Toast;

import com.github.ybq.endless.Endless;
import com.google.gson.Gson;
import com.htlconline.sm.classmate.Batch.Adapters.BatchListingAdapter;
import com.htlconline.sm.classmate.Batch.BatchActivity;
import com.htlconline.sm.classmate.Batch.Data.BatchListData;
import com.htlconline.sm.classmate.Batch.TestClass;
import com.htlconline.sm.classmate.Config;
import com.htlconline.sm.classmate.R;
import com.htlconline.sm.classmate.interfaces.OnRecyclerItemClick;
import com.htlconline.sm.classmate.volley.MyJsonRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.SEARCH_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class BatchListFragment extends Fragment implements MyJsonRequest.OnServerResponse, OnRecyclerItemClick {

    private static TestClass list;
    private static List<BatchListData.Results> combined = new ArrayList<>();
    private List<BatchListData.Results> results = new ArrayList<>();
    private String json;
    private RecyclerView recyclerView;
    private BatchListingAdapter listAdapter;
    private LinearLayoutManager manager;
    private Context context;
    private SearchView searchView;
    private EditText searchPlate;
    private int pageCount = 1;
    private int rangeCount = 1;
    private int ResponseCount = 0;
    private int from;
    private int to;
    private ProgressBar progress;
    private Endless endless;
    private int PAGE_NUMBER = 1;
    private boolean showProgress = true;

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

        Log.d("Test", "on create view of list");
        //setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_batch_list, container, false);

    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();

        View toggleButton = getActivity().findViewById(R.id.toolbar_toggle_frame);
        View toolbarTitle = getActivity().findViewById(R.id.toolBarTitle);

        toolbarTitle.setVisibility(View.VISIBLE);
        toggleButton.setVisibility(View.GONE);
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.menu_batch_list, menu);
//        final MenuItem searchItem = menu.findItem(R.id.action_search);
//
//        if (searchItem != null) {
//            searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
//            searchView.setOnCloseListener(new SearchView.OnCloseListener() {
//                @Override
//                public boolean onClose() {
//                    //some operation
//                    return false;
//                }
//            });
//            searchView.setOnSearchClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //some operation
//                }
//            });
//            searchPlate = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
//            searchPlate.setHint("Search");
//            View searchPlateView = searchView.findViewById(android.support.v7.appcompat.R.id.search_plate);
//            searchPlateView.setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.transparent));
//            // use this method for search process
//            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//                @Override
//                public boolean onQueryTextSubmit(String query) {
//                    // use this method when query submitted
//                    //Toast.makeText(context, query, Toast.LENGTH_SHORT).show();
//                    return false;
//                }
//
//                @Override
//                public boolean onQueryTextChange(String newText) {
//                    // use this method for auto complete search process
//
//                    newText = newText.toLowerCase();
//                    //Log.d("Test",newText);
//
//                    final List<BatchListData.Results> filteredList = new ArrayList<>();
//
//                    for (int i = 0; i < results.size(); i++) {
//
//                        final String text = results.get(i).getDisplay_name().toLowerCase();
//
//                        if (text.contains(newText)) {
//                            // Log.d("Test",text);
//                            filteredList.add(results.get(i));
//                        }
//                    }
//                    // Log.d("test", filteredList.size()+"");
//                    listAdapter = new BatchListingAdapter(getActivity(),BatchListFragment.this,filteredList);
//                    //listAdapter.setClickListener(BatchListFragment.this);
//                    recyclerView.setAdapter(listAdapter);
//                    endless.setAdapter(listAdapter);
//                    manager = new LinearLayoutManager(getActivity());
//                    recyclerView.setLayoutManager(manager);
//                    listAdapter.notifyDataSetChanged();
//                    return true;
//                }
//            });
//            SearchManager searchManager = (SearchManager) getActivity().getSystemService(SEARCH_SERVICE);
//            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
//
//        }
//
//    }


    @Override
    public void onResume() {

        super.onResume();
//        Log.d("Test", "on Resume list");
//        getView().setFocusableInTouchMode(true);
//        getView().requestFocus();
//        getView().setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//
//                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
//
//                    if (!searchView.isIconified()) {
//                        searchView.setIconified(true);
//                        return true;
//                    }
//
//
//                }
//
//                return false;
//            }
//        });
//        listAdapter = new BatchListingAdapter(getActivity(),this, results);
//        //listAdapter.setClickListener(BatchListFragment.this);
//        recyclerView.setAdapter(listAdapter);
//        endless.setAdapter(listAdapter);
//        manager = new LinearLayoutManager(getActivity());
//        recyclerView.setLayoutManager(manager);

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

//            case R.id.action_filter:
//                FragmentManager manager = getActivity().getSupportFragmentManager();
//                Fragment fragment = new BatchFilterFragment();
//                FragmentTransaction transaction = manager.beginTransaction();
//                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
//                transaction.replace(R.id.container, fragment, "batch_filter_fragment");
//                transaction.addToBackStack(null);
//                transaction.commit();
//                return true;
        }
        return true;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("Test", "onViewCreated of list");
        setHasOptionsMenu(true);
        recyclerView = (RecyclerView) view.findViewById(R.id.custom_recycler_view);
        manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.hasFixedSize();

        View loadingView = View.inflate(getActivity(), R.layout.layout_loading_item, null);
        loadingView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        endless = Endless.applyTo(recyclerView,
                loadingView
        );

        endless.setLoadMoreListener(new Endless.LoadMoreListener() {

            @Override
            public void onLoadMore(int i) {
                PAGE_NUMBER = i;
                getBatchListData(PAGE_NUMBER);
            }
        });

        getBatchListData(PAGE_NUMBER);

    }


//    @Override
//    public void onClick(View view, String title) {
//        Intent i = new Intent(context, BatchActivity.class);
//        //i.putExtra("title", title);
//        i.putExtra("title", title);
//        startActivity(i);
//    }

    private void getBatchListData(int pageCount) {
        String url = Config.BATCH_LIST_URL + "?page=" + pageCount;
        Log.d("Batch List url", url);
        if (pageCount > 1) {
            showProgress = false;
        }
        MyJsonRequest batchListRequest = new MyJsonRequest(getActivity(), this);
        batchListRequest.getJsonFromServer(Config.BATCH_LIST_URL, url, showProgress, false);
    }

    @Override
    public void getJsonFromServer(boolean flag, String tag, JSONObject jsonObject, String error) {
        try {
            if (flag) {
                Log.e("List", jsonObject.toString());

                Gson gson = new Gson();
                BatchListData list;
                list = gson.fromJson(jsonObject.toString(), BatchListData.class);
                results.addAll(list.getResults());
                if (results.size() > 0) {
                    if (PAGE_NUMBER == 1) {
                        listAdapter = new BatchListingAdapter(getActivity(),this, results);
                        //listAdapter.setClickListener(BatchListFragment.this);
                        recyclerView.setAdapter(listAdapter);
                        endless.setAdapter(listAdapter);
                    } else {
                        listAdapter.notifyDataSetChanged();
                        endless.loadMoreComplete();
                    }
                } else {
                    Toast.makeText(getActivity(), "No Data Available.", Toast.LENGTH_LONG).show();
                    endless.loadMoreComplete();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getJsonFromServer(boolean flag, String tag, String stringObject, String error) {

    }

    @Override
    public void onItemClick(int position,String displayName,String batchId) {
        Intent i = new Intent(context, BatchActivity.class);
        i.putExtra(Config.BATCH_TITLE, displayName);
        i.putExtra(Config.BATCH_ID,batchId);
        startActivity(i);
    }

    @Override
    public void onItemClick(int position) {

    }
}
