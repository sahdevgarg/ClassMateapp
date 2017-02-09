package com.htlconline.sm.classmate.Schedule.MonthData;
import java.util.Date;
import java.util.List;

/**
 * Created by Anurag on 29-12-2016.
 */

public class Events {

        private String event_name;
        private String event_venue;
        private String start_time;
        private String end_time;

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
}


