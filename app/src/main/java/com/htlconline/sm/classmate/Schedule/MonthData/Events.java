package com.htlconline.sm.classmate.Schedule.MonthData;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Anurag on 29-12-2016.
 */

public class Events {

        private String event_name;
        private String event_venue;
        private String start_time;
        private String end_time;
        private String color;
        private String product;
        private String subject;
        private String instructor;

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getEvent_venue() {
        return event_venue;
    }

    public void setEvent_venue(String event_venue) {
        this.event_venue = event_venue;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
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

    public static String getFormattedTime(String part)
    {
        SimpleDateFormat originalFormat = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
        SimpleDateFormat targetFormat = new SimpleDateFormat("hh:mm a",Locale.ENGLISH);
        Date date = null;
        try {
            date = originalFormat.parse(part);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return targetFormat.format(date);

    }
}


