package com.htlconline.sm.classmate.Schedule.widget;

/**
 * Created by Anurag on 15-01-2017.
 */

public class Model {

    private static String  batch_id="";
    private static String centre_id="";
    private static String subject_id="";
    private static String class_level_id="";
    private static String start_date;
    private static String end_date;

    private static String batchUrl;
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
        Model.batchUrl = "http://www.htlconline.com/api/timetable/" +
                "?batch_id=" + Model.batch_id + "&centre_id=" + Model.centre_id + "&subject_id=" + Model.subject_id +
                "&class_level_id=" + Model.class_level_id + "&start_date=" + Model.start_date + "&end_date=" + Model.end_date + "&view=agendaWeek";

    }
}
