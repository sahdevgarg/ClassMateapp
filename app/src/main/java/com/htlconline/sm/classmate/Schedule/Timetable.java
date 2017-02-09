package com.htlconline.sm.classmate.Schedule;

import android.graphics.Color;
import android.util.Log;

import com.alamkanak.weekview.WeekViewEvent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Anurag on 29-12-2016.
 */

public class Timetable {

    public List<Results> results;

    public List<Results> getResults() {
        return results;
    }

    public void setResults(List<Results> results) {
        this.results = results;
    }

    public  class Results{

        private String title;
        private String start;
        private String end;
        private String subject;
        private String instructor;
        private Boolean is_holiday;
        private String centre;
        private String class_type;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getInstructor() {
            return instructor;
        }

        public void setInstructor(String instructor) {
            this.instructor = instructor;
        }

        public Boolean getIs_holiday() {
            return is_holiday;
        }

        public void setIs_holiday(Boolean is_holiday) {
            this.is_holiday = is_holiday;
        }

        public String getCentre() {
            return centre;
        }

        public void setCentre(String centre) {
            this.centre = centre;
        }

        public String getClass_type() {
            return class_type;
        }

        public void setClass_type(String class_type) {
            this.class_type = class_type;
        }





        public WeekViewEvent toWeekViewEvent(){

            // Parse time.
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss",Locale.ENGLISH);
            Date start = new Date();
            Date end = new Date();
            try {
                start = sdf.parse(getStartTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                end = sdf.parse(getEndTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // Initialize start and end time.
            Calendar now = Calendar.getInstance();
            Calendar startTime = (Calendar) now.clone();
            startTime.setTimeInMillis(start.getTime());
            startTime.set(Calendar.YEAR, getYear());
            startTime.set(Calendar.MONTH,getMonth()-1);
            startTime.set(Calendar.DAY_OF_MONTH, getDayOfMonth());
            Calendar endTime = (Calendar) startTime.clone();
            endTime.setTimeInMillis(end.getTime());
            endTime.set(Calendar.YEAR, getYear());
            endTime.set(Calendar.MONTH, getMonth()-1);
            endTime.set(Calendar.DAY_OF_MONTH, getDayOfMonth());

            // Create an week view event.
            SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            String formatted = format1.format(startTime.getTime());
//            Log.d("Test start", formatted);

            formatted = format1.format(endTime.getTime());
//            Log.d("Test end", formatted);

            WeekViewEvent weekViewEvent = new WeekViewEvent();
            weekViewEvent.setName(getClass_type());
            weekViewEvent.setStartTime(startTime);
            weekViewEvent.setEndTime(endTime);
            weekViewEvent.setColor(Color.parseColor("#F57F68"));

            return weekViewEvent;
        }

        public String getStartTime() {
            String time= getStart();
            String parts[] = time.split("T");

            return parts[1];
        }

        public String getDate()
        {
            String time= getStart();
            String parts[] = time.split("T");

            return parts[0];
        }

        public String getEndTime() {
            String time = getEnd();
            String parts[]= time.split("T");
            return parts[1];
        }

        public int getDayOfMonth() {
            String time = getEnd();
            String parts[]= time.split("T");
            String req = parts[0];
            String dis[] = req.split("-");
           // Log.d("Test", ""+Integer.valueOf(dis[2]));
            return Integer.valueOf(dis[2]);
        }
        public int getYear()
        {
            String time = getEnd();
            String parts[]= time.split("T");
            String req = parts[0];
            String dis[] = req.split("-");
           // Log.d("Test", ""+Integer.valueOf(dis[2]));
            return Integer.valueOf(dis[0]);
        }
        public int getMonth()
        {
            String time = getEnd();
            String parts[]= time.split("T");
            String req = parts[0];
            String dis[] = req.split("-");
           // Log.d("Test", ""+Integer.valueOf(dis[2]));
            return Integer.valueOf(dis[1]);
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
    }



}
