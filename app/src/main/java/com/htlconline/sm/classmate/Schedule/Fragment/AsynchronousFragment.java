package com.htlconline.sm.classmate.Schedule.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.Pair;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alamkanak.weekview.WeekViewEvent;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.gson.Gson;


import com.htlconline.sm.classmate.AppController;
import com.htlconline.sm.classmate.Batch.BatchActivity;
import com.htlconline.sm.classmate.Batch.Adapters.BatchPagerAdapter;
import com.htlconline.sm.classmate.CustomRequests.CustomGetRequest;
import com.htlconline.sm.classmate.Schedule.PagerAdapter;
import com.htlconline.sm.classmate.Schedule.Timetable;
import com.htlconline.sm.classmate.Schedule.apiclient.Event;
import com.htlconline.sm.classmate.Schedule.widget.Model;
import com.htlconline.sm.classmate.Student.StudentDetailActivity;


import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


import retrofit.Callback;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AsynchronousFragment extends BaseFragment implements Callback<List<Event>> {


    private static List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
    boolean calledNetwork = false;
    private String json;
    boolean gotResponse = false;
    private static Timetable list;
    private String batch_id = "";
    private String centre_id = "";
    private String subject_id = "";
    private String class_level_id = "";
    private String start_date = null;
    private String end_date = null;
    private static int currYear = -1;
    private static int currMonth = -1;
    private static Boolean calledByBatch = true;
    private static List<WeekViewEvent> matchedEvents = new ArrayList<WeekViewEvent>();
    private HashMap<Pair<Integer, Integer>, Boolean> map = new HashMap<>();
    private static List<WeekViewEvent> savedEvents = new ArrayList<WeekViewEvent>();
    String Url;

    public AsynchronousFragment() {
        super();
    }

   public AsynchronousFragment(PagerAdapter.FirstPageListener listener) {
       super(listener);
        calledByBatch = false;
          StudentDetailActivity.swipeOff();

    }

    public AsynchronousFragment(int id) {
       super(id);
    }

    public AsynchronousFragment(BatchPagerAdapter.FirstPageListener listener) {
        super(listener);
        calledByBatch = true;
        BatchActivity.swipeOff();

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        json = loadJSONFromAsset();
//        Gson gson = new Gson();
//        list = gson.fromJson(json, Timetable.class);

    }

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {

//         Download events from network if it hasn't been done already. To understand how events are
//         downloaded using retrofit, visit http://square.github.io/retrofit
//        if (!calledNetwork) {
//            RestAdapter retrofit = new RestAdapter.Builder()
//                    .setEndpoint("https://api.myjson.com/bins")
//                    .build();
//            MyJsonService service = retrofit.create(MyJsonService.class);
//            service.listEvents(this);
//            calledNetwork = true;
//        }

        //Toast.makeText(getContext(),"on month change "+ newMonth +" " + newYear,Toast.LENGTH_SHORT).show();

        // Return only the events that matches newYear and newMonth.


        Pair<Integer, Integer> temp = new Pair<>(newMonth, newYear);
        if (!map.containsKey(temp)) {
            map.put(temp, true);
            fetchData(newYear, newMonth);
        }

        Log.d("Test change", "" + newYear + newMonth);
//        Log.d("Test dates",""+currYear+currMonth);
//
//        if(newMonth!=currMonth && newYear!=currYear)
//        {
//            //calledNetwork=false;
//            currYear=newYear;
//            currMonth=newMonth;
//
//        }
//
//        if(!calledNetwork)
//        {
//
//        }


        Log.d("Test change", "on month change called 1");
       List<WeekViewEvent> matchedEvents = new ArrayList<WeekViewEvent>();
        for (WeekViewEvent event : events) {
            if (eventMatches(event, newYear, newMonth)) {
                matchedEvents.add(event);
            }
        }

//
//        Log.d("Test change", "on month change called 2");
//        List<Timetable.Results> results = list.getResults();
//        for (int i = 0; i < list.getResults().size(); i++) {
//            Timetable.Results temp = results.get(i);
//            String start = temp.getStart();
//
//        }

        return matchedEvents;

    }

    private void fetchData(int newYear, int newMonth) {

        setUpVariables(newMonth, newYear);
        if (calledByBatch) {
            Log.d("Test called", "called by batch");
            Model.setBatchUrl();
            Url = Model.getBatchUrl();
        } else {
            // replace with Student Url;
            Model.setBatchUrl();
            Url = Model.getBatchUrl();
        }
        Log.d("Test URL", Url);
        CustomGetRequest customGetRequest = new CustomGetRequest(Request.Method.GET, Url, new com.android.volley.Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                Gson gson = new Gson();
                json = response.toString();
                list = gson.fromJson(json, Timetable.class);
                List<Timetable.Results> results = list.getResults();
                Log.d("Test results", results.size() + "");
                Log.d("Test results", json);
                for (int i = 0; i < list.getResults().size(); i++) {
                    Timetable.Results result = results.get(i);
                    events.add(result.toWeekViewEvent());

        }
                getWeekView().notifyDatasetChanged();

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

//    private void getData(int newYear, int newMonth) {
//
//        Log.d("Test results", "matched");
//
//        for (WeekViewEvent event : events) {
//            if (eventMatches(event, newYear, newMonth)) {
//                matchedEvents.add(event);
//            }
//        }
//
//
//        List<Timetable.Results> results = list.getResults();
//        for (int i = 0; i < list.getResults().size(); i++) {
//            Timetable.Results temp = results.get(i);
//            String start = temp.getStart();
//
//        }
//        Log.d("Test results", "matched 1");
//
//    }

    private void setUpVariables(int newMonth, int newYear) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, newMonth - 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.YEAR, newYear);
        int max = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        Model.setStart_date(newYear + "-" + newMonth + "-" + 1);
        Model.setEnd_date(newYear + "-" + newMonth + "-" + max);

    }

    /**
     * Checks if an event falls into a specific year and month.
     *
     * @param //event The event to check for.
     * @param //year  The year.
     * @param //month The month.
     * @return True if the event matches the year and month.
     */
    private boolean eventMatches(WeekViewEvent event, int year, int month) {
        return (event.getStartTime().get(Calendar.YEAR) == year && event.getStartTime().get(Calendar.MONTH) == month - 1) || (event.getEndTime().get(Calendar.YEAR) == year && event.getEndTime().get(Calendar.MONTH) == month - 1);
    }

    @Override
    public void success(List<Event> events, Response response) {
//        this.events.clear();
//
//
//        for (Event event : events) {
//            this.events.add(event.toWeekViewEvent());
//        }
//        getWeekView().notifyDatasetChanged();

        //Log.d("Test", "on success called" + events.size());
    }

    @Override
    public void failure(RetrofitError error) {
//        error.printStackTrace();
//        Toast.makeText(getContext(), R.string.async_error, Toast.LENGTH_SHORT).show();
    }


        public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getContext().getAssets().open("timetable.json");
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("Test async", "on view created asyn");
        events.clear();
        map.clear();
//        List<Timetable.Results> results = list.getResults();
//        for (int i = 0; i < list.getResults().size(); i++) {
//            Timetable.Results result = results.get(i);
//            events.add(result.toWeekViewEvent());
//
//        }
//        getWeekView().notifyDatasetChanged();
    }


    @Override
    public void OnSwitchToNextFragment(FragmentManager fm) {

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("Test", "on resume asyn");
        events.clear();
        map.clear();
        if (savedEvents.size() > 0) {
            matchedEvents = savedEvents;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("Test", "on Saved instance state");
        savedEvents = matchedEvents;
    }
}
