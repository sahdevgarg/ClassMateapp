package com.htlconline.sm.classmate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.htlconline.sm.classmate.pojo.AttendancePojo;
import com.htlconline.sm.classmate.volley.MyJsonRequest;

import org.json.JSONObject;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by M82061 on 3/2/2017.
 */

public class ReportCardFragment extends Fragment implements View.OnClickListener, MyJsonRequest.OnServerResponse, OnRecyclerItemClick {

    private View rootView;
    private ImageView previousMonth;
    private ImageView nextMonth;
    private TextView noDataText;
    private TextView monthName;
    private Calendar currentCalendar;
    private RecyclerView reportCardRecyclerView;
    private ArrayList<AttendancePojo.Result> reportCardArraylist;
    private ReportCardRecyclerViewAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.attendance_layout, container, false);

        reportCardRecyclerView = (RecyclerView)rootView.findViewById(R.id.attendanceRecyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        reportCardRecyclerView.setLayoutManager(mLayoutManager);

        previousMonth = (ImageView)rootView.findViewById(R.id.previousMonth);
        nextMonth = (ImageView)rootView.findViewById(R.id.nextMonth);

        noDataText = (TextView)rootView.findViewById(R.id.noData);

        monthName = (TextView)rootView.findViewById(R.id.monthName);
        monthName.setText(getCurrentMonth());

        previousMonth.setOnClickListener(this);
        nextMonth.setOnClickListener(this);

        currentCalendar = Calendar.getInstance();






        getReportCardData();
        //getCurrentMonthData();


        return rootView;
    }

    private void getReportCardData() {

        SessionManagement sessionManagement = new SessionManagement(getActivity());
        String studentId = sessionManagement.getStudentId();

        //String url = "http://10.140.64.90/api/student/attendance/?student_id="+studentId+"&batch_id=&"+getCurrentMonthDataForAPI(currentCalendar);
        String url = Config.ATTENDANCE_URL+"?student_id="+studentId+"&batch_id=&"+getCurrentMonthDataForAPI(currentCalendar);
        Log.d("Test URL", url);
        MyJsonRequest myJsonRequest = new MyJsonRequest(getActivity(), this);
        myJsonRequest.getJsonFromServer(Config.ATTENDANCE_URL, url, true, false);
    }

    private String getCurrentMonthDataForAPI(Calendar currentCalendar) {
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.nextMonth:
                String nextMonth = getNextMonth(currentCalendar);
                monthName.setText(nextMonth);
                getReportCardData();
                break;
            case R.id.previousMonth:
                String prevMonth = getPreviousMonth(currentCalendar);
                monthName.setText(prevMonth);
                getReportCardData();
                break;
        }
    }

    @Override
    public void getJsonFromServer(boolean flag, String tag, JSONObject jsonObject, String error) {
        try {

            if(flag){
                Gson gson = new Gson();
                AttendancePojo attendancePojo = gson.fromJson(jsonObject.toString(), AttendancePojo.class);
                reportCardArraylist = attendancePojo.getResults();

                if(reportCardArraylist.size() > 0) {
                    noDataText.setVisibility(View.GONE);
                    reportCardRecyclerView.setVisibility(View.VISIBLE);
                    mAdapter = new ReportCardRecyclerViewAdapter(getActivity(), this, reportCardArraylist);
                    reportCardRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    reportCardRecyclerView.setAdapter(mAdapter);
                }else {
                    reportCardRecyclerView.setVisibility(View.GONE);
                    noDataText.setVisibility(View.VISIBLE);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void getJsonFromServer(boolean flag, String tag, String stringObject, String error) {

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

    @Override
    public void onItemClick(int position) {

    }
}
