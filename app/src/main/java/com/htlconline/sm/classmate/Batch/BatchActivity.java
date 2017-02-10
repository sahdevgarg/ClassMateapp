package com.htlconline.sm.classmate.Batch;

import android.content.Context;
import android.support.design.widget.TabLayout;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;

import com.htlconline.sm.classmate.Batch.Adapters.BatchCustomPagerAdapter;
import com.htlconline.sm.classmate.Batch.Adapters.BatchPagerAdapter;
import com.htlconline.sm.classmate.R;


public class BatchActivity extends AppCompatActivity {
    private static BatchCustomPagerAdapter viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private LinearLayout linearLayout;
    private static FragmentManager fragmentManager;
    public static Context context;
    private CheckedTextView mToolbarToggle;
    private Bundle bundle;
    public static String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch);
        viewPager = (BatchCustomPagerAdapter) findViewById(R.id.pager);
        context = BatchActivity.this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        linearLayout = (LinearLayout) findViewById(R.id.batch_list_container);
        tabLayout = (TabLayout) findViewById(R.id.scrollable_tab);
        fragmentManager = getSupportFragmentManager();
        viewPager.setAdapter(new BatchPagerAdapter(fragmentManager, context));
        bundle = getIntent().getExtras();
        title = bundle.getString("title");
        tabLayout.setupWithViewPager(viewPager);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public static void swipeOn() {
        viewPager.setSwipeable(true);
    }

    public static void swipeOff() {
        viewPager.setSwipeable(false);
    }


}

