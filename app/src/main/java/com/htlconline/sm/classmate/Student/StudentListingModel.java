package com.htlconline.sm.classmate.Student;

/**
 * Created by Shikhar Garg on 27-12-2016.
 */
public class StudentListingModel {
        private int count;
        private int current_page;
        private int total_pages;
        private Results[] results;
        private String next;
        private int per_page_record;
        private String previous;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(int current_page) {
            this.current_page = current_page;
        }

        public Results[] getResults() {
            return results;
        }

        public void setResults(Results[] results) {
            this.results = results;
        }

        public int getTotal_pages() {
            return total_pages;
        }

        public void setTotal_pages(int total_pages) {
            this.total_pages = total_pages;
        }

        public String getNext() {
            return next;
        }

        public void setNext(String next) {
            this.next = next;
        }

        public int getPer_page_record() {
            return per_page_record;
        }

        public void setPer_page_record(int per_page_record) {
            this.per_page_record = per_page_record;
        }

        public String getPrevious() {
            return previous;
        }

        public void setPrevious(String previous) {
            this.previous = previous;
        }
    public static class Results{
        private String id;
        private String name;
        private String joining_date;
        private String [] get_enrolled_programs;
        private Attendance get_attendance_percentage;
        private String get_performance_percentage;
        private String get_absolute_url;
        private String class_level;
        private String centre;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getJoining_date() {
            return joining_date;
        }

        public void setJoining_date(String joining_date) {
            this.joining_date = joining_date;
        }

        public String[] getGet_enrolled_programs() {
            return get_enrolled_programs;
        }

        public void setGet_enrolled_programs(String[] get_enrolled_programs) {
            this.get_enrolled_programs = get_enrolled_programs;
        }

        public Attendance getGet_attendance_percentage() {
            return get_attendance_percentage;
        }

        public void setGet_attendance_percentage(Attendance get_attendance_percentage) {
            this.get_attendance_percentage = get_attendance_percentage;
        }

        public String getGet_performance_percentage() {
            return get_performance_percentage;
        }

        public void setGet_performance_percentage(String get_performance_percentage) {
            this.get_performance_percentage = get_performance_percentage;
        }

        public String getGet_absolute_url() {
            return get_absolute_url;
        }

        public void setGet_absolute_url(String get_absolute_url) {
            this.get_absolute_url = get_absolute_url;
        }

        public String getClass_level() {
            return class_level;
        }

        public void setClass_level(String class_level) {
            this.class_level = class_level;
        }

        public String getCentre() {
            return centre;
        }

        public void setCentre(String centre) {
            this.centre = centre;
        }
    }
    public static class Attendance{
        private int absent;
        private int late_present;
        private int present;

        public int getAbsent() {
            return absent;
        }

        public void setAbsent(int absent) {
            this.absent = absent;
        }

        public int getLate_present() {
            return late_present;
        }

        public void setLate_present(int late_present) {
            this.late_present = late_present;
        }

        public int getPresent() {
            return present;
        }

        public void setPresent(int present) {
            this.present = present;
        }
    }
}
