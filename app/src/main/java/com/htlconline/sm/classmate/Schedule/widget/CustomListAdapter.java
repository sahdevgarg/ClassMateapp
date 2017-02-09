package com.htlconline.sm.classmate.Schedule.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.htlconline.sm.classmate.R;
import com.htlconline.sm.classmate.Schedule.MonthData.Events;

import java.util.List;

/**
 * Created by Anurag on 31-12-2016.
 */

public class CustomListAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private int id;
    private List<String> items ;
    private List<Events> events;
    private static final String CLASS_TYPE_1 = "Regular Class";
    private static final String CLASS_TYPE_2 = "Assessment Class";

    public CustomListAdapter(Context context, int textViewResourceId , List<String> list  )
    {
        super(context, textViewResourceId, list);
        mContext = context;
        id = textViewResourceId;
        items = list ;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent)
    {
        View mView = v ;
        if(mView == null){
            LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mView = vi.inflate(id, null);
        }

        TextView text = (TextView) mView.findViewById(R.id.custom_event);

        if(items.get(position) != null )
        {
            text.setTextColor(Color.BLACK);
            text.setText(items.get(position));
            String string = items.get(position);
            String parts[] = string.split("\\n");
            if(parts[0].contains(CLASS_TYPE_1))
            text.setBackgroundColor(Color.GREEN);
            else if(parts[0].contains(CLASS_TYPE_2))
            text.setBackgroundColor(Color.YELLOW);


        }

        return mView;
    }


}
