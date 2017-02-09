package com.htlconline.sm.classmate.Attendance;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Shikhar Garg on 02-02-2017.
 */
public class StudentBatchListModel {
//    private String batchname;
//    private String classname;
    @SerializedName("batch_id")
    private String batchid;
    @SerializedName("subject")
    private String subjectname;
    @SerializedName("attendance_percentage")
    private String att;
    @SerializedName("student_id")
    private String student_id;

    public String getBatchid() {
        return batchid;
    }

    public void setBatchid(String batchid) {
        this.batchid = batchid;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getSubjectname() {
        return subjectname;
    }

    public void setSubjectname(String subjectname) {
        this.subjectname = subjectname;
    }

    public String getAtt() {
        return att;
    }

    public void setAtt(String att) {
        this.att = att;
    }
}
