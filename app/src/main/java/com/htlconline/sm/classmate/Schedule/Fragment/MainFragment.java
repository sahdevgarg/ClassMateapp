package com.htlconline.sm.classmate.Schedule.Fragment;


import android.Manifest;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckedTextView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.htlconline.sm.classmate.AppController;
import com.htlconline.sm.classmate.Batch.Adapters.BatchPagerAdapter;
import com.htlconline.sm.classmate.CustomRequests.CustomGetRequest;
import com.htlconline.sm.classmate.R;
import com.htlconline.sm.classmate.Schedule.CalendarUtils;
import com.htlconline.sm.classmate.Schedule.MonthData.Events;
import com.htlconline.sm.classmate.Schedule.PagerAdapter;
import com.htlconline.sm.classmate.Schedule.Timetable;
import com.htlconline.sm.classmate.Schedule.content.CalendarCursor;
import com.htlconline.sm.classmate.Schedule.content.EventCursor;
import com.htlconline.sm.classmate.Schedule.content.EventsQueryHandler;
import com.htlconline.sm.classmate.Schedule.widget.CustomAdapter;
import com.htlconline.sm.classmate.Schedule.widget.CustomInfo;
import com.htlconline.sm.classmate.Schedule.widget.EventCalendarView;

import com.htlconline.sm.classmate.Schedule.widget.Model;
import com.htlconline.sm.classmate.interfaces.FragmentChangeListener;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, CustomAdapter.OnClickListItem {


    private static final String STATE_TOOLBAR_TOGGLE = "state:toolbarToggle";
    private static final int REQUEST_CODE_CALENDAR = 0;
    private static final int REQUEST_CODE_LOCATION = 1;
    private static final String SEPARATOR = ",";
    private static final int LOADER_CALENDARS = 0;
    private Boolean calledByOnCreate = false;
    private static final int LOADER_LOCAL_CALENDAR = 1;
    private FrameLayout view;
    private static int position;

    private static int pos;
    private static int max;
    private static final Coordinator mCoordinator = new Coordinator();
    private CheckedTextView mToolbarToggle;
    private EventCalendarView mCalendarView;
    private final HashSet<String> mExcludedCalendarIds = new HashSet<>();

    private static LinearLayoutManager manager;
    private static RecyclerView recyclerView;
    private static CustomAdapter customAdapter;
    private static List<CustomInfo> monthCalendar = new ArrayList<>();
    private Timetable list;
    private static HashMap<String, Integer> map = new HashMap<String, Integer>();
    private static HashMap<Pair<Integer, Integer>, Boolean> yearMap = new HashMap<>();
    private static Boolean control = true;
    private String json;
    private static String currMonth;
    private static String currYear;
    private static Context context;
    private static Map<String, List<Events>> setUpList = new HashMap<String, List<Events>>();
    private static FragmentChangeListener changeListener;
    private PopupWindow pw;
    private String Url;
    private static int counter = 0;
    private ProgressBar progressBar;
    private FragmentManager fragmentManager;

    public MainFragment() {
    }

    public MainFragment(PagerAdapter.FirstPageListener listener) {
        // if student activity calls this fragment
        // set the api url accordingly
        changeListener = listener;
        setUpVariables();
        Model.setBatchUrl();
        Url = Model.getBatchUrl();
    }

    public MainFragment(BatchPagerAdapter.FirstPageListener listener) {

        // if called by the batch activity
        changeListener = listener;
        setUpVariables();
        Model.setBatchUrl();
        Url = Model.getBatchUrl();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.include_activity_main, container, false);

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View toggleButton = getActivity().findViewById(R.id.toolbar_toggle_frame);
        if (toggleButton != null) { // can be null as disabled in landscape
            toggleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mToolbarToggle.toggle();
                    toggleCalendarView();
                }
            });
        }
        if (MainFragment.this.getUserVisibleHint()) {

            mToolbarToggle.toggle();

        }

        mCoordinator.coordinate(mToolbarToggle, mCalendarView);
