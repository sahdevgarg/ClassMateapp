package com.htlconline.sm.classmate.Schedule.widget;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Anurag on 27-12-2016.
 */

public class CustomInfo {
    private String date;
    private String event;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String findDay()
    {
        String date = getDate();
        SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date date1= new Date();
        try {
            date1 = format1.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
       ;
        return ( new SimpleDateFormat("EE",Locale.ENGLISH).format(date1));
    }
    public String findNumericDay()
    {
        String date = getDate();
        String parts[] = date.split("-");
        return parts[0];
    }
}
