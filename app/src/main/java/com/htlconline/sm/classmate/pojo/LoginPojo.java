package com.htlconline.sm.classmate.pojo;

import java.util.ArrayList;

/**
 * Created by M82061 on 2/15/2017.
 */

public class LoginPojo {

    private String success;
    private String error;
    private String userId;
    private String name;
    private String student_id;
    private String user_name;
    private ArrayList<Integer> role;
    private ArrayList<Student> students;


    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Integer> getRole() {
        return role;
    }

    public void setRole(ArrayList<Integer> role) {
        this.role = role;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }


    public class Student {

        private String student_name;
        private String student_id;

        public String getStudentName() {
            return student_name;
        }

        public void setStudentName(String studentName) {
            this.student_name = studentName;
        }

        public String getStudentId() {
            return student_id;
        }

        public void setStudentId(String studentId) {
            this.student_id = studentId;
        }

    }
}
