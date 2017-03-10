package com.htlconline.sm.classmate.Batch;

import android.content.Context;
import android.support.design.widget.TabLayout;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;

import com.htlconline.sm.classmate.Batch.Adapters.CustomViewPager;
import com.htlconline.sm.classmate.Batch.Adapters.BatchPagerAdapter;
import com.htlconline.sm.classmate.Config;
import com.htlconline.sm.classmate.R;


public class BatchActivity extends AppCompatActivity {
    private static CustomViewPager viewPager;
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
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
        setContentView(R.layout.activity_batch);
        viewPager = (CustomViewPager) findViewById(R.id.pager);
        context = BatchActivity.this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        linearLayout = (LinearLayout) findViewById(R.id.batch_list_container);
        tabLayout = (TabLayout) findViewById(R.id.scrollable_tab);
        fragmentManager = getSupportFragmentManager();
        viewPager.setAdapter(new BatchPagerAdapter(fragmentManager, context));
        bundle = getIntent().getExtras();
        title = bundle.getString(Config.BATCH_TITLE);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}

