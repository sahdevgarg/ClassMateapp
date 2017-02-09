package com.htlconline.sm.classmate.Attendance.StudentBatchDetail;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Shikhar Garg on 02-02-2017.
 */
public class StudentBatchDetailModel {
    private String next;

    public Results[] getResults() {
        return results;
    }

    public void setResults(Results[] results) {
        this.results = results;
    }

    private String prev;
    Results[] results;
    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrev() {
        return prev;
    }

    public void setPrev(String prev) {
        this.prev = prev;
    }

    public static class Results {
        @SerializedName("class_date")
        private String date;
        @SerializedName("class_type")
        private String class_type;
        @SerializedName("attendance_type")
        private String att_type;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getClass_type() {
            return class_type;
        }

        public void setClass_type(String class_type) {
            this.class_type = class_type;
        }

        public String getAtt_type() {
            return att_type;
        }

        public void setAtt_type(String att_type) {
            this.att_type = att_type;
        }
    }
}
