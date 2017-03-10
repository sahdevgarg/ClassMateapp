package com.htlconline.sm.classmate;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.htlconline.sm.classmate.pojo.AttendancePojo;
import com.htlconline.sm.classmate.volley.MyJsonRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by M82061 on 2/28/2017.
 */

public class AttendanceFragmentNew extends Fragment implements MyJsonRequest.OnServerResponse, OnRecyclerItemClick, View.OnClickListener {

    private View rootView;
    private RecyclerView attendanceRecyclerView;
    private AttendanceRecyclerViewAdapter mAdapter;
    private ArrayList<AttendancePojo.Result> attendanceArraylist;
    private ImageView previousMonth;
    private ImageView nextMonth;
    private TextView monthName;
    private Calendar currentCalendar;
    private TextView noDataText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.attendance_layout, container, false);

        attendanceRecyclerView = (RecyclerView)rootView.findViewById(R.id.attendanceRecyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        attendanceRecyclerView.setLayoutManager(mLayoutManager);

        previousMonth = (ImageView)rootView.findViewById(R.id.previousMonth);
        nextMonth = (ImageView)rootView.findViewById(R.id.nextMonth);

        noDataText = (TextView)rootView.findViewById(R.id.noData);

        monthName = (TextView)rootView.findViewById(R.id.monthName);
        monthName.setText(getCurrentMonth());

        previousMonth.setOnClickListener(this);
        nextMonth.setOnClickListener(this);

        currentCalendar = Calendar.getInstance();

        getAttendanceData();
        //getCurrentMonthData();
        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.attendance_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_all:

                return true;
            case R.id.action_batch:

                Intent filterIntent = new Intent(getActivity(),FilterActivity.class);
                startActivity(filterIntent);
                getActivity().overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                return true;
        }
        return true;
    }

    public void getAttendanceData(){
        SessionManagement sessionManagement = new SessionManagement(getActivity());
        String studentId = sessionManagement.getStudentId();

        //String url = "http://10.140.64.90/api/student/attendance/?student_id="+studentId+"&batch_id=&"+getCurrentMonthDataForAPI(currentCalendar);
        String url = Config.ATTENDANCE_URL+"?student_id="+studentId+"&batch_id=&"+getCurrentMonthDataForAPI(currentCalendar);
        //String url = "http://10.140.64.90/api/student/attendance/?student_id=124732&batch_id=&"+getCurrentMonthDataForAPI(currentCalendar);
        Log.d("Test URL", url);
        MyJsonRequest myJsonRequest = new MyJsonRequest(getActivity(), this);
        myJsonRequest.getJsonFromServer(Config.ATTENDANCE_URL, url, true, false);
    }

    public void getFilters(){
        String url = Config.ALLOWED_BATCH_LIST_URL;
        //String url = "http://10.140.64.90/api/student/attendance/?student_id=124732&batch_id=&"+getCurrentMonthDataForAPI(currentCalendar);
        Log.d("Test URL", url);
        MyJsonRequest myJsonRequest = new MyJsonRequest(getActivity(), this);
        myJsonRequest.getJsonFromServer(Config.ALLOWED_BATCH_LIST_URL, url, true,true);
    }

    @Override
    public void getJsonFromServer(boolean flag, String tag, JSONObject jsonObject, String error) {
        try {

            if(flag) {
                if (tag.equalsIgnoreCase(Config.ATTENDANCE_URL)) {
                    Gson gson = new Gson();
                    AttendancePojo attendancePojo = gson.fromJson(jsonObject.toString(), AttendancePojo.class);
                    attendanceArraylist = attendancePojo.getResults();

                    if (attendanceArraylist.size() > 0) {
                        noDataText.setVisibility(View.GONE);
                        attendanceRecyclerView.setVisibility(View.VISIBLE);
                        mAdapter = new AttendanceRecyclerViewAdapter(getActivity(), this, attendanceArraylist);
                        attendanceRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        attendanceRecyclerView.setAdapter(mAdapter);
                    } else {
                        attendanceRecyclerView.setVisibility(View.GONE);
                        noDataText.setVisibility(View.VISIBLE);
                    }
                }
            }else {

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void getJsonFromServer(boolean flag, String tag, String stringObject, String error) {

    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.nextMonth:
                String nextMonth = getNextMonth(currentCalendar);
                monthName.setText(nextMonth);
                getAttendanceData();
                break;
            case R.id.previousMonth:
                String prevMonth = getPreviousMonth(currentCalendar);
                monthName.setText(prevMonth);
                getAttendanceData();
                break;
        }
    }

    public String getCurrentMonthDataForAPI(Calendar currentCalendar){
        //Calendar calendar = Calendar.getInstance();
        //calendar.add(Calendar.MONTH, 1);
        currentCalendar.set(Calendar.DATE, currentCalendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        Date monthFirstDay = currentCalendar.getTime();
        currentCalendar.set(Calendar.DATE, currentCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date monthLastDay = currentCalendar.getTime();

        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        String startDate = formatter.format(monthFirstDay);
        String endDate = formatter.format(monthLastDay);
        Log.e("CurrentMonthData","First:"+startDate+"Last:"+endDate);

        String stringUppendToUrl = "start="+startDate+"&end="+endDate;

        return stringUppendToUrl;

    }

    public String getCurrentMonth(){
        currentCalendar = Calendar.getInstance();
        int currentMonth = currentCalendar.get(Calendar.MONTH);

        String monthName = Config.getMonthName(currentMonth);
        String year = String.valueOf(currentCalendar.get(Calendar.YEAR));

        String currentMonthAndYear = monthName+" "+year;
        return currentMonthAndYear;
    }

    public String getNextMonth(Calendar currentCalendar){
        currentCalendar.add(Calendar.MONTH,1);
        int currentMonth = currentCalendar.get(Calendar.MONTH);

        String monthName = Config.getMonthName(currentMonth);
        String year = String.valueOf(currentCalendar.get(Calendar.YEAR));

        String currentMonthAndYear = monthName+" "+year;
        return currentMonthAndYear;
    }

    public String getPreviousMonth(Calendar currentCalendar){
        currentCalendar.add(Calendar.MONTH,-1);
        int currentMonth = currentCalendar.get(Calendar.MONTH);

        String monthName = Config.getMonthName(currentMonth);
        String year = String.valueOf(currentCalendar.get(Calendar.YEAR));

        String currentMonthAndYear = monthName+" "+year;
        return currentMonthAndYear;
    }

    public void GotoToday(){
        String currentDate = "";
        currentCalendar = Calendar.getInstance();
        getAttendanceData();
        attendanceRecyclerView.scrollToPosition(getStringPos(currentDate));

    }

    private int getStringPos(String date) {
        return attendanceArraylist.indexOf(date);
    }
}
