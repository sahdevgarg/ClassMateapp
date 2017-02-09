package com.htlconline.sm.classmate.Batch;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckedTextView;

import com.htlconline.sm.classmate.Batch.BatchFragments.BatchListFragment;
import com.htlconline.sm.classmate.R;


public class BatchMainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private CheckedTextView mToolbarToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.batch_activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Fragment fragment = new BatchListFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.main_batch_layout,fragment,"batch_list_fragment");
        transaction.commit();
        mToolbarToggle = (CheckedTextView)findViewById(R.id.toolbar_toggle);
        mToolbarToggle.setVisibility(View.INVISIBLE);
    }

}












