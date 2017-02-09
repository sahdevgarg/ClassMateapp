package com.htlconline.sm.classmate.Student;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.htlconline.sm.classmate.R;
import com.htlconline.sm.classmate.Schedule.CustomPagerAdapter;
import com.htlconline.sm.classmate.Schedule.Fragment.AsynchronousFragment;
import com.htlconline.sm.classmate.Schedule.PagerAdapter;

import java.util.AbstractCollection;

/**
 * Created by Shikhar Garg on 29-12-2016.
 */
public class StudentDetailActivity extends AppCompatActivity{

    private  static CustomPagerAdapter viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private LinearLayout linearLayout;
    private static FragmentManager fragmentManager;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_detail);
        viewPager = (CustomPagerAdapter) findViewById(R.id.pager);
        context= StudentDetailActivity.this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        linearLayout = (LinearLayout) findViewById(R.id.batch_list_container);
        tabLayout= (TabLayout) findViewById(R.id.scrollable_tab);
        fragmentManager = getSupportFragmentManager();
        viewPager.setAdapter(new PagerAdapter(fragmentManager,context));
        tabLayout.setupWithViewPager(viewPager);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public static void swipeOn() {
        viewPager.setSwipeable(true);
    }

    public static void swipeOff() {
        viewPager.setSwipeable(false);
    }


//    private void setupviewpager() {
//
//        StudentViewPagerAdapter studentViewPagerAdapter=new StudentViewPagerAdapter(getSupportFragmentManager());
//       //studentViewPagerAdapter.addFragment(new StudentScheduleFragment(),"Schedule");//3 day view replace
//        studentViewPagerAdapter.addFragment(new AsynchronousFragment(), "Schedule");//3 day view replace
//        studentViewPagerAdapter.addFragment(new StudentAttendanceFragment(), "Attendance");
//        studentViewPagerAdapter.addFragment(new StudentReportFragment(),"Report");
//        viewpager.setAdapter(studentViewPagerAdapter);
//
//    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.previous_header_student_detail:
//                onBackPressed();
//                break;
//        }
//    }
//
//    public static void swipeOff() {
//        viewpager.setSwipeable(false);
//    }
}
