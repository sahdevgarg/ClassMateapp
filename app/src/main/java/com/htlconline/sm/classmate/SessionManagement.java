package com.htlconline.sm.classmate;

import android.content.Context;
import android.content.SharedPreferences;

import com.htlconline.sm.classmate.pojo.LoginPojo;

import java.util.ArrayList;

/**
 * Created by Shikhar Garg on 26-12-2016.
 */
public class SessionManagement {
    private static SessionManagement instance;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    private String studentId;

    public SessionManagement(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(Config.PREF_NAME, Config.PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void setcookie(String cookie) {
        editor.putString(Config.SET_COOKIE_KEY, cookie);
        editor.commit();
    }

    public boolean get_login() {
        return sharedPreferences.getBoolean(Config.IS_LOG_IN, false);
    }

    public void set_login(boolean status,String userName,String userId,String userRole,String userNameId) {
        editor.putBoolean(Config.IS_LOG_IN, status);
        editor.commit();

        if(status) {
            setUserName(userName);
            setUserId(userId);
            setUserRole(userRole);
            setUserNameId(userNameId);
        }else {
            setUserName("");
            setUserId("");
            setUserRole("");
            setUserNameId("");
        }
    }

    public boolean get_otp() {
        return sharedPreferences.getBoolean(Config.IS_OTP_DONE, false);
    }

    public void set_otp(boolean status) {
        editor.putBoolean(Config.IS_OTP_DONE, status);
        editor.commit();
    }

    public boolean get_kyc() {
        return sharedPreferences.getBoolean(Config.IS_KYC_DONE, false);
    }

    public void set_kyc(boolean status) {
        editor.putBoolean(Config.IS_KYC_DONE, status);
        editor.commit();
    }

    public boolean get_is_student() {
        return sharedPreferences.getBoolean(Config.IS_STUDENT, false);
    }

    public void set_is_student(boolean status) {
        editor.putBoolean(Config.IS_STUDENT, status);
        editor.commit();
    }

    public String get_cookie() {
        return sharedPreferences.getString(Config.SET_COOKIE_KEY, "");
    }

    public void setUserName(String userName) {
        editor.putString(Config.SET_USER_NAME, userName);
        editor.commit();
    }

    public String getUserName() {
        return sharedPreferences.getString(Config.SET_USER_NAME, "");
    }

    public void setUserId(String userId) {
        editor.putString(Config.SET_USER_ID, userId);
        editor.commit();
    }

    public String getUserId() {
        return sharedPreferences.getString(Config.SET_USER_ID, "");
    }

    public void setUserRole(String role) {
        editor.putString(Config.SET_USER_ROLE, role);
        editor.commit();
    }

    public String getUserRole() {
        return sharedPreferences.getString(Config.SET_USER_ROLE, "");
    }

    public void setStudentId(String studentId) {
        editor.putString(Config.SET_STUDENT_ID, studentId);
        editor.commit();
    }

    public String getStudentId() {
        return sharedPreferences.getString(Config.SET_STUDENT_ID, "");
    }

    public void setUserNameId(String userName) {
        editor.putString(Config.SET_USER_NAME_ID, userName);
        editor.commit();
    }

    public String getUserNameId() {
        return sharedPreferences.getString(Config.SET_USER_NAME_ID, "");
    }

    public void setNoOfStudents(int noOfStudents) {
        editor.putInt(Config.NO_OF_STUDENTS, noOfStudents);
        editor.commit();
    }

    public int getNoOfStudents() {
        return sharedPreferences.getInt(Config.NO_OF_STUDENTS, 0);
    }

    public void setJsonResponse(String response) {
        editor.putString(Config.JSON_RESPONSE, response);
        editor.commit();
    }

    public String getJsonResponse() {
        return sharedPreferences.getString(Config.JSON_RESPONSE, "");
    }

    public void setActiveStudent(int position) {
        editor.putInt(Config.ACTIVE_STUDENT, position);
        editor.commit();
    }

    public int getActiveStudent() {
        return sharedPreferences.getInt(Config.ACTIVE_STUDENT, 0);
    }

}
