package com.htlconline.sm.classmate.pojo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by M82061 on 2/24/2017.
 */

public class AttendancePojo {

    private int count;
    private int current_page;
    private int total_pages;
    private ArrayList<Result> results;
    private String next;
    private int per_page_record;
    private String previous;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCurrentPage() {
        return current_page;
    }

    public void setCurrentPage(int currentPage) {
        this.current_page = currentPage;
    }

    public int getTotalPages() {
        return total_pages;
    }

    public void setTotalPages(int totalPages) {
        this.total_pages = totalPages;
    }

    public ArrayList<Result> getResults() {
        return results;
    }

    public void setResults(ArrayList<Result> results) {
        this.results = results;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public int getPerPageRecord() {
        return per_page_record;
    }

    public void setPerPageRecord(int perPageRecord) {
        this.per_page_record = perPageRecord;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public class Result {

        private String class_date;
        private String class_type;
        private String subject;
        private String end_time;
        private int status;
        private String attendance_type;

        public String getClassDate() {
            return class_date;
        }

        public void setClassDate(String classDate) {
            this.class_date = classDate;
        }

        public String getClassType() {
            return class_type;
        }

        public void setClassType(String classType) {
            this.class_type = classType;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getAttendanceType() {
            return attendance_type;
        }

        public void setAttendanceType(String attendanceType) {
            this.attendance_type = attendanceType;
        }


        public String getFormattedDate()
        {
            String date = getDate();
            Date date1 = null;
            String formatted = null;
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            try {
                date1 = format1.parse(date);
                SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy",Locale.ENGLISH);
                formatted = format2.format(date1);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return formatted;

        }

        public String getDate()
        {
            String time= getClassDate();
            String parts[] = time.split("T");

            return parts[0];
        }

        public String getFormattedDateWithDay(){
            String date = getDate();
            Date date1 = null;
            String formatted = null;
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            try {
                date1 = format1.parse(date);
                SimpleDateFormat format2 = new SimpleDateFormat("EEEE MMM dd",Locale.ENGLISH);
                formatted = format2.format(date1);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return formatted;
        }

        public String getStartTime() {
            String time= getClassDate();
            String parts[] = time.split("T");

            return (parts[1]);
        }

        public String getEndTime() {
            String time= getEnd_time();
            String parts[] = time.split("T");

            return (parts[1]);
        }


        public String getFormattedTime(){
            String date = getStartTime();
            Date date1 = null;
            String formatted = null;
            SimpleDateFormat format1 = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
            try {
                date1 = format1.parse(date);
                SimpleDateFormat format2 = new SimpleDateFormat("HH:mm",Locale.ENGLISH);
                formatted = format2.format(date1);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return formatted;
        }

        public String getFormattedEndTime(){
            String date = getEndTime();
            Date date1 = null;
            String formatted = null;
            SimpleDateFormat format1 = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
            try {
                date1 = format1.parse(date);
                SimpleDateFormat format2 = new SimpleDateFormat("HH:mm",Locale.ENGLISH);
                formatted = format2.format(date1);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return formatted;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }
    }



    }
