package com.htlconline.sm.classmate.Schedule.Fragment;


import android.content.Context;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.res.ResourcesCompat;
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
import com.htlconline.sm.classmate.Batch.BatchActivity;
import com.htlconline.sm.classmate.Batch.Adapters.BatchPagerAdapter;
import com.htlconline.sm.classmate.R;
import com.htlconline.sm.classmate.Schedule.MonthData.Events;
import com.htlconline.sm.classmate.Schedule.PagerAdapter;

import com.htlconline.sm.classmate.interfaces.FragmentChangeListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.htlconline.sm.classmate.Batch.BatchActivity.context;


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
    private PopupWindow pw;
    private static int counter =0;

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
        mToolbarToggle.setVisibility(View.GONE);


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

    @Override
    public void onEventLongPress(WeekViewEvent event, RectF eventRect) {
        //Toast.makeText(getContext(), "Long pressed event: " + event.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEmptyViewLongPress(Calendar time) {
        // Toast.makeText(getContext(), "Empty view long pressed: " + getEventTitle(time), Toast.LENGTH_SHORT).show();
    }

    public WeekView getWeekView() {
        return mWeekView;
    }


    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        Log.d("test", " on month change of base");
        return null;
    }


    @Override
    public void OnSwitchToNextFragment(FragmentManager fm) {

    }

    @Override
    public void OnSwitchToNextFragment(int id) {

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


    }
}
