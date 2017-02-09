package com.htlconline.sm.classmate.Attendance;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.PercentFormatter;
import com.htlconline.sm.classmate.Attendance.StudentBatchDetail.StudentBatchDetailActivity;
import com.htlconline.sm.classmate.R;
import com.htlconline.sm.classmate.Student.StudentListingModel;
import com.htlconline.sm.classmate.Student.StudentListingViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shikhar Garg on 02-02-2017.
 */
public class StudentBatchListAdapter extends RecyclerView.Adapter<StudentBatchListViewHolder> {
    List<StudentBatchListModel> slist;
    Context context;

    public StudentBatchListAdapter(List<StudentBatchListModel> slist, Context context) {
        this.slist = slist;
        this.context = context;
    }

    @Override
    public StudentBatchListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.student_batach_list_item,parent,false);
        StudentBatchListViewHolder slvh=new StudentBatchListViewHolder(v,context);
        return slvh;
    }

    @Override
    public void onBindViewHolder(StudentBatchListViewHolder holder, final int position) {
        holder.batchid.setText(slist.get(position).getSubjectname());
        holder.att.setText(slist.get(position).getAtt());
        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudentBatchDetailActivity frag=new StudentBatchDetailActivity();
                Bundle bundle=new Bundle();
                bundle.putString("student_id",slist.get(position).getStudent_id());
                bundle.putString("batch_id",slist.get(position).getBatchid());
                frag.setArguments(bundle);
            }
        });
//        holder.rl.addView(holder.chart);
//        holder.chart.setUsePercentValues(true);
//        holder.chart.setDescription("Attendance");
//        // enable hole and configure
//        holder.chart.setDrawHoleEnabled(true);
//        holder.chart.setHoleColorTransparent(true);
//        holder.chart.setHoleRadius(7);
//        holder.chart.setTransparentCircleRadius(10);
//        // enable rotation of the chart by touch
//        holder.chart.setRotationAngle(0);
//        holder.chart.setRotationEnabled(true);
//        adddata(holder);
    }
//
//    private void adddata(StudentBatchListViewHolder holder) {
//        String [] xvals={"A","P"};
//        ///fill y axis
//        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
//        for (int i = 0; i < ydata.length; i++)
//            yVals1.add(new Entry(ydata[i], i));
//        //fill colors
//        ArrayList<Integer> colors=new ArrayList<Integer>();
//        colors.add(Color.GREEN);
//        colors.add(Color.RED);
//
//        PieDataSet pieDataSet=new PieDataSet(yVals1,"Attendance");
//        pieDataSet.setSliceSpace(3);
//        pieDataSet.setSelectionShift(5);
//        pieDataSet.setColors(colors);
//
//        PieData data = new PieData(xvals,pieDataSet);
//        data.setValueFormatter(new PercentFormatter());
//        data.setValueTextSize(11f);
//        data.setValueTextColor(Color.GRAY);
//
//        holder.chart.setData(data);
//    }

    @Override
    public int getItemCount() {
        return slist.size();
    }
}
