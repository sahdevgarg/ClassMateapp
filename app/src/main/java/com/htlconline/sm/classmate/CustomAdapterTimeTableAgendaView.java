package com.htlconline.sm.classmate;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.htlconline.sm.classmate.Schedule.Fragment.MainFragment;
import com.htlconline.sm.classmate.Schedule.MonthData.Events;
import com.htlconline.sm.classmate.Schedule.widget.CustomAdapter;
import com.htlconline.sm.classmate.Schedule.widget.CustomInfo;
import com.htlconline.sm.classmate.Schedule.widget.CustomListAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by M82061 on 2/23/2017.
 */

public class CustomAdapterTimeTableAgendaView extends RecyclerView.Adapter<CustomAdapterTimeTableAgendaView.CustomViewHolder> {


    private static final String Color_Type_1 = "#039be5";
    private static final String Color_Type_2 = "#DD3333";
    private static final String Color_Type_3 = "#11e578";
    private static final String Color_Type_4 = "#FFA500";
    private static final String Color_Type_5 = "#ffccbd";


    private static final String CLASS_TYPE_1 = "Regular Class";
    private static final String CLASS_TYPE_2 = "Assessment Class";
    private static final String CLASS_TYPE_3 = "Extra Class";
    private static final String CLASS_TYPE_4 = "Doubt Class";
    private static final String CLASS_TYPE_5 = "Holiday";


    private final LayoutInflater inflator;
    private View view;
    private List<Events> events;
    private List<CustomInfo> monthCalendar;
    private Map<String, List<Events>> setUpList = new HashMap<>();
    private ArrayAdapter<String> listadapter;
    private Context context;
    private static Events event;
    private OnClickListItem clickListener;

    public CustomAdapterTimeTableAgendaView(Context context, List<CustomInfo> monthCalendar, Map<String, List<Events>> setUpList) {

        inflator = LayoutInflater.from(context);
        this.setUpList = setUpList;
        this.monthCalendar = monthCalendar;
        this.context = context;



    }

    @Override
    public CustomAdapterTimeTableAgendaView.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = inflator.inflate(R.layout.custom_agenda_list, parent, false);
        CustomAdapterTimeTableAgendaView.CustomViewHolder viewHolder = new CustomAdapterTimeTableAgendaView.CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomAdapterTimeTableAgendaView.CustomViewHolder holder, final int position) {

        CustomInfo customInfo = monthCalendar.get(position);
        String date = customInfo.getDate();
        holder.date.setText(customInfo.findNumericDay());
        holder.day.setText(customInfo.findDay());
        if (setUpList.containsKey(date)) {
            events = setUpList.get(date);
            event = null;
            String event_list[] = new String[events.size()];
            String event_color[] = new String[events.size()];
            for (int i = 0; i < events.size(); i++) {
                event = events.get(i);
                event_color[i] = getColor(event.getEvent_name());
                event.setColor(event_color[i]);

                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(event.getEvent_name());
                stringBuilder.append("\n");
                stringBuilder.append(event.getEvent_venue());
                stringBuilder.append("\n");

                event_list[i] = stringBuilder.toString();
            }

            ArrayList<String> eventList = new ArrayList<String>();
            eventList.addAll(Arrays.asList(event_list));
            listadapter = new CustomListAdapter(context, R.layout.list_item_agenda, eventList , event_color);
            holder.listView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, events.size() * 190));
            holder.listView.setDividerHeight(10);
            holder.listView.setAdapter(listadapter);

        } else

        {


            String event_list[] = {"NO EVENT"};
            String event_color[] = {""};
            ArrayList<String> eventList = new ArrayList<String>();
            eventList.addAll(Arrays.asList(event_list));
            listadapter = new CustomListAdapter(context, R.layout.list_item_agenda, eventList, event_color);
            holder.listView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 120));
            holder.listView.setDivider(null);
            holder.listView.setAdapter(listadapter);


        }


    }

    private String getColor(String part) {
        if (part.contains(CLASS_TYPE_1))
            return Color_Type_1;
        else if (part.contains(CLASS_TYPE_2))
            return Color_Type_2;
        else if (part.contains(CLASS_TYPE_3))
            return Color_Type_3;
        else if (part.contains(CLASS_TYPE_4))
            return Color_Type_4;
        else if (part.contains(CLASS_TYPE_5))
            return Color_Type_5;

        return null;
    }


    @Override
    public int getItemCount() {
        return this.monthCalendar.size();
    }

    public void setClickListener(TimeTableAgendaViewFragment mainFragment) {
        this.clickListener = mainFragment;
    }

    public void update(Map<String, List<Events>> setUpList) {

        this.setUpList = setUpList;
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {

        private TextView date;
        private ListView listView;
        private TextView day;

        public CustomViewHolder(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.custom_date);
            day = (TextView) itemView.findViewById(R.id.custom_day);
            listView = (ListView) itemView.findViewById(R.id.custom_event_list);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                    CustomInfo customInfo = monthCalendar.get(getAdapterPosition());
                    String date = customInfo.getDate();
                    events = setUpList.get(date);
                    if(events !=null)
                    {
                        event = events.get(pos);
                        clickListener.onClick(view, event, date);
                    }

                }
            });
        }


    }


    public interface OnClickListItem {
        void onClick(View view, Events events, String date);
    }


}

