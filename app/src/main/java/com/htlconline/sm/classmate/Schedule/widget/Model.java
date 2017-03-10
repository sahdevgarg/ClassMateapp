package com.htlconline.sm.classmate.Schedule.widget;

import android.content.Context;

import com.htlconline.sm.classmate.Config;
import com.htlconline.sm.classmate.SessionManagement;

/**
 * Created by Anurag on 15-01-2017.
 */

public class Model {

    public static String batch_id="";
    private static String centre_id="";
    private static String subject_id="";
    private static String class_level_id="";
    private static String start_date;
    private static String end_date;

    private static String batchUrl;
    private static String lessionPlanUrl;
    private static String timeTabeUrl;
    private static String attendanceUrl;
    private SessionManagement sessionManagement;

    public static void setBatch_id(String batch_id) {
        Model.batch_id = batch_id;
    }

    public static void setCentre_id(String centre_id) {
        Model.centre_id = centre_id;
    }

    public static void setSubject_id(String subject_id) {
        Model.subject_id = subject_id;
    }

    public static void setClass_level_id(String class_level_id) {
        Model.class_level_id = class_level_id;
    }

    public static void setStart_date(String start_date) {
        Model.start_date = start_date;
    }

    public static void setEnd_date(String end_date) {
        Model.end_date = end_date;
    }

    public static String getBatchUrl() {
        return batchUrl;
    }

    public static void setBatchUrl() {
        Model.batchUrl = Config.BATCH_DETAIL_URL +
                "?batch_id=" + Model.batch_id + "&centre_id=" + Model.centre_id + "&subject_id=" + Model.subject_id +
                "&class_level_id=" + Model.class_level_id + "&start_date=" + Model.start_date + "&end_date=" + Model.end_date + "&view=agendaWeek";

    }

    public static String getLessionPlanUrl(){
        return lessionPlanUrl;
    }

    public static void setLessionPlanUrl() {
        Model.lessionPlanUrl = Config.LESSION_PLAN_URL +
                "?batch_id=" + Model.batch_id;
    }

    public static String getTimeTableUrl(){
        return timeTabeUrl;
    }

    public static void setTimeTableUrl() {
        Model.timeTabeUrl = Config.BATCH_DETAIL_URL +
                "?batch_id=&centre_id=" + Model.centre_id + "&subject_id=" + Model.subject_id +
                "&class_level_id=" + Model.class_level_id + "&start_date=" + Model.start_date + "&end_date=" + Model.end_date + "&view=agendaWeek";

    }

//    public static String getAttendanceUrl(){
//        return attendanceUrl;
//    }
//
//    public void setAttendanceUrl(Context context) {
//        sessionManagement = new SessionManagement(context);
//        Model.attendanceUrl = Config.ATTENDANCE_URL + "?student_id="+sessionManagement.getStudentId()+"&batch_id="+Model.batch_id;
//    }
}
