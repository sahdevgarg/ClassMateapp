package com.htlconline.sm.classmate.Schedule.Fragment;


import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.Toast;

import com.alamkanak.weekview.DateTimeInterpreter;
import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.htlconline.sm.classmate.Batch.BatchActivity;
import com.htlconline.sm.classmate.Batch.BatchPagerAdapter;
import com.htlconline.sm.classmate.R;
import com.htlconline.sm.classmate.Schedule.PagerAdapter;

import com.htlconline.sm.classmate.Schedule.widget.Title;
import com.htlconline.sm.classmate.interfaces.FragmentChangeListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment implements FragmentChangeListener,
        WeekView.EventClickListener, MonthLoader.MonthChangeListener, WeekView.EventLongPressListener, WeekView.EmptyViewLongPressListener {


    private static final int TYPE_DAY_VIEW = 1;
    private static final int TYPE_THREE_DAY_VIEW = 2;
    private static final int TYPE_WEEK_VIEW = 3;
    private static FragmentChangeListener changeListener;
    private int mWeekViewType = TYPE_THREE_DAY_VIEW;
    private WeekView mWeekView;
    private View mView;
    private Bundle bundle;
    private Boolean saved = false;
    private int menu_id = -1;
    private MenuItem item;
    private boolean done = false;
    private String id;
    private FragmentManager fragmentManager;

    public BaseFragment() {
        // Required empty public constructor
    }


    public BaseFragment(PagerAdapter.FirstPageListener listener) {
        // Log.d("test", "base fragment called" + listener);
        changeListener = listener;
    }

    public BaseFragment(int id) {
        this.menu_id = id;
    }

    public BaseFragment(BatchPagerAdapter.FirstPageListener listener) {
        changeListener = listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getActivity().getSupportFragmentManager();

    }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        mView = inflater.inflate(R.layout.fragment_base, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Log.d("Test", "on view of base created");
        //setSupportActionBar((Toolbar) view.findViewById(R.id.toolbar));


        //getSupportActionBar().setDisplayShowTitleEnabled(false);


        //noinspection ConstantConditions

        // Get a reference for the week view in the layout.
        mWeekView = (WeekView) view.findViewById(R.id.weekView);

        // Show a toast message about the touched event.
        mWeekView.setOnEventClickListener(this);

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
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        //Log.d("Title",Title.title);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(BatchActivity.title);
        CheckedTextView mToolbarToggle = (CheckedTextView) getActivity().findViewById(R.id.toolbar_toggle);
        mToolbarToggle.setVisibility(View.INVISIBLE);



        inflater.inflate(R.menu.menu_base, menu);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saved = true;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(Title.title);
        //StudentDetailActivity toolbar

        //CheckedTextView mToolbarToggle = (CheckedTextView) getActivity().findViewById(R.id.toolbar_toggle);
        //mToolbarToggle.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        this.item = item;
        setupDateTimeInterpreter(id == R.id.action_week_view);
        switch (id) {
            case R.id.action_today:
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
                changeListener.OnSwitchToNextFragment(fragmentManager);
                return true;
        }

       return true;
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


    protected String getEventTitle(Calendar time) {
        return String.format("Event of %02d:%02d %s/%d", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.MONTH) + 1, time.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {
        Toast.makeText(getContext(), "Clicked " + event.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEventLongPress(WeekViewEvent event, RectF eventRect) {
        Toast.makeText(getContext(), "Long pressed event: " + event.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEmptyViewLongPress(Calendar time) {
        Toast.makeText(getContext(), "Empty view long pressed: " + getEventTitle(time), Toast.LENGTH_SHORT).show();
    }

    public WeekView getWeekView() {
        return mWeekView;
    }


    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        return null;
    }


    @Override
    public void OnSwitchToNextFragment(FragmentManager fm) {

    }

    @Override
    public void OnSwitchToNextFragment(int id) {

    }

}
