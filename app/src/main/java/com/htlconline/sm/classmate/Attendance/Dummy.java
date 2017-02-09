package com.htlconline.sm.classmate.Attendance;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.support.v7.app.AppCompatActivity;

import com.htlconline.sm.classmate.R;

/**
 * Created by Shikhar Garg on 02-02-2017.
 */
public class Dummy extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dummy_layout);
        StudentBatchListFragment frag=new StudentBatchListFragment();
        frag.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.container,frag).commit();
    }
}
