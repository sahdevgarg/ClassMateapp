package com.htlconline.sm.classmate;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.htlconline.sm.classmate.pojo.AttendancePojo;

import java.util.ArrayList;

/**
 * Created by M82061 on 10/19/2016.
 */
public class AnnouncementRecyclerViewAdapter extends RecyclerView.Adapter<AnnouncementRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private OnRecyclerItemClick onRecyclerItemClick;

    public AnnouncementRecyclerViewAdapter(Context context, OnRecyclerItemClick onRecyclerItemClick)
    {
        this.context = context;
        this.onRecyclerItemClick = onRecyclerItemClick;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.announcement_list_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView dateHeader;
        private final TextView classTime;
        private final TextView className;
        private final TextView classType;
        private final RelativeLayout headerLayout;
        private final View saparetor;
        private final TextView classEndTime;

        public ViewHolder(View itemView) {
            super(itemView);

            saparetor = (View)itemView.findViewById(R.id.saprator);
            dateHeader = (TextView)itemView.findViewById(R.id.dateHeader);
            headerLayout = (RelativeLayout)itemView.findViewById(R.id.headerLayout);
            classTime = (TextView)itemView.findViewById(R.id.classTime);
            classEndTime = (TextView)itemView.findViewById(R.id.classEndTime);
            className = (TextView)itemView.findViewById(R.id.className);
            classType = (TextView)itemView.findViewById(R.id.classType);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    onRecyclerItemClick.onItemClick(getAdapterPosition());
//                }
//            });

        }
    }
}
