package com.htlconline.sm.classmate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.htlconline.sm.classmate.LoginModule.Login;
import com.htlconline.sm.classmate.LoginModule.KYC.MapsActivityKyc;

/**
 * Created by Shikhar Garg on 19-12-2016.
 */
public class SplashScreen extends AppCompatActivity {
    SessionManagement sessionManagement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        sessionManagement=new SessionManagement(getApplicationContext());
        Thread logoTimer = new Thread() {
            public void run(){
                try{
                    int logoTimer = 0;
                    while(logoTimer < 3000){
                        sleep(100);
                        logoTimer = logoTimer +100;
                    };

                    //take user to dashboard
                    if(sessionManagement.get_login()){
                        //for thr time being
                        sessionManagement.set_is_student(true);
                        sessionManagement.set_kyc(true);

                       if((sessionManagement.get_is_student() && sessionManagement.get_kyc())|| (!sessionManagement.get_is_student())){
                           startActivity(new Intent(SplashScreen.this, MainActivity.class));
                       }
                    }

                    //take user to login screen
                    if(!sessionManagement.get_login()){
                        startActivity(new Intent(SplashScreen.this, Login.class));
                    }

                    //take to the kyc screen
                    //change activity name according to main kyc and this takes to kyc if not done
                    if(sessionManagement.get_is_student() && !sessionManagement.get_kyc()){
                        startActivity(new Intent(SplashScreen.this, MapsActivityKyc.class));
                    }

                }
                catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                finally{
                    finish();
                }
            }
        };

        logoTimer.start();
    }
}
