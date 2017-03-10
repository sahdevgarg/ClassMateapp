package com.htlconline.sm.classmate;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.htlconline.sm.classmate.Batch.BatchFragments.BatchListFragment;
import com.htlconline.sm.classmate.chat.ContactListActivity;
import com.htlconline.sm.classmate.pojo.LoginPojo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private Toolbar toolbar;
    private SessionManagement sessionManagement;
    private TextView userName;
    private TextView toolbarText;
    private LinearLayout headerLayout;
    private FloatingActionButton fab;
    private TextView userId;
    private ArrayList<LoginPojo.Student> studentArrayList;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_activity);
        sessionManagement = new SessionManagement(getApplicationContext());
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarText = (TextView) findViewById(R.id.toolBarTitle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        init();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        addSliderMenu(navigationView);

        userName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.userName);
        userName.setText(sessionManagement.getUserName());

        userId = (TextView) navigationView.getHeaderView(0).findViewById(R.id.textView);
        userId.setText(sessionManagement.getUserNameId());

        headerLayout = (LinearLayout) navigationView.getHeaderView(0).findViewById(R.id.headerLayout);
        headerLayout.setOnClickListener(this);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

    public void init() {
        //actionBar.setTitle("HOME");
        toolbarText.setVisibility(View.VISIBLE);
        toolbarText.setText("Dashboard");
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment;
        fragment = new DashboardFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
////        if (id == R.id.action_settings) {
//////            Intent settingIntent = new Intent(MainActivity.this,SettingsActivity.class);
//////            startActivity(settingIntent);
//////            overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
////            return true;
////        }
//        if (id == R.id.action_logout) {
//            sessionManagement.set_login(false,"","","");
//            Intent intent = new Intent(MainActivity.this,SplashScreen.class);
//            startActivity(intent);
//            finish();
//            return true;
//        }
//
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();
        DrawerLayout drawer;
        Fragment fragment;

        switch (id) {
            case 1:
                //toolbar.setTitle(item.getTitle());
                toolbarText.setVisibility(View.VISIBLE);
                toolbarText.setText(item.getTitle());
                fragment = new DashboardFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment)
                        .commit();
                drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;

            case 2:
                //toolbar.setTitle(item.getTitle());
                toolbarText.setVisibility(View.VISIBLE);
                toolbarText.setText(item.getTitle());
                fragment = new ProgramFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment)
                        .commit();
                drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;

            case 3:
                //toolbar.setTitle(item.getTitle());
                toolbarText.setVisibility(View.VISIBLE);
                toolbarText.setText(item.getTitle());
                fragment = new BatchListFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment, "batch_list_fragment")
                        .commit();

                drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;

            case 4:
                //toolbar.setTitle(item.getTitle());
                toolbarText.setVisibility(View.VISIBLE);
                toolbarText.setText(item.getTitle());
                fragment = new TimeTableFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment)
                        .commit();
                drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;

            case 5:
                //toolbar.setTitle(item.getTitle());
                toolbarText.setVisibility(View.VISIBLE);
                toolbarText.setText(item.getTitle());
                fragment = new AttendanceFragmentNew();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment)
                        .commit();
                drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;

            case 6:
                //toolbar.setTitle(item.getTitle());
                toolbarText.setVisibility(View.VISIBLE);
                toolbarText.setText(item.getTitle());
                fragment = new ReportCardFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment)
                        .commit();
                drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;

            case 7:
                toolbarText.setVisibility(View.VISIBLE);
                toolbarText.setText(item.getTitle());
                fragment = new PtmFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment)
                        .commit();
                drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;

            case 8:
                //toolbar.setTitle(item.getTitle());
                showSwitchStudentsDialog();
                drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;

            case 9:
                //toolbar.setTitle(item.getTitle());
                toolbarText.setVisibility(View.VISIBLE);
                toolbarText.setText(item.getTitle());
                fragment = new AnnouncementFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment)
                        .commit();
//                Intent intent = new Intent(MainActivity.this,ContactListActivity.class);
//                intent.putExtra("name", "Preet");
//                intent.putExtra("userId", "101FBD00007");
//                startActivity(intent);

                drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;

            case 10:
                //toolbar.setTitle(item.getTitle());
                toolbarText.setVisibility(View.VISIBLE);
                toolbarText.setText(item.getTitle());
//                final String userName = sessionManagement.getUserName();
//                final String studentId = sessionManagement.getUserNameId();
//
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        Intent intent = new Intent(MainActivity.this,ContactListActivity.class);
//                        intent.putExtra("name", userName);
//                        intent.putExtra("userId", studentId);
//                        startActivity(intent);
//
//                        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
//                    }
//                }, 100);


                drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;

        }