//
//     ((AppCompatActivity)getActivity()).setSupportActionBar((Toolbar) view.findViewById(R.id.toolbar));
//        //noinspection ConstantConditions
//     ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayOptions(
//            ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_HOME_AS_UP);
//        mDrawerToggle.setDrawerIndicatorEnabled(true);
//        mDrawerToggle.syncState();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mCoordinator.saveState(outState);
        outState.putBoolean(STATE_TOOLBAR_TOGGLE, mToolbarToggle.isChecked());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Test", "" + "onCreate()");
        initSetUp();
        setUpPreferences();
    }

    private void initSetUp() {

        map.clear();
        setUpList.clear();
        monthCalendar.clear();
        yearMap.clear();
        pos = 0;
        position = 0;
        Calendar calendar = Calendar.getInstance();
        LoadData(calendar.getTimeInMillis());
        calledByOnCreate = true;
    }

//    private void fetchData() {
//        progressBar.setVisibility(View.VISIBLE);
//        Log.d("Test URL", Url);
//        CustomGetRequest customGetRequest = new CustomGetRequest(Request.Method.GET, Url, new com.android.volley.Response.Listener<JSONObject>() {
//
//            @Override
//            public void onResponse(JSONObject response) {
//
//                Gson gson = new Gson();
//                json = response.toString();
//                list = gson.fromJson(json, Timetable.class);
//                List<Timetable.Results> results = list.getResults();
//                //Log.d("Test results", results.size() + "");
//                Log.d("Test results", json);
//                setupData();
//                progressBar.setVisibility(View.GONE);
//
//                //getData(currYear,currMonth);
//
//            }
//
//        }, new com.android.volley.Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        //Log.i("Student","Student7");
//                        error.printStackTrace();
//                    }
//                });
//        //mRequestQueue.add(customGetRequest);
//        AppController.getInstance(getActivity()).getRequestQueue().add(customGetRequest);
//
//    }

    private void fetchData() {
        progressBar.setVisibility(View.VISIBLE);
        Log.d("Test URL", Url);
        CustomGetRequest customGetRequest = new CustomGetRequest(Request.Method.GET, Url, new com.android.volley.Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                Gson gson = new Gson();
                json = response.toString();
                list = gson.fromJson(json, Timetable.class);
                List<Timetable.Results> results = list.getResults();
                //Log.d("Test results", results.size() + "");
                Log.d("Test results", json);
                setupData();
                progressBar.setVisibility(View.GONE);

                //getData(currYear,currMonth);

            }


        },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Log.i("Student","Student7");
                        error.printStackTrace();
                    }
                },getActivity());
        //mRequestQueue.add(customGetRequest);
        AppController.getInstance(getActivity()).getRequestQueue().add(customGetRequest);

    }

    private void setUpVariables() {

        // set up variables for the api

        Calendar calendar = Calendar.getInstance();
        int month = (calendar.get(Calendar.MONTH) + 1);
        int dd = (calendar.get(Calendar.DATE));
        int yer = (calendar.get(Calendar.YEAR));
        max = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        // indicating that we are fetching the data for a particular month in a year
        // so that we can check later whether we have information for that month or not

        Pair<Integer, Integer> pair = new Pair<>(month, yer);
        yearMap.put(pair, true);


        Model.setStart_date(yer + "-" + month + "-" + 1);
        Model.setEnd_date(yer + "-" + month + "-" + max);


    }

    private void setupData() {

        // format data coming from the api
        // dates do not come sequentially from the api
        // so make a list having all the events of a particular day
        // this is done using a hash map
        // we will give this list  to adapter of the recycler view


        List<Timetable.Results> results = list.getResults();
        List<Events> eventsList;
        Events events;
        for (int i = 0; i < list.getResults().size(); i++) {
                Timetable.Results result = results.get(i);
            if (setUpList.containsKey(result.getFormattedDate())) {
                    eventsList = setUpList.get(result.getFormattedDate());

            } else {
                    eventsList = new ArrayList<>();

                }
            events = new Events();
            events.setEnd_time(result.getEndTime());
            events.setStart_time(result.getStartTime());
            events.setEvent_name(result.getClass_type());
            events.setEvent_venue(result.getCentre());
            events.setProduct(result.getProduct());
            events.setSubject(result.getSubject());
            events.setInstructor(result.getInstructor());
            eventsList.add(events);
            setUpList.put(result.getFormattedDate(), eventsList);
        }

        populate();

    }

    private void populate() {

        // if the recycler view is being populated for the first time

        if (calledByOnCreate) {
            Log.d("Test", "called by on create");
            customAdapter = new CustomAdapter(getActivity(), monthCalendar, setUpList);
            customAdapter.setClickListener(MainFragment.this);
            recyclerView.setAdapter(customAdapter);
            Log.d("Test pos", +position + "");
            manager.scrollToPositionWithOffset(position, 0);
        }
        // if we are adding months to the list after
        else {
            Log.d("Test", "not called by on create");
            customAdapter.update(setUpList);
            //customAdapter.notify();
        }

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("Test ", "Main Fragment View Created");
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        setUpContentView(view);
        fetchData();
        setHasOptionsMenu(true);
        if (MainFragment.this.getUserVisibleHint()) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");
            mToolbarToggle = (CheckedTextView) (getActivity()).findViewById(R.id.toolbar_toggle);
            mToolbarToggle.setVisibility(View.VISIBLE);

        }


        context = getActivity();
        recyclerView = (RecyclerView) view.findViewById(R.id.custom_recycler_view);
        manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        fragmentManager = getFragmentManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                try {
                    //apply check to see if scroll listener is allowed to perform its action
                    // this is done so that this method and custom sync does not fire up at the
                    // same time
                    if (control)
                    notifydatechanged();




                    // to check if we reached the bottom end of the recycler view
                    // add data for next month
                    int visibleItemCount = manager.getChildCount();
                    int totalItemCount = manager.getItemCount();
                    int pastVisibleItems = manager.findFirstVisibleItemPosition();
                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                        Log.d("Test scroll", "down");
                        addToMonth(-1, totalItemCount);

                    }
                    // to check if we reached at the top of the recycler view
                    // add data for previous month
                    if (MainFragment.this.getUserVisibleHint())
                        if (manager.findFirstCompletelyVisibleItemPosition() == 0) {
                            Log.d("Test scroll", "top");
                            addToMonth(1, 1);

                        }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }


        });

        mCalendarView.setVisibility(View.VISIBLE);
        control = true;


    }

    private void addToMonth(int direction, int curr) {
        // direction = diction of scrolling
        // curr = the curr index of the adapter
        SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Calendar cal = Calendar.getInstance();
        CustomInfo info = new CustomInfo();
        info = monthCalendar.get(curr - 1);
        String date = info.getDate();
        String parts[] = date.split("-");
        int month = Integer.valueOf(parts[1]);
        int year = Integer.valueOf(parts[2]);

        {
            // if scrolling down and the month is december
            // current year will change
            if (direction == -1) {
                if (month == 12) {
                    cal.set(Calendar.MONTH, 0);
                    cal.set(Calendar.YEAR, year + 1);
                } else {
                    cal.set(Calendar.MONTH, month);
                    cal.set(Calendar.YEAR, year);
                }

                cal.set(Calendar.DAY_OF_MONTH, 1);
                int mm = cal.get(Calendar.MONTH);
                int yr = cal.get(Calendar.YEAR);

                // add data for a month only if it is not already present

                if (!map.containsKey("15-" + (mm + 1) + "-" + yr)) {
                    max = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                    int from = monthCalendar.size();
                    info = new CustomInfo();
                    info.setEvent("");
                    info.setDate(format1.format(cal.getTime()));
                    monthCalendar.add(info);
                    map.put(format1.format(cal.getTime()), pos++);

                    for (int i = 1; i < max; i++) {
                        cal.set(Calendar.DAY_OF_MONTH, i + 1);
                        info = new CustomInfo();
                        info.setEvent("");
                        info.setDate(format1.format(cal.getTime()));
                        map.put(format1.format(cal.getTime()), pos++);
                        monthCalendar.add(info);
                    }
                    customAdapter.notifyItemRangeInserted(from, from + max);

                }


            } else {

                Log.d("Test scroll", " called");
                // if scrolled up and the month is november then
                // current year will change
                if (month == 1) {
                    cal.set(Calendar.MONTH, 11);
                    cal.set(Calendar.YEAR, year - 1);
                } else {
                    cal.set(Calendar.MONTH, month - 2);
                    cal.set(Calendar.YEAR, year);
                }
                int mm = cal.get(Calendar.MONTH);
                int yr = cal.get(Calendar.YEAR);

                // add only if not already present
                if (!map.containsKey("15-" + (mm + 1) + "-" + yr)) {
                    max = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                    cal.set(Calendar.DAY_OF_MONTH, max);

                    Log.d("Test scroll", "" + max);


                    // int from = monthCalendar.size();
                    info = new CustomInfo();
                    info.setEvent("");
                    info.setDate(format1.format(cal.getTime()));
                    monthCalendar.add(0, info);
                    map.put(format1.format(cal.getTime()), pos++);

                    for (int i = max - 1; i > 0; i--) {
                        cal.set(Calendar.DAY_OF_MONTH, i);
                        info = new CustomInfo();
                        info.setEvent("");
                        // Log.d("Test date", "" + format1.format(cal.getTime()));
                        info.setDate(format1.format(cal.getTime()));
                        map.put(format1.format(cal.getTime()), pos++);
                        monthCalendar.add(0, info);
                    }
                    customAdapter.notifyItemRangeInserted(0, max);
                }

            }


        }


    }

    public List<CustomInfo> LoadData(long time) {

        // creating a initial list of current month
        // and of a month before and after of the current month
        monthCalendar = new ArrayList<CustomInfo>();
        SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        String currDate = format1.format(time);
        Log.d("Test date",currDate);
        Date date = new Date();
        try {
            date = format1.parse(currDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        //data for month before
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        max = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        int month = (calendar.get(Calendar.MONTH) + 1);
        int dd = (calendar.get(Calendar.DATE));
        int yer = (calendar.get(Calendar.YEAR));
        Log.d("Test ",month+" "+dd+" "+yer);


        CustomInfo info1 = new CustomInfo();
        info1.setEvent("");
        info1.setDate(format1.format(calendar.getTime()));
        monthCalendar.add(info1);
        map.put(format1.format(calendar.getTime()), pos++);
        if (format1.format(calendar.getTime()).equals(currDate)) {
            position = pos-1;
        }

        for (int i = 1; i < max; i++) {
            calendar.set(Calendar.DAY_OF_MONTH, i + 1);
            if (format1.format(calendar.getTime()).equals(currDate)) {
                position = pos;
            }
            info1 = new CustomInfo();
            info1.setEvent("");
            info1.setDate(format1.format(calendar.getTime()));
            map.put(format1.format(calendar.getTime()), pos++);
            // Log.d("Test date", "" + format1.format(calendar.getTime()));
            monthCalendar.add(info1);
        }


        //data for curr month
        calendar = Calendar.getInstance();
        calendar.setTime(date);

        month = (calendar.get(Calendar.MONTH) + 1);
        dd = (calendar.get(Calendar.DATE));
        yer = (calendar.get(Calendar.YEAR));

        Log.d("Test ",month+" "+dd+" "+yer);

        currMonth = "" + month;
        currYear = "" + yer;
        Log.d("Test ",currDate+" "+currYear+" "+currMonth);

        Pair<Integer, Integer> pair = new Pair<>(month, yer);
        yearMap.put(pair, true);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.YEAR, yer);
        max = cal.getActualMaximum(Calendar.DAY_OF_MONTH);


        CustomInfo info = new CustomInfo();
        info.setEvent("");
        info.setDate(format1.format(cal.getTime()));
        monthCalendar.add(info);
        map.put(format1.format(cal.getTime()), pos++);
        if (format1.format(calendar.getTime()).equals(currDate)) {
            position = pos-1;
        }

        for (int i = 1; i < max; i++) {
            cal.set(Calendar.DAY_OF_MONTH, i + 1);
            if (format1.format(cal.getTime()).equals(currDate)) {
                position = pos;
                Log.d("Test ",currDate+" "+""+format1.format(cal.getTime()));
            }
            info1 = new CustomInfo();
            info1.setEvent("");
            info1.setDate(format1.format(cal.getTime()));
            map.put(format1.format(cal.getTime()), pos++);
            monthCalendar.add(info1);
        }


        //data for month after curr month
        calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        max = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        month = (calendar.get(Calendar.MONTH) + 1);
        dd = (calendar.get(Calendar.DATE));
        yer = (calendar.get(Calendar.YEAR));

        Log.d("Test ",month+" "+dd+" "+yer);

        info1 = new CustomInfo();
            info1.setEvent("");
        info1.setDate(format1.format(calendar.getTime()));
            monthCalendar.add(info1);
        map.put(format1.format(calendar.getTime()), pos++);
        if (format1.format(calendar.getTime()).equals(currDate)) {
            position = pos-1;
        }

        for (int i = 1; i < max; i++) {
            calendar.set(Calendar.DAY_OF_MONTH, i + 1);
            if (format1.format(calendar.getTime()).equals(currDate)) {
                position = pos;
            }
            info1 = new CustomInfo();
            info1.setEvent("");
            info1.setDate(format1.format(calendar.getTime()));
            map.put(format1.format(calendar.getTime()), pos++);
            monthCalendar.add(info1);
        }

        return monthCalendar;
    }


    private void notifydatechanged() throws ParseException {

        // to synchronise the recycler view visible item with the dat of the month view(calendar)
        int position = manager.findFirstVisibleItemPosition();
        if (position < 0) {
            return;
        }
        CustomInfo info = monthCalendar.get(position);
        String date = info.getDate();
        String parts[] = date.split("-");
        int month = Integer.valueOf(parts[1]);
        int year = Integer.valueOf(parts[2]);
        Pair<Integer, Integer> pair = new Pair<>(month, year);
        // check if we have data for that particular month or nor
        // if not proceed and fetch it
        // otherwise there is no need
        // we already have that months data
        if (!yearMap.containsKey(pair) && map.containsKey(date) && MainFragment.this.getUserVisibleHint()) {
            Log.d("Test year", "called here");
            populateSetupList(date);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date date1 = sdf.parse(date);
        long milli = (date1.getTime());

        mCoordinator.sync(milli, recyclerView);

    }

    private void populateSetupList(String date) {

        // for adding data if notifydatechange method sees that for this date
        // there is no available data
        Log.d("Test", "populating set up list");
        calledByOnCreate = false;
        String parts[] = date.split("-");
        int month = Integer.valueOf(parts[1]);
        int year = Integer.valueOf(parts[2]);
        // to indicate we are fetching the data of a particular month
        // so that we will not fetch the same later
        Pair<Integer, Integer> pair = new Pair<>(month, year);
        yearMap.put(pair, true);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.DAY_OF_MONTH,1);


        int month1 = (cal.get(Calendar.MONTH) + 1);
        int dd = (cal.get(Calendar.DATE));
        int yer = (cal.get(Calendar.YEAR));

        max = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
//        Log.d("Test date ",date+" " +max+" "+month+"  "+year);
//        Log.d("Test date",month1+" "+dd+" "+yer);
        // from this date
        Model.setStart_date(year + "-" + month + "-" + 1);
        // to this date
        Model.setEnd_date(year + "-" + month + "-" + max);
        //set this parameters in the url
        Model.setBatchUrl();
        Url = Model.getBatchUrl();

        Log.d("Test Url",Url);

        // get data from the api
        fetchData();


    }

    private void customsync(long date) {
        // to synchronise the scrolling
        // first let custom sync handle the addition of new data rather than scrolling
        // date = time in milliseconds of the day highlighted in the calendar
        // revoke control from on scroll listener of the recycler view
        control = false;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        String formatted = sdf.format(date);

        String parts[] = formatted.split("-");
        String month = parts[1];
        String year = parts[2];

        if (month != (currMonth) && year != (currYear)) {
            // if there is no entry yet for the date then we need to add it to the list
            if (!map.containsKey(formatted))
                // if current year is less than year add data to the bottom of the list
                if (Integer.valueOf(year) > Integer.valueOf(currYear)) {
                    addToMonth(-1, manager.getItemCount());
                    // if current year is more than year then add data to the top the list
                } else if (Integer.valueOf(year) < Integer.valueOf(currYear)) {
                    addToMonth(1, 1);
                } else {
                    // if year is equal to the current year but current month is greater than month
                    // add data to the top of the list
                    if (Integer.valueOf(month) < Integer.valueOf(currMonth)) {
                        addToMonth(1, 1);
                    } else {
                        addToMonth(-1, manager.getItemCount());
                    }
                }

        }
        // if that days data already has an entry then simply scroll to that position
        if (map.containsKey(sdf.format(date))) {
            for (int i = 0; i < monthCalendar.size(); i++) {
                CustomInfo info = monthCalendar.get(i);
                if (info.getDate().equals(sdf.format(date))) {
                    manager.scrollToPositionWithOffset(i, 0);
                    break;
                }
            }

        }
        // give control to the recycler view scroll listener to preform ita action
        control = true;


    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");
        mToolbarToggle = (CheckedTextView) (getActivity()).findViewById(R.id.toolbar_toggle);
        mToolbarToggle.setVisibility(View.VISIBLE);

        inflater.inflate(R.menu.menu_main, menu);


    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                moveToFragment(R.id.action_three_day_view);
                break;
            case R.id.action_today:
                mCoordinator.reset();
                customsync(Calendar.getInstance().getTimeInMillis());
                break;
            case R.id.action_day_view:
                moveToFragment(R.id.action_day_view);
                break;
            case R.id.action_three_day_view:
                moveToFragment(R.id.action_three_day_view);
                break;
            case R.id.action_week_view:
                moveToFragment(R.id.action_week_view);
            case R.id.action_agenda_view:
                return true;


        }

        return super.onOptionsItemSelected(item);
    }

    public void moveToFragment(int id) {

        changeListener.OnSwitchToNextFragment(id);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mCalendarView.deactivate();

        PreferenceManager.getDefaultSharedPreferences(getContext())
                .edit()
                .putString(CalendarUtils.PREF_CALENDAR_EXCLUSIONS,
                        TextUtils.join(SEPARATOR, mExcludedCalendarIds))
                .apply();

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_CODE_CALENDAR:
                if (checkCalendarPermissions()) {
                    toggleEmptyView(false);
                    loadEvents();
                } else {
                    toggleEmptyView(true);
                }
                break;
        }

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String selection = null;
        String[] selectionArgs = null;
        return new CursorLoader(getContext(),
                CalendarContract.Calendars.CONTENT_URI,
                CalendarCursor.PROJECTION, selection, selectionArgs,
                CalendarContract.Calendars.DEFAULT_SORT_ORDER);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


    private void setUpPreferences() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());

        String exclusions = PreferenceManager.getDefaultSharedPreferences(getContext())
                .getString(CalendarUtils.PREF_CALENDAR_EXCLUSIONS, null);
        if (!TextUtils.isEmpty(exclusions)) {
            mExcludedCalendarIds.addAll(Arrays.asList(exclusions.split(SEPARATOR)));
        }
        CalendarUtils.sWeekStart = sp.getInt(CalendarUtils.PREF_WEEK_START, Calendar.MONDAY);

    }

        private void setUpContentView(View view) {
//        mCoordinatorLayout = view.findViewById(R.id.coordinator_layout);
//        mDrawerLayout = (DrawerLayout) view.findViewById(R.id.drawer_layout);
//        mDrawer = view.findViewById(R.id.drawer);
//        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout,
         //       R.string.open_drawer, R.string.close_drawer);
//        mDrawerLayout.addDrawerListener(mDrawerToggle);
//        mToolbarToggle = (CheckedTextView) view.findViewById(R.id.toolbar_toggle);

        mCalendarView = (EventCalendarView) view.findViewById(R.id.calendar_view);

//       // mFabAdd = (FloatingActionButton) view.findViewById(R.id.fab);
//       // mFabAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                createEvent();
//            }
//        });
//        mFabAdd.hide();
    }

    private void toggleCalendarView() {
        if (mToolbarToggle.isChecked()) {
            mCalendarView.setVisibility(View.VISIBLE);
        } else {
            mCalendarView.setVisibility(View.GONE);
        }
    }

    @SuppressWarnings("ConstantConditions")
    private void toggleEmptyView(boolean visible) {
        if (visible) {
            view.findViewById(R.id.empty).setVisibility(View.VISIBLE);
            view.findViewById(R.id.empty).bringToFront();
            view.findViewById(R.id.button_permission)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            requestCalendarPermissions();
                        }
                    });
        } else {
            view.findViewById(R.id.empty).setVisibility(View.GONE);
        }
    }


