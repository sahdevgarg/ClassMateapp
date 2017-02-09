package com.htlconline.sm.classmate.Schedule.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.htlconline.sm.classmate.R;
//import com.htlconline.sm.classmate.Schedule.Fragment.MainFragment;
import com.htlconline.sm.classmate.Schedule.Fragment.MainFragment;
import com.htlconline.sm.classmate.Schedule.MonthData.Events;

import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Anurag on 27-12-2016.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder>{


    private  final  LayoutInflater inflator;
    private View view;
    private List<Events> events;
    private List<CustomInfo> monthCalendar;
    private Map<String,List<Events>> setUpList = new HashMap<>();
    private ArrayAdapter<String> listadapter;
    private Context context;
    private static Events event;
    private static int max;
    private OnClickListItem clickListener;
    public CustomAdapter(Context context, List<CustomInfo> monthCalendar, Map<String, List<Events>> setUpList)
    {

        inflator=LayoutInflater.from(context);
        this.setUpList = setUpList;
        this.monthCalendar = monthCalendar;
        this.context=context;

        this.max = monthCalendar.size()-4;


    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view=inflator.inflate(R.layout.custom_agenda_list,parent,false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, final int position) {

        CustomInfo customInfo = monthCalendar.get(position);
        String date = customInfo.getDate();
        if(position < max)
        {
            holder.date.setText(customInfo.findNumericDay());
            holder.day.setText(customInfo.findDay());

        }
        else
        {
            holder.date.setText("");
            holder.day.setText("");
        }

        if(setUpList.containsKey(date))
        {
            events = setUpList.get(date);
            event = null;
            String event_list[] = new String[events.size()];
            for (int i=0;i<events.size();i++)
            {
                event = events.get(i);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(event.getEvent_name());
                stringBuilder.append("\n");
                stringBuilder.append(event.getEvent_venue());
                stringBuilder.append("\n");
                event_list[i] = stringBuilder.toString();
            }

            ArrayList<String> eventList = new ArrayList<String>();
            eventList.addAll(Arrays.asList(event_list));
            listadapter= new CustomListAdapter(context,R.layout.list_item_agenda,eventList);
            holder.listView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,events.size()*190));
            holder.listView.setDividerHeight(10);
            holder.listView.setAdapter(listadapter);

        }else

        {

               if(position+1 > max)
               {
                   String event_list[] = {""};
                   ArrayList<String> eventList = new ArrayList<String>();
                   eventList.addAll(Arrays.asList(event_list));
                   listadapter = new ArrayAdapter<String>(context,R.layout.list_item_agenda,eventList);
                   holder.listView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,120));
                   holder.listView.setDivider(null);
                   holder.listView.setAdapter(listadapter);
               }

              else
               {
                   String event_list[] = {"NO EVENT"};
                   ArrayList<String> eventList = new ArrayList<String>();
                   eventList.addAll(Arrays.asList(event_list));
                   listadapter = new ArrayAdapter<String>(context,R.layout.list_item_agenda,eventList);
                   holder.listView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,120));
                   holder.listView.setDivider(null);
                   holder.listView.setAdapter(listadapter);
               }


        }



    }


    @Override
    public int getItemCount() {
        return this.monthCalendar.size();
    }

   public void setClickListener(MainFragment mainFragment) {
        this.clickListener=mainFragment;
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder{

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
                    events= setUpList.get(date);
                    event = events.get(pos);
                    clickListener.onClick(view,event , date);
                }
            });
        }




    }


    public interface OnClickListItem
    {
        void onClick(View view,Events events, String date);
    }


}
