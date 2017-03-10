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
public class AttendanceRecyclerViewAdapter extends RecyclerView.Adapter<AttendanceRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<AttendancePojo.Result> attendanceArrayList;
    //private ArrayList<MembersPojo> frontlineUsersList;
    private Context context;
    private OnRecyclerItemClick onRecyclerItemClick;

    public AttendanceRecyclerViewAdapter(Context context, OnRecyclerItemClick onRecyclerItemClick, ArrayList<AttendancePojo.Result> attendanceArrayList)
    {
        this.context = context;
        this.attendanceArrayList = attendanceArrayList;
        this.onRecyclerItemClick = onRecyclerItemClick;
//        this.frontlineUsersList = frontlineUsersList;

//        imageLoader = ImageLoader.getInstance();
//        options = new DisplayImageOptions.Builder()
//                .showImageOnLoading(R.drawable.dp)
//                .showImageForEmptyUri(R.drawable.dp)//empty
//                .showImageOnFail(R.drawable.dp)//error
//                .cacheInMemory(true)
//                .cacheOnDisk(true)
//                .considerExifParams(true)
//                .build();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.attendance_group_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if(position > 0){
            if(attendanceArrayList.get(position).getFormattedDateWithDay().equalsIgnoreCase(attendanceArrayList.get(position-1).getFormattedDateWithDay())){
                holder.headerLayout.setVisibility(View.GONE);
                holder.dateHeader.setVisibility(View.GONE);
                //holder.dateHeader.setText(attendanceArrayList.get(position).getFormattedDateWithDay());
            }else {
                holder.headerLayout.setVisibility(View.VISIBLE);
                holder.dateHeader.setVisibility(View.VISIBLE);
                holder.dateHeader.setText(attendanceArrayList.get(position).getFormattedDateWithDay());
            }
        }else {
            holder.headerLayout.setVisibility(View.VISIBLE);
            holder.dateHeader.setVisibility(View.VISIBLE);
            holder.dateHeader.setText(attendanceArrayList.get(position).getFormattedDateWithDay());
        }

        if(!Config.getAttendanceColor(attendanceArrayList.get(position).getAttendanceType()).equalsIgnoreCase(""))
        holder.saparetor.setBackgroundColor(Color.parseColor(Config.getAttendanceColor(attendanceArrayList.get(position).getAttendanceType())));

        holder.dateHeader.setText(attendanceArrayList.get(position).getFormattedDateWithDay());
        holder.classTime.setText(attendanceArrayList.get(position).getFormattedTime());
        holder.classEndTime.setText(attendanceArrayList.get(position).getFormattedEndTime());
        holder.className.setText(attendanceArrayList.get(position).getSubject());
        holder.classType.setText(attendanceArrayList.get(position).getClassType());
    }

    @Override
    public int getItemCount() {
        return attendanceArrayList.size();
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