//    private void createEvent() {
//        startActivity(new Intent(getContext(), EditActivity.class));
//    }

    private void loadEvents() {

        getActivity().getSupportLoaderManager().initLoader(LOADER_CALENDARS, null, this);
//      mFabAdd.show();
        mCalendarView.setCalendarAdapter(new CalendarCursorAdapter(getContext(), mExcludedCalendarIds));

    }


    @VisibleForTesting
    protected boolean checkCalendarPermissions() {
        return (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CALENDAR) |
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_CALENDAR)) ==
                PackageManager.PERMISSION_GRANTED;
    }


    @VisibleForTesting
    protected void requestCalendarPermissions() {
        //Toast.makeText(getContext(),"calendar permissions",Toast.LENGTH_SHORT).show();
        MainFragment.this.requestPermissions(new String[]{
                        Manifest.permission.READ_CALENDAR,
                        Manifest.permission.WRITE_CALENDAR},
                REQUEST_CODE_CALENDAR);
    }

    @Override
    public void onClick(View view, Events events, String date) {
        //Log.d("Test", " event clicked");

        SimpleDateFormat originalFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        SimpleDateFormat targetFormat = new SimpleDateFormat("dd MMMM, yyyy", Locale.ENGLISH);
        Date date1 = null;
        try {
            date1 = originalFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedDate = targetFormat.format(date1);
        initiatePopupWindow(view, events, formattedDate);
    }


    private void initiatePopupWindow(View view, Events events, String date) {

        try {
            //We need to get the instance of the LayoutInflater, use the context of this activity
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //Inflate the view from a predefined XML layout
            View layout = inflater.inflate(R.layout.fragment_event_detial,
                    (ViewGroup) view.findViewById(R.id.pop_up));
            // create a 300px width and 470px height PopupWindow
            pw = new PopupWindow(layout, LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            // display the popup in the center
            pw.showAtLocation(view, Gravity.CENTER, 0, 0);
            FrameLayout frameLayout;
            TextView eventDate;
            TextView eventProduct;
            TextView eventSubject;
            TextView eventInstructor;
            TextView eventName;
            TextView eventStart;
            TextView eventEnd;
            TextView eventVenue;
            ImageButton closeButton;
            frameLayout = (FrameLayout) layout.findViewById(R.id.pop_container);
            eventDate = (TextView) layout.findViewById(R.id.detail_event_date);
            eventVenue = (TextView) layout.findViewById(R.id.detail_event_venue);
            eventName = (TextView) layout.findViewById(R.id.detail_event_name);
            eventStart = (TextView) layout.findViewById(R.id.detail_event_start);
            eventEnd = (TextView) layout.findViewById(R.id.detail_event_end);
            eventProduct = (TextView) layout.findViewById(R.id.detail_event_product);
            eventInstructor = (TextView) layout.findViewById(R.id.detail_event_instructor);
            eventSubject = (TextView) layout.findViewById(R.id.detail_event_subject);
            changeStatusBarColor(Color.parseColor(events.getColor()));
            frameLayout.setBackgroundColor(Color.parseColor(events.getColor()));
            eventName.setText(events.getEvent_name());
            eventStart.setText(Events.getFormattedTime(events.getStart_time()));
            eventProduct.setText(events.getProduct());
            eventSubject.setText(events.getSubject());
            eventInstructor.setText(events.getInstructor());
            eventEnd.setText(Events.getFormattedTime(events.getEnd_time()));
            eventVenue.setText(events.getEvent_venue());
            eventDate.setText(date);
            closeButton = (ImageButton) layout.findViewById(R.id.close_button);
            closeButton.setBackgroundColor(Color.parseColor(events.getColor()));
            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Toast.makeText(context,"hetjbljk",Toast.LENGTH_SHORT).show();
                    pw.dismiss();
                    changeStatusBarColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null));

                }
            });


        } catch (Exception e) {
            // Log.d("Test", "Exception Raised");
            e.printStackTrace();
        }
    }

    private void changeStatusBarColor(int color) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);

        }


    }


    /**
     * Coordinator utility that synchronizes widgets as selected date changes
     */
    static class Coordinator {
        private static final String STATE_SELECTED_DATE = "state:selectedDate";

        private final EventCalendarView.OnChangeListener mCalendarListener
                = new EventCalendarView.OnChangeListener() {
            @Override
            public void onSelectedDayChange(long calendarDate) {
                sync(calendarDate, mCalendarView);
                new MainFragment().customsync(calendarDate);
            }
        };


        private TextView mTextView;
        private EventCalendarView mCalendarView;
        //private AgendaView mAgendaView;
        private long mSelectedDayMillis = CalendarUtils.NO_TIME_MILLIS;

        /**
         * Set up widgets to be synchronized
         *
         * @param textView     title
         * @param calendarView calendar view
         */
        private void coordinate(@NonNull TextView textView,
                               @NonNull EventCalendarView calendarView
                               ) {
            if (mCalendarView != null) {
                mCalendarView.setOnChangeListener(null);
            }

            mTextView = textView;
            mCalendarView = calendarView;

            if (mSelectedDayMillis < 0) {
                mSelectedDayMillis = CalendarUtils.today();
            }
            mCalendarView.setSelectedDay(mSelectedDayMillis);

          updateTitle(mSelectedDayMillis);
            calendarView.setOnChangeListener(mCalendarListener);

        }

        void saveState(Bundle outState) {
            outState.putLong(STATE_SELECTED_DATE, mSelectedDayMillis);
        }

        void restoreState(Bundle savedState) {
            if (savedState != null)
            mSelectedDayMillis = savedState.getLong(STATE_SELECTED_DATE,
                    CalendarUtils.NO_TIME_MILLIS);


        }

        void reset() {
            mSelectedDayMillis = CalendarUtils.today();
            if (mCalendarView != null) {
                mCalendarView.reset();
            }

            updateTitle(mSelectedDayMillis);
        }

        public void sync(long dayMillis, View originator) {
            mSelectedDayMillis = dayMillis;
            if (originator != mCalendarView) {
                mCalendarView.setSelectedDay(dayMillis);
            }

            updateTitle(dayMillis);
        }

        private void updateTitle(long dayMillis) {
            if (mTextView != null)
            mTextView.setText(CalendarUtils.toMonthString(mTextView.getContext(), dayMillis));
        }
    }


    static class CalendarCursorAdapter extends EventCalendarView.CalendarAdapter {
        private final MonthEventsQueryHandler mHandler;

        public CalendarCursorAdapter(Context context, Collection<String> excludedCalendarIds) {
            mHandler = new MonthEventsQueryHandler(context.getContentResolver(), this,
                    excludedCalendarIds);
        }

        @Override
        protected void loadEvents(long monthMillis) {
            long startTimeMillis = CalendarUtils.monthFirstDay(monthMillis),
                    endTimeMillis = startTimeMillis + DateUtils.DAY_IN_MILLIS *
                            CalendarUtils.monthSize(monthMillis);
            mHandler.startQuery(monthMillis, startTimeMillis, endTimeMillis);
        }
    }


    static class MonthEventsQueryHandler extends EventsQueryHandler {

        private final CalendarCursorAdapter mAdapter;

        public MonthEventsQueryHandler(ContentResolver cr,
                                       CalendarCursorAdapter adapter,
                                       @NonNull Collection<String> excludedCalendarIds) {
            super(cr, excludedCalendarIds);
            mAdapter = adapter;
        }

        @Override
        protected void handleQueryComplete(int token, Object cookie, EventCursor cursor) {
            mAdapter.bindEvents((Long) cookie, cursor);
        }
    }

    static class CalendarQueryHandler extends AsyncQueryHandler {

        public CalendarQueryHandler(ContentResolver cr) {
            super(cr);
        }
    }


    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

    }


    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("Test", "on detach");
        recyclerView.removeOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

    }

    @Override
    public void onResume() {

        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    // handle back button's click listener
                    // sometimes this method fires up two times
                    // therefore a counter is set to ensure only one condition satisfies
                    counter++;
                    if (counter == 1) {
                        Log.d("Test resume", "resume called called 11");
                        if (pw != null)
                            if (pw.isShowing()) {
                                pw.dismiss();
                                changeStatusBarColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null));
                            } else moveToFragment(R.id.action_three_day_view);
                        else {
                            moveToFragment(R.id.action_three_day_view);
    }
                    }
                    // do nohinf if the method fires up simultaneously second time
                    else if (counter == 2) {
                        counter = 0;
                    }


                    return true;


                }
                return false;
            }
        });

    }


}
