package com.htlconline.sm.classmate;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by M82061 on 3/2/2017.
 */

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView profileName;
    private Button logoutButton;
    private SessionManagement sessionManagement;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("My Profile");


        sessionManagement = new SessionManagement(getApplicationContext());

        profileName = (TextView)findViewById(R.id.userProfileName);
        logoutButton = (Button)findViewById(R.id.logout);

        profileName.setText("Welcome, "+sessionManagement.getUserName());

        logoutButton.setOnClickListener(this);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.logout:
                sessionManagement.set_login(false,"","","","");
                Intent intent = new Intent(ProfileActivity.this,SplashScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
        }
    }
}