//        if (id == R.id.nav_camera) {
//            Log.i("student", "Student0");
//            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.container,new StudentListingFragment());
//            ft.commit();
//            Log.i("student", "Student2");
//        } else
//        if (id == R.id.nav_gallery) {
//            startActivity(new Intent(MainActivity.this, BatchMainActivity.class));
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//            new SessionManagement(getApplicationContext()).set_login(false);
//            startActivity(new Intent(MainActivity.this,Login.class));
//            finish();
//
//        }
        return true;

    }

    private void addSliderMenu(NavigationView navigationView) {
        menu = navigationView.getMenu();
        String[] mainArray = {"Dashboard", "Programs", "Batches", "Timetable", "Attendance", "Report Card", "PTM"};
        int[] icon = {R.drawable.ic_laptop_windows_black_24dp, R.drawable.programs, R.drawable.batches, R.drawable.calendar, R.drawable.attendance, R.drawable.report_card, R.drawable.ptm};
        int size = mainArray.length;

        String[] collabSubmenuArray = {"Announcements", "Messages"};
        int[] collab_icon = {R.drawable.announcements, R.drawable.messages};
        int collabSize = collabSubmenuArray.length;


        String role = sessionManagement.getUserRole();
        if (role.equalsIgnoreCase(Config.Role.STUDENT)) {
            size = size - 1;
        }
//        else if(role.equalsIgnoreCase(Config.Role.PARENT) && sessionManagement.getNoOfStudents() < 2){
//            size = size - 1;
//        }

//        if (Utils.getIntSharedPreference(Constant.ROLE_ID, BaseActivity.this, 0) == 2 || Utils.getIntSharedPreference(Constant.ROLE_ID, BaseActivity.this, 0) == 3) {
//            size = size - 1;
//        }
        for (int i = 0; i < size; i++) {
            MenuItem menuItem = menu.add(0, (i + 1), Menu.NONE, mainArray[i]);
            Drawable drawable = getResources().getDrawable(icon[i]);
            menuItem.setIcon(drawable);
            //applyFontToMenuItem(menuItem);

        }

        if (role.equalsIgnoreCase(Config.Role.PARENT) && sessionManagement.getNoOfStudents() > 1) {
            SubMenu subMenu = menu.addSubMenu("Switch");
            MenuItem menuItem = subMenu.add(0, mainArray.length + 1, Menu.NONE, "Switch Students");
            Drawable drawable = getResources().getDrawable(R.drawable.switch_students);
            menuItem.setIcon(drawable);


            subMenu.setGroupCheckable(0, true, true);
            menuItem.setCheckable(false);
        }

        SubMenu collaborationSubMenu = menu.addSubMenu("Collaboration");
        for (int i = 0; i < collabSize; i++) {
            MenuItem collabaMenuItem = collaborationSubMenu.add(0, ((mainArray.length + 1) + 1) + i, Menu.NONE, collabSubmenuArray[i]);
            Drawable collabDrawable = getResources().getDrawable(collab_icon[i]);
            collabaMenuItem.setIcon(collabDrawable);
            //applyFontToMenuItem(menuItem);

        }

        //applyFontToMenuItem(menuItem);

        //MenuItem menuItem = menu.add(1,8,Menu.,"Switch");
        //MenuItem menuItem = menu.add(1, 10, Menu.NONE, "Logout");
        //menuItem.setIcon(getResources().getDrawable(R.drawable.ic_logout));
        //applyFontToMenuItem(menuItem);


        menu.setGroupCheckable(0, true, true);
        //menu.setGroupCheckable(1, true, true);
        collaborationSubMenu.setGroupCheckable(0, true, true);

        menu.getItem(0).setChecked(true);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.headerLayout:
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                        startActivity(intent);

                        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                    }
                }, 100);

                break;
            case R.id.fab:

                final String userName = sessionManagement.getUserName();
                final String studentId = sessionManagement.getUserNameId();

                Intent intent = new Intent(MainActivity.this, ContactListActivity.class);
                intent.putExtra("name", userName);
                intent.putExtra("userId", studentId);
                startActivity(intent);
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);

                break;
        }
    }

    public void showSwitchStudentsDialog(){
        try {
        String response = sessionManagement.getJsonResponse();
        JSONObject jsonObject = new JSONObject(response);
        Gson gson = new Gson();
        LoginPojo loginPojo = gson.fromJson(jsonObject.toString(), LoginPojo.class);

            if(loginPojo.getRole().get(0).toString().equalsIgnoreCase(Config.Role.PARENT)){
                studentArrayList = new ArrayList<>();
                studentArrayList = loginPojo.getStudents();
            }
            String[] items = new String[studentArrayList.size()];

            for(int i = 0; i < studentArrayList.size(); i++){
                items[i] = studentArrayList.get(i).getStudentName();
            }

        AlertDialog levelDialog;

// Strings to Show In Dialog with Radio Buttons

        // Creating and Building the Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this,android.R.style.Theme_Material_Light_Dialog_NoActionBar_MinWidth);
        builder.setTitle("Switch Students");
        builder.setSingleChoiceItems(items, sessionManagement.getActiveStudent(), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {


                switch(item)
                {
                    case 0:
                        // Your code when first option seletced
                        sessionManagement.setActiveStudent(item);
                        sessionManagement.setStudentId(studentArrayList.get(item).getStudentId());



                        break;
                    case 1:
                        // Your code when 2nd  option seletced
                        sessionManagement.setActiveStudent(item);
                        sessionManagement.setStudentId(studentArrayList.get(item).getStudentId());
                        break;
                }

                Fragment fragment = new DashboardFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, fragment)
                        .commit();
                menu.getItem(0).setChecked(true);
                dialog.dismiss();
            }
        });

        levelDialog = builder.create();
        levelDialog.show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
