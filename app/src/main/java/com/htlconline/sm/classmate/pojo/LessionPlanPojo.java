package com.htlconline.sm.classmate.pojo;

import java.util.ArrayList;

/**
 * Created by M82061 on 2/21/2017.
 */

public class LessionPlanPojo {

    private ArrayList<Result> results;

    public ArrayList<Result> getResults() {
        return results;
    }

    public void setResults(ArrayList<Result> results) {
        this.results = results;
    }

    public class Lecture {

        private String lecture_name;
        private Boolean is_completed;
        private String completion_date;
        private int lecture_no;

        public String getLectureName() {
            return lecture_name;
        }

        public void setLectureName(String lectureName) {
            this.lecture_name = lectureName;
        }

        public Boolean getIsCompleted() {
            return is_completed;
        }

        public void setIsCompleted(Boolean isCompleted) {
            this.is_completed = isCompleted;
        }

        public String getCompletionDate() {
            return completion_date;
        }

        public void setCompletionDate(String completionDate) {
            this.completion_date = completionDate;
        }

        public int getLectureNo() {
            return lecture_no;
        }

        public void setLectureNo(int lectureNo) {
            this.lecture_no = lectureNo;
        }

    }

    public class Result {

        private ArrayList<Lecture> lectures;
        private String chapter_name;
        private int chapter_no;

        public ArrayList<Lecture> getLectures() {
            return lectures;
        }

        public void setLectures(ArrayList<Lecture> lectures) {
            this.lectures = lectures;
        }

        public String getChapterName() {
            return chapter_name;
        }

        public void setChapterName(String chapterName) {
            this.chapter_name = chapterName;
        }

        public int getChapterNo() {
            return chapter_no;
        }

        public void setChapterNo(int chapterNo) {
            this.chapter_no = chapterNo;
        }

    }
}
