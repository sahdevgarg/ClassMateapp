package com.htlconline.sm.classmate.Schedule.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.htlconline.sm.classmate.R;
import com.htlconline.sm.classmate.Schedule.MonthData.Events;

import java.lang.reflect.Field;



public class EventDetialFragment extends Fragment {

    private TextView eventDate;
    private TextView eventName;
    private TextView eventStart;
    private TextView eventEnd;
    private TextView eventVenue;
    private Events events;
    private  String date;
    private ImageButton closeButton;


    public EventDetialFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_detial, container, false);

        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        eventDate= (TextView) view.findViewById(R.id.detail_event_date);
        eventVenue= (TextView) view.findViewById(R.id.detail_event_venue);
        eventName= (TextView) view.findViewById(R.id.detail_event_name);
        eventStart= (TextView) view.findViewById(R.id.detail_event_start);
        eventEnd= (TextView) view.findViewById(R.id.detail_event_end);
        closeButton = (ImageButton) view.findViewById(R.id.close_button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        eventName.setText(events.getEvent_name());
        eventStart.setText(events.getStart_time());
        eventEnd.setText(events.getEnd_time());
        eventVenue.setText(events.getEvent_venue());
        eventDate.setText(date);
    }

    public  void setUp(Events event, String d) {
        events =event;
        date = d;
    }

    @Override
    public void onDetach() {
        super.onDetach();


    }
}
