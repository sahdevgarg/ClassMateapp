package com.htlconline.sm.classmate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.htlconline.sm.classmate.pojo.LoginPojo;
import com.htlconline.sm.classmate.volley.MyJsonRequest;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Shikhar Garg on 19-12-2016.
 */
public class SplashScreen extends AppCompatActivity implements MyJsonRequest.OnServerResponse {
    public String pass;
    public String user;
    SessionManagement sessionManagement;
    private Animation animation;
    private Animation animation2;
    private ProgressBar progressBar;
    private EditText password;
    private EditText username;
    private boolean isLoggedin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_screen);
        sessionManagement = new SessionManagement(getApplicationContext());
//        sessionManagement=new SessionManagement(getApplicationContext());
//        Thread logoTimer = new Thread() {
//            public void run(){
//                try{
//                    int logoTimer = 0;
//                    while(logoTimer < 3000){
//                        sleep(100);
//                        logoTimer = logoTimer +100;
//                    };
//
//                    //take user to dashboard
//                    if(sessionManagement.get_login()){
//                        //for thr time being
//                        sessionManagement.set_is_student(true);
//                        sessionManagement.set_kyc(true);
//
//                       if((sessionManagement.get_is_student() && sessionManagement.get_kyc())|| (!sessionManagement.get_is_student())){
//                           startActivity(new Intent(SplashScreen.this, MainActivity.class));
//                       }
//                    }
//
//                    //take user to login screen
//                    if(!sessionManagement.get_login()){
//                        startActivity(new Intent(SplashScreen.this, Login.class));
//                    }
//
//                    //take to the kyc screen
//                    //change activity name according to main kyc and this takes to kyc if not done
//                    if(sessionManagement.get_is_student() && !sessionManagement.get_kyc()){
//                        startActivity(new Intent(SplashScreen.this, MapsActivityKyc.class));
//                    }
//
//                }
//                catch (InterruptedException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//                finally{
//                    finish();
//                }
//            }
//        };
//
//        logoTimer.start();
        if (!sessionManagement.get_login()) {
            animation = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.new_anim);
            animation2 = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.img_splash);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Do something after 100ms
                    RelativeLayout imageView = (RelativeLayout) findViewById(R.id.rel);
                    imageView.setVisibility(View.VISIBLE);
                    imageView.startAnimation(animation2);

                    ImageView logo = (ImageView) findViewById(R.id.logo);
                    logo.startAnimation(animation);
                }
            }, 2000);

            progressBar = (ProgressBar) findViewById(R.id.progress);
            progressBar.setVisibility(View.GONE);
            username = (EditText) findViewById(R.id.userName);
            //username.setText(Utils.getStringSharedPreference(Constant.USER_NAME, this, ""));
            password = (EditText) findViewById(R.id.password);
//            username.setTypeface(Typeface.createFromAsset(getAssets(), "opensans_regular.ttf"));
//            password.setTypeface(Typeface.createFromAsset(getAssets(), "opensans_regular.ttf"));
            Button submit = (Button) findViewById(R.id.submit);
            submit.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    user = username.getText().toString();
                    pass = password.getText().toString();
                    if (user.trim().length() < 1) {
                        Toast.makeText(SplashScreen.this, "Please enter user name.", Toast.LENGTH_SHORT).show();
                    } else if (pass.trim().length() < 1) {
                        Toast.makeText(SplashScreen.this, "Please enter password.", Toast.LENGTH_SHORT).show();
                    } else {
                        login(user, pass);
                    }
                }
            });

        } else {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Do something after 100ms
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 2000);
        }

    }

    public void login(String user, String password) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("username", user);
        map.put("password", pass);
        //Log.i("Login_checking", user + " " + pass);
//        UserActivityHandler userActivityHandler=new UserActivityHandler(this);
//        userActivityHandler.postUserLogin(Config.LOGIN_URL, map);

        MyJsonRequest loginPostRequest = new MyJsonRequest(this, this);
        loginPostRequest.postRequest(Config.LOGIN_URL, Config.LOGIN_URL, true, map, "");
    }

//    @Override
//    public void onSuccess(HashMap<String, Object> map) {
//        LoginResponse res=(LoginResponse)map.get("LoginResponse");
//        String error_message=res.getError();
//        Log.i("response_msg",res.getSuccess()+" "+ res.getError());
//        if(res.getSuccess().equalsIgnoreCase("true")){
//            handle_login_success();
//        }
//        else{
//            handle_login_failure(error_message);
//        }
//    }

//    @Override
//    public void onFailure(HashMap<String, Object> map) {
//        Log.i("Login_checking","check5");
//    }
//
//    private void handle_login_failure(String msg) {
//        Snackbar.make(findViewById(R.id.normal_sign_in),msg, Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show();
//    }

//    private void handle_login_success(){
//        sessionManagement.set_login(true);
//        //just for time being
////        sm.set_is_student(true);
////        sm.set_kyc(true);
//
//        //add a check for student here from res after you get the response from api
//            /*
//                if(res.isStudent()){
//                  sm.set_is_Student(true);
//                 }
//                 if(res.isKycdone()){
//                    sm.set_kyc_done(true);
//                 }
//
//            /*/
//
//        //take to kyc screen
//        if(sessionManagement.get_is_student() && !sessionManagement.get_kyc()){
//            Intent intent=new Intent(this,MapsActivityKyc.class);
//            startActivity(intent);
//            finish();
//        }
//        //take to dahsboard
//        else{
////            Intent intent=new Intent(this,KYC_Activity.class);
////            startActivity(intent);
////            finish();
//            Intent intent=new Intent(this,MainActivity.class);
//            startActivity(intent);
//            finish();
//        }
//    }

    @Override
    public void getJsonFromServer(boolean flag, String tag, JSONObject jsonObject, String error) {

    }

    @Override
    public void getJsonFromServer(boolean flag, String tag, String stringObject, String error) {
        if (stringObject != null && !stringObject.equalsIgnoreCase("") && flag) {
            try {
                JSONObject jsonObject = new JSONObject(stringObject);
                Gson gson = new Gson();
                LoginPojo loginPojo = gson.fromJson(jsonObject.toString(), LoginPojo.class);
                if (loginPojo.getSuccess().equalsIgnoreCase("true")) {
                    sessionManagement.setJsonResponse(stringObject);
                    sessionManagement.set_login(true,loginPojo.getName(),loginPojo.getUserId(),loginPojo.getRole().get(0).toString(),loginPojo.getUser_name());
                    if(loginPojo.getRole().get(0).toString().equalsIgnoreCase(Config.Role.STUDENT)){
                        sessionManagement.setStudentId(loginPojo.getStudent_id());
                    }else if(loginPojo.getRole().get(0).toString().equalsIgnoreCase(Config.Role.PARENT)){
                        sessionManagement.setStudentId(loginPojo.getStudents().get(0).getStudentId());
                        sessionManagement.setNoOfStudents(loginPojo.getStudents().size());
                    }
//                    sessionManagement.setUserId(loginPojo.getUserId());
//                    sessionManagement.setUserName(loginPojo.getName());
//                    sessionManagement.setUserRole(loginPojo.getRole().get(0).toString());
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(SplashScreen.this, loginPojo.getError().toString(), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            Toast.makeText(SplashScreen.this, error + " ,Please try again.", Toast.LENGTH_SHORT).show();
        }
    }
}
