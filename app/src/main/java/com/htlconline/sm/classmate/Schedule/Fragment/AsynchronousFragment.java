package com.htlconline.sm.classmate.Schedule.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alamkanak.weekview.WeekViewEvent;
import com.google.gson.Gson;


import com.htlconline.sm.classmate.Batch.BatchActivity;
import com.htlconline.sm.classmate.Batch.BatchPagerAdapter;
import com.htlconline.sm.classmate.Schedule.Timetable;
import com.htlconline.sm.classmate.Schedule.apiclient.Event;
import com.htlconline.sm.classmate.Student.StudentDetailActivity;



import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import retrofit.Callback;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AsynchronousFragment extends BaseFragment implements Callback<List<Event>> {


    private List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
    boolean calledNetwork = false;
    private String json;
    private Timetable list;

    public  AsynchronousFragment()
    {super();
    }

   public AsynchronousFragment(com.htlconline.sm.classmate.Schedule.PagerAdapter.FirstPageListener listener) {
       super(listener);
          StudentDetailActivity.swipeOff();

    }

    public AsynchronousFragment(int id) {
       super(id);
    }

    public AsynchronousFragment(BatchPagerAdapter.FirstPageListener listener) {
        super(listener);
        BatchActivity.swipeOff();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        json = loadJSONFromAsset();
        Gson gson= new Gson();
        this.list=gson.fromJson(json,Timetable.class);
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
       List<WeekViewEvent> matchedEvents = new ArrayList<WeekViewEvent>();
        for (WeekViewEvent event : events) {
            if (eventMatches(event, newYear, newMonth)) {
                matchedEvents.add(event);
            }
        }


        //Log.d("Test", "on month change called");
        List<Timetable.Results> results =list.getResults();
        for(int i=0;i<list.getResults().size();i++)
        {
            Timetable.Results temp = results.get(i);
            String start = temp.getStart();

        }
      return matchedEvents;
    }

    /**
     * Checks if an event falls into a specific year and month.
     * @param //event The event to check for.
     * @param //year The year.
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
            Log.d("Test error" , String.valueOf(ex));
            return null;
        }
        return json;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Log.d("Test", "on view created");
        List<Timetable.Results> results= list.getResults();
        for(int i=0;i<list.getResults().size();i++)
        {
            Timetable.Results result = results.get(i);

            this.events.add(result.toWeekViewEvent());

        }
        getWeekView().notifyDatasetChanged();
    }


    @Override
    public void OnSwitchToNextFragment(FragmentManager fm) {

    }
}
