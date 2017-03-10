package com.htlconline.sm.classmate;

import android.content.Context;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
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
import android.widget.TextView;
import android.widget.Toast;

import com.alamkanak.weekview.DateTimeInterpreter;
import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.google.gson.Gson;
import com.htlconline.sm.classmate.Batch.Adapters.BatchPagerAdapter;
import com.htlconline.sm.classmate.Batch.BatchActivity;
import com.htlconline.sm.classmate.Schedule.Fragment.BaseFragment;
import com.htlconline.sm.classmate.Schedule.Timetable;
import com.htlconline.sm.classmate.Schedule.apiclient.Event;
import com.htlconline.sm.classmate.Schedule.widget.Model;
import com.htlconline.sm.classmate.interfaces.FragmentChangeListener;
import com.htlconline.sm.classmate.volley.MyJsonRequest;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.htlconline.sm.classmate.Batch.BatchActivity.context;

/**
 * Created by M82061 on 2/21/2017.
 */

public class TimeTableFragment extends Fragment implements Callback<List<Event>>,MyJsonRequest.OnServerResponse,WeekView.EventClickListener, MonthLoader.MonthChangeListener, WeekView.EventLongPressListener, WeekView.EmptyViewLongPressListener {
    private static List<WeekViewEvent> matchedEvents = new ArrayList<WeekViewEvent>();
    private HashMap<Pair<Integer, Integer>, Boolean> map = new HashMap<>();
    private static List<WeekViewEvent> savedEvents = new ArrayList<WeekViewEvent>();
    private static List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
    private String json;
    private static Timetable list;
    private String url;
    private View mView;
    private WeekView mWeekView;
    private int menu_id = -1;
    private MenuItem item;
    private static final int TYPE_DAY_VIEW = 1;
    private static final int TYPE_THREE_DAY_VIEW = 2;
    private static final int TYPE_WEEK_VIEW = 3;
    private int mWeekViewType = TYPE_THREE_DAY_VIEW;
    private PopupWindow pw;
    private static int counter =0;

    //private View rootView;

    public TimeTableFragment(){

    }

    public TimeTableFragment(int id){
        //goToCalenderViewType(id);
        this.menu_id = id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.timetable_layout, container, false);
        setHasOptionsMenu(true);
        return mView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

//        //Log.d("Title",Title.title);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(BatchActivity.title);
//        CheckedTextView mToolbarToggle = (CheckedTextView) getActivity().findViewById(R.id.toolbar_toggle);
//        if(mToolbarToggle != null) {
//            mToolbarToggle.setVisibility(View.GONE);
//        }

        inflater.inflate(R.menu.menu_base, menu);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        this.item = item;
        setupDateTimeInterpreter(id == R.id.action_week_view);
        switch (id) {
            case android.R.id.home:
                getActivity().finish();
                break;
            case R.id.action_today:
                mWeekView.goToHour(10);
                mWeekView.goToToday();
                return true;
            case R.id.action_day_view:
                goToDayView();
                return true;
            case R.id.action_three_day_view:
                goToThreeDayView();
                return true;
            case R.id.action_week_view:
                goToWeekView();
                return true;
            case R.id.action_agenda_view:
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                Fragment fragment = new TimeTableAgendaViewFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment)
                        .commit();
                //changeListener.OnSwitchToNextFragment(fragmentManager);
                return true;
        }

        return true;
    }


    public void goToCalenderViewType(int id){
        switch (id) {
            case android.R.id.home:
                getActivity().finish();
                break;
            case R.id.action_today:
                mWeekView.goToHour(10);
                mWeekView.goToToday();
                break;
            case R.id.action_day_view:
                goToDayView();
                break;
            case R.id.action_three_day_view:
                goToThreeDayView();
                break;
            case R.id.action_week_view:
                goToWeekView();
                break;
            case R.id.action_agenda_view:
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                Fragment fragment = new TimeTableAgendaViewFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment)
                        .commit();

                break;
        }
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
//        if (calledByBatch) {
//            Log.d("Test called", "called by batch");
//            Model.setBatchUrl();
//            Url = Model.getBatchUrl();
//        } else {
//            // replace with Student Url;
//            Model.setBatchUrl();
//            Url = Model.getBatchUrl();
//        }
        Model.setTimeTableUrl();
        url = Model.getTimeTableUrl();
        Log.d("Test URL", url);

        MyJsonRequest batchDetailJsonRequest = new MyJsonRequest(getActivity(),this);
        batchDetailJsonRequest.getJsonFromServer(Config.BATCH_DETAIL_URL,url,true,false);
