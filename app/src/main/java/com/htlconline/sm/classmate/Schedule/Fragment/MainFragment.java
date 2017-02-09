package com.htlconline.sm.classmate.Schedule.Fragment;


import android.Manifest;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.htlconline.sm.classmate.Batch.BatchPagerAdapter;
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

import com.htlconline.sm.classmate.interfaces.FragmentChangeListener;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
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

import static java.security.AccessController.getContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> , CustomAdapter.OnClickListItem {


    private static final String STATE_TOOLBAR_TOGGLE = "state:toolbarToggle";
    private static final int REQUEST_CODE_CALENDAR = 0;
    private static final int REQUEST_CODE_LOCATION = 1;
    private static final String SEPARATOR = ",";
    private static final int LOADER_CALENDARS = 0;
    private static final int LOADER_LOCAL_CALENDAR = 1;
    private FrameLayout view;
    private static int position;
    private static int max;
    private static final Coordinator mCoordinator = new Coordinator();
   // private View mCoordinatorLayout;
    private CheckedTextView mToolbarToggle;
    private EventCalendarView mCalendarView;
   // private Field childFragmentManager;
   // private FloatingActionButton mFabAdd;

   // private ActionBarDrawerToggle mDrawerToggle;
   // private DrawerLayout mDrawerLayout;
   // private View mDrawer;
    private final HashSet<String> mExcludedCalendarIds = new HashSet<>();

    private static LinearLayoutManager manager;
    private static RecyclerView recyclerView;
    private  static CustomAdapter customAdapter;
    private static List<CustomInfo> monthCalendar;
    private Timetable list;
    private static HashMap<String, Integer> map = new HashMap<String, Integer>();
    private  static Boolean control=true;
    private String json;
    private static String currMonth;
    private static String currYear;
    private static Context context;
    private static Map<String,List<Events>> setUpList = new HashMap<String, List<Events>>();
    private static FragmentChangeListener changeListener;
    private static FragmentManager fragmentManager;
    private PopupWindow pw;


    public MainFragment() {}

    public MainFragment(PagerAdapter.FirstPageListener listener) {changeListener = listener;}

    public MainFragment(BatchPagerAdapter.FirstPageListener listener) {
        changeListener = listener;
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
        if(MainFragment.this.getUserVisibleHint()) {

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

        json = loadJSONFromAsset();
        Gson gson= new Gson();
        this.list=gson.fromJson(json,Timetable.class);
        Calendar calendar = Calendar.getInstance();
        LoadData(calendar.getTimeInMillis());
        setupData();
        setUpPreferences();
    }

    private void setupData() {
        List<Timetable.Results> results= list.getResults();
        List<Events> eventsList;
        Events events;
        for(int i=0;i<list.getResults().size();i++)
        {
                Timetable.Results result = results.get(i);
                if(setUpList.containsKey(result.getFormattedDate()))
                {
                    eventsList = setUpList.get(result.getFormattedDate());

                }else
                {
                    eventsList = new ArrayList<>();

                }
            events = new Events();
            events.setEnd_time(result.getEndTime());
            events.setStart_time(result.getStartTime());
            events.setEvent_name(result.getClass_type());
            events.setEvent_venue(result.getCentre());
            eventsList.add(events);
            setUpList.put(result.getFormattedDate(),eventsList);
        }

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("Test ","Main Fragment View Created");
        setUpContentView(view);
        setHasOptionsMenu(true);
        if(MainFragment.this.getUserVisibleHint()) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");
            mToolbarToggle = (CheckedTextView) (getActivity()).findViewById(R.id.toolbar_toggle);
            mToolbarToggle.setVisibility(View.VISIBLE);

        }


//        mDrawerToggle.setDrawerIndicatorEnabled(true);
//        mDrawerToggle.syncState();
        context = getActivity();
        recyclerView= (RecyclerView) view.findViewById(R.id.custom_recycler_view);
        customAdapter= new CustomAdapter(getActivity(), monthCalendar, setUpList);
        customAdapter.setClickListener(this);
        recyclerView.setAdapter(customAdapter);
        manager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        recyclerView.scrollToPosition(position);
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
                    //apply check
                     if(control)
                    notifydatechanged();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }


        });

        mCalendarView.setVisibility(View.VISIBLE);
        control = true;

       // loadEvents();


    }

    public  List<CustomInfo> LoadData(long time) {
        monthCalendar = new ArrayList<CustomInfo>();

        SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        String formatted = format1.format(time);
        Date date= new Date();
        try {
             date = format1.parse(formatted);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int month = (calendar.get(Calendar.MONTH) + 1);
        int dd = (calendar.get(Calendar.DATE));
        int yer = (calendar.get(Calendar.YEAR));

        currMonth =""+month;
        currYear = ""+yer;

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH,month-1);
        cal.set(Calendar.DAY_OF_MONTH,1);
        cal.set(Calendar.YEAR,yer);
        max=cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //Log.d("Test day" ,""+ dd );
        int pos=0;
        CustomInfo info= new CustomInfo();
        info.setEvent("NONE");
        info.setDate(format1.format(cal.getTime()));
        monthCalendar.add(info);
        map.put(format1.format(cal.getTime()),pos++);

        for (int i = 1; i < max ; i++)
        {
            cal.set(Calendar.DAY_OF_MONTH, i + 1);
            if(i+1==dd)
            {
                position=i;
            }
            CustomInfo info1= new CustomInfo();
            info1.setEvent("");
            info1.setDate(format1.format(cal.getTime()));
            map.put(format1.format(cal.getTime()),pos++);
            monthCalendar.add(info1);
        }
        for (int i = 0; i < 4 ; i++)
       {
            CustomInfo info1= new CustomInfo();
            info1.setEvent("");
            info1.setDate("");
            monthCalendar.add(info1);
        }


        return monthCalendar;
    }




    private void notifydatechanged() throws ParseException {

        //apply check

        int position = manager.findFirstVisibleItemPosition();
        if (position < 0) {
            return;
        }
        CustomInfo info = monthCalendar.get(position);
        String date= info.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy",Locale.ENGLISH);
        Date date1 = sdf.parse(date);
        long milli=(date1.getTime());
        mCoordinator.sync(milli,recyclerView);

    }

    private  void customsync(long date)
    {

        control=false;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy",Locale.ENGLISH);
        String formatted =sdf.format(date);

        String parts[]= formatted.split("-");
        String month= parts[1];
        String year = parts[2];

        if(month!=(currMonth) && year!=(currYear))
        {
            map.clear();
            monthCalendar.clear();
            LoadData(date);
            customAdapter= new CustomAdapter(context,monthCalendar,setUpList);
            customAdapter.setClickListener(MainFragment.this);
            recyclerView.setAdapter(customAdapter);
            manager=new LinearLayoutManager(context);
            recyclerView.setLayoutManager(manager);
            manager.scrollToPositionWithOffset(position,0);

        }
        control=true;
        if(map.containsKey(sdf.format(date))) {
            int go_to_position = map.get(sdf.format(date));
            manager.scrollToPositionWithOffset(go_to_position,0);

        }


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
        switch (item.getItemId()){
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

        return  super.onOptionsItemSelected(item);
    }

    public void moveToFragment(int id){

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
        initiatePopupWindow(view, events, date);
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


            TextView eventDate;
            TextView eventName;
            TextView eventStart;
            TextView eventEnd;
            TextView eventVenue;
            ImageButton closeButton;
            eventDate = (TextView) layout.findViewById(R.id.detail_event_date);
            eventVenue = (TextView) layout.findViewById(R.id.detail_event_venue);
            eventName = (TextView) layout.findViewById(R.id.detail_event_name);
            eventStart = (TextView) layout.findViewById(R.id.detail_event_start);
            eventEnd = (TextView) layout.findViewById(R.id.detail_event_end);
            eventName.setText(events.getEvent_name());
            eventStart.setText(events.getStart_time());
            eventEnd.setText(events.getEnd_time());
            eventVenue.setText(events.getEvent_venue());
            eventDate.setText(date);
            closeButton = (ImageButton) layout.findViewById(R.id.close_button);
            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Toast.makeText(context,"hetjbljk",Toast.LENGTH_SHORT).show();
                    pw.dismiss();
                }
            });


        } catch (Exception e) {
            // Log.d("Test", "Exception Raised");
            e.printStackTrace();
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
         * @param textView      title
         * @param calendarView  calendar view
         *
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
            if(savedState!=null)
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
            if(mTextView!=null)
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
    public void onDetach() {
        super.onDetach();
        setUpList.clear();


    }

}
