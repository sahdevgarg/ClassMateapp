package com.htlconline.sm.classmate.Batch.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anurag on 26-01-2017.
 */

public class BatchLessonPlanData {
    private int ChapterNo;
    private String ChapterName;
    private List<Integer> LectureNo;
    private List<String> LectureTopic;

    public int getChapterNo() {
        return ChapterNo;
    }

    public void setChapterNo(int chapterNo) {
        ChapterNo = chapterNo;
    }

    public String getChapterName() {
        return ChapterName;
    }

    public void setChapterName(String chapterName) {
        ChapterName = chapterName;
    }

    public List<Integer> getLectureNo() {
        return LectureNo;
    }

    public void setLectureNo(List<Integer> lectureNo) {
        LectureNo = lectureNo;
    }

    public List<String> getLectureTopic() {
        return LectureTopic;
    }

    public void setLectureTopic(List<String> lectureTopic) {
        LectureTopic = lectureTopic;
    }

    public static List<BatchLessonPlanData> CreateDummyData() {
        List<BatchLessonPlanData> lessonPlanDatas = new ArrayList<>();
        BatchLessonPlanData lessonPlanData = new BatchLessonPlanData();
        lessonPlanData.setChapterName("Light - Reflection and Refraction");
        lessonPlanData.setChapterNo(1);
        List<Integer> temp = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            temp.add(i + 1);
        }
        lessonPlanData.setLectureNo(temp);
        List<String> list = new ArrayList<>();
        list.add("Reflection of Light ,Laws of Reflection . Reflection by Plain Mirrors , Related Terms Spherical mirrors" +
                " - Convex and Concave mirrors Understanding terms related with spherical " +
                "mirrors Home Assignment for the day");
        list.add("Focus and Focal Length of Spherical mirrors - ( 1 ) Convex mirror ( 2 ) Concave mirror Rules for " +
                "obtaining Images by Spherical Mirrors Image formation by Concave Mirrors " +
                "( Make students draw ray Diagrams in the Class-Important cases ) Home Assignment for the day");
        list.add("Focus and Focal Length of Spherical mirrors - ( 1 ) Convex mirror ( 2 ) Concave mirror Rules for " +
                "obtaining Images by Spherical Mirrors Image formation by Concave Mirrors " +
                "( Make students draw ray Diagrams in the Class-Important cases ) Home Assignment for the day");
        list.add("Focus and Focal Length of Spherical mirrors - ( 1 ) Convex mirror ( 2 ) Concave mirror Rules for " +
                "obtaining Images by Spherical Mirrors Image formation by Concave Mirrors " +
                "( Make students draw ray Diagrams in the Class-Important cases ) Home Assignment for the day");
        list.add("Focus and Focal Length of Spherical mirrors - ( 1 ) Convex mirror ( 2 ) Concave mirror Rules for " +
                "obtaining Images by Spherical Mirrors Image formation by Concave Mirrors " +
                "( Make students draw ray Diagrams in the Class-Important cases ) Home Assignment for the day");
        list.add("Focus and Focal Length of Spherical mirrors - ( 1 ) Convex mirror ( 2 ) Concave mirror Rules for " +
                "obtaining Images by Spherical Mirrors Image formation by Concave Mirrors " +
                "( Make students draw ray Diagrams in the Class-Important cases ) Home Assignment for the day");
        list.add("Focus and Focal Length of Spherical mirrors - ( 1 ) Convex mirror ( 2 ) Concave mirror Rules for " +
                "obtaining Images by Spherical Mirrors Image formation by Concave Mirrors " +
                "( Make students draw ray Diagrams in the Class-Important cases ) Home Assignment for the day");
        list.add("Focus and Focal Length of Spherical mirrors - ( 1 ) Convex mirror ( 2 ) Concave mirror Rules for " +
                "obtaining Images by Spherical Mirrors Image formation by Concave Mirrors " +
                "( Make students draw ray Diagrams in the Class-Important cases ) Home Assignment for the day");
        lessonPlanData.setLectureTopic(list);
        lessonPlanDatas.add(lessonPlanData);

        lessonPlanData = new BatchLessonPlanData();
        lessonPlanData.setChapterName("Light - Reflection and Refraction");
        lessonPlanData.setChapterNo(1);
        temp = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            temp.add(i + 1);
        }
        lessonPlanData.setLectureNo(temp);
        list = new ArrayList<>();
        list.add("Reflection of Light ,Laws of Reflection . Reflection by Plain Mirrors , Related Terms Spherical mirrors" +
                " - Convex and Concave mirrors Understanding terms related with spherical " +
                "mirrors Home Assignment for the day");
        list.add("Focus and Focal Length of Spherical mirrors - ( 1 ) Convex mirror ( 2 ) Concave mirror Rules for " +
                "obtaining Images by Spherical Mirrors Image formation by Concave Mirrors " +
                "( Make students draw ray Diagrams in the Class-Important cases ) Home Assignment for the day");
        list.add("Focus and Focal Length of Spherical mirrors - ( 1 ) Convex mirror ( 2 ) Concave mirror Rules for " +
                "obtaining Images by Spherical Mirrors Image formation by Concave Mirrors " +
                "( Make students draw ray Diagrams in the Class-Important cases ) Home Assignment for the day");
        list.add("Focus and Focal Length of Spherical mirrors - ( 1 ) Convex mirror ( 2 ) Concave mirror Rules for " +
                "obtaining Images by Spherical Mirrors Image formation by Concave Mirrors " +
                "( Make students draw ray Diagrams in the Class-Important cases ) Home Assignment for the day");
        list.add("Focus and Focal Length of Spherical mirrors - ( 1 ) Convex mirror ( 2 ) Concave mirror Rules for " +
                "obtaining Images by Spherical Mirrors Image formation by Concave Mirrors " +
                "( Make students draw ray Diagrams in the Class-Important cases ) Home Assignment for the day");
        list.add("Focus and Focal Length of Spherical mirrors - ( 1 ) Convex mirror ( 2 ) Concave mirror Rules for " +
                "obtaining Images by Spherical Mirrors Image formation by Concave Mirrors " +
                "( Make students draw ray Diagrams in the Class-Important cases ) Home Assignment for the day");
        list.add("Focus and Focal Length of Spherical mirrors - ( 1 ) Convex mirror ( 2 ) Concave mirror Rules for " +
                "obtaining Images by Spherical Mirrors Image formation by Concave Mirrors " +
                "( Make students draw ray Diagrams in the Class-Important cases ) Home Assignment for the day");
        list.add("Focus and Focal Length of Spherical mirrors - ( 1 ) Convex mirror ( 2 ) Concave mirror Rules for " +
                "obtaining Images by Spherical Mirrors Image formation by Concave Mirrors " +
                "( Make students draw ray Diagrams in the Class-Important cases ) Home Assignment for the day");
        lessonPlanData.setLectureTopic(list);


        lessonPlanDatas.add(lessonPlanData);

        return lessonPlanDatas;
    }
}