//        CustomGetRequest customGetRequest = new CustomGetRequest(Request.Method.GET, Url, new com.android.volley.Response.Listener<JSONObject>() {
//
//            @Override
//            public void onResponse(JSONObject response) {
//
//                Gson gson = new Gson();
//                json = response.toString();
//                list = gson.fromJson(json, Timetable.class);
//                List<Timetable.Results> results = list.getResults();
//                Log.d("Test results", results.size() + "");
//                Log.d("Test results", json);
//                for (int i = 0; i < list.getResults().size(); i++) {
//                    Timetable.Results result = results.get(i);
//                    events.add(result.toWeekViewEvent());
//
//        }
//                getWeekView().notifyDatasetChanged();
//
//                //getData(currYear,currMonth);
//
//            }
//
//
//        },
//                new com.android.volley.Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        //Log.i("Student","Student7");
//                        error.printStackTrace();
//                    }
//                },getActivity());
//        //mRequestQueue.add(customGetRequest);
//        AppControllerOld.getInstance(getActivity()).getRequestQueue().add(customGetRequest);


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

        // Get a reference for the week view in the layout.
        mWeekView = (WeekView) view.findViewById(R.id.weekView);

        // Show a toast message about the touched event.
        mWeekView.setOnEventClickListener(this);

        mWeekView.setXScrollingSpeed(0.3f);

        // The week view has infinite scrolling horizontally. We have to provide the events of a
        // month every time the month changes on the week view.
        mWeekView.setMonthChangeListener(this);

        // Set long press listener for events.
        mWeekView.setEventLongPressListener(this);

        // Set long press listener for empty view
        mWeekView.setEmptyViewLongPressListener(this);

        // Set up a date time interpreter to interpret how the date and time will be formatted in
        // the week view. This is optional.
        mWeekView.goToHour(10);
        if (menu_id == R.id.action_day_view)
            goToDayView();
        else if (menu_id == R.id.action_three_day_view)
            goToThreeDayView();
        else if (menu_id == R.id.action_week_view)
            goToWeekView();
        setupDateTimeInterpreter(false);


        events.clear();
        map.clear();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View toggleButton = getActivity().findViewById(R.id.toolbar_toggle_frame);
        View toolbarTitle = getActivity().findViewById(R.id.toolBarTitle);

        toolbarTitle.setVisibility(View.VISIBLE);
        toggleButton.setVisibility(View.GONE);
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
                    counter++;
                    if (counter == 1) {
                        Log.d("Test resume", "resume called called 11");
                        if (pw != null)
                            if (pw.isShowing()) {
                                pw.dismiss();
                                changeStatusBarColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null));
                            } else getActivity().finish();
                        else getActivity().finish();
                    } else if (counter == 2) {
                        counter = 0;
                    }

                    return true;


                }
                return false;
            }
        });

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

    @Override
    public void getJsonFromServer(boolean flag, String tag, JSONObject jsonObject, String error) {
        try {
            if(flag){
                Gson gson = new Gson();
                json = jsonObject.toString();
                list = gson.fromJson(json, Timetable.class);
                List<Timetable.Results> results = list.getResults();
                Log.d("Test results", results.size() + "");
                Log.d("Test results", json);
                for (int i = 0; i < list.getResults().size(); i++) {
                    Timetable.Results result = results.get(i);
                    events.add(result.toWeekViewEvent());

                }
                getWeekView().notifyDatasetChanged();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void getJsonFromServer(boolean flag, String tag, String stringObject, String error) {

    }

    public void goToDayView() {
        if (mWeekViewType != TYPE_DAY_VIEW) {

            mWeekViewType = TYPE_DAY_VIEW;
            mWeekView.setNumberOfVisibleDays(1);
            mWeekView.goToHour(10);
            // Lets change some dimensions to best fit the view.
            mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
            mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
            mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
        }
    }

    public void goToThreeDayView() {
        if (mWeekViewType != TYPE_THREE_DAY_VIEW) {

            mWeekViewType = TYPE_THREE_DAY_VIEW;
            mWeekView.setNumberOfVisibleDays(3);
            mWeekView.goToHour(10);

            // Lets change some dimensions to best fit the view.
            mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
            mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
            mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
        }
    }

    public void goToWeekView() {
        if (mWeekViewType != TYPE_WEEK_VIEW) {

            mWeekViewType = TYPE_WEEK_VIEW;
            mWeekView.setNumberOfVisibleDays(7);
            mWeekView.goToHour(10);

            // Lets change some dimensions to best fit the view.
            mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
            mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 9, getResources().getDisplayMetrics()));
            mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 9, getResources().getDisplayMetrics()));
        }
    }


    private void setupDateTimeInterpreter(final boolean shortDate) {
        mWeekView.setDateTimeInterpreter(new DateTimeInterpreter() {
            @Override
            public String interpretDate(Calendar date) {
                SimpleDateFormat weekdayNameFormat = new SimpleDateFormat("EEE", Locale.getDefault());
                String weekday = weekdayNameFormat.format(date.getTime());
                SimpleDateFormat format = new SimpleDateFormat(" M/d", Locale.getDefault());

                // All android api level do not have a standard way of getting the first letter of
                // the week day name. Hence we get the first char programmatically.
                // Details: http://stackoverflow.com/questions/16959502/get-one-letter-abbreviation-of-week-day-of-a-date-in-java#answer-16959657
                if (shortDate)
                    weekday = String.valueOf(weekday.charAt(0));
                return weekday.toUpperCase() + format.format(date.getTime());
            }

            @Override
            public String interpretTime(int hour) {
                return hour > 11 ? (hour - 12) + " PM" : (hour == 0 ? "12 AM" : hour + " AM");
            }
        });
    }



    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {
        Toast.makeText(getContext(), "Clicked " + event.getName(), Toast.LENGTH_SHORT).show();
        String date = getDate(event.getStartTime().getTimeInMillis());
        Log.d("test time", date);
        initiatePopupWindow(getView(), event, date);

    }

    private String getDate(long startTime) {
        SimpleDateFormat originalFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        SimpleDateFormat targetFormat = new SimpleDateFormat("dd MMMM, yyyy",Locale.ENGLISH);
        Date date1 = null;
        try {
            date1 = originalFormat.parse(originalFormat.format(startTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return targetFormat.format(date1);

    }
    public  String getFormattedTime(long time)
    {
        SimpleDateFormat originalFormat = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
        SimpleDateFormat targetFormat = new SimpleDateFormat("hh:mm a",Locale.ENGLISH);
        Date date = null;
        try {
            date = originalFormat.parse(originalFormat.format(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return targetFormat.format(date);

    }

    public WeekView getWeekView() {
        return mWeekView;
    }

    private void initiatePopupWindow(View view, WeekViewEvent events, String date) {

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
            eventSubject = (TextView)layout.findViewById(R.id.detail_event_subject);
            changeStatusBarColor(events.getColor());
            frameLayout.setBackgroundColor(events.getColor());
            eventDate.setText(date);
            eventName.setText(events.getName());
            eventProduct.setText(events.getmProduct());
            eventInstructor.setText(events.getmInstructor());
            eventSubject.setText(events.getmSubject());
            eventStart.setText(getFormattedTime(events.getStartTime().getTimeInMillis()));
            eventEnd.setText(getFormattedTime(events.getEndTime().getTimeInMillis()));
            eventVenue.setText(events.getLocation());

            closeButton = (ImageButton) layout.findViewById(R.id.close_button);
            closeButton.setBackgroundColor(events.getColor());
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


    @Override
    public void onEmptyViewLongPress(Calendar time) {

    }

    @Override
    public void onEventLongPress(WeekViewEvent event, RectF eventRect) {

    }
}
