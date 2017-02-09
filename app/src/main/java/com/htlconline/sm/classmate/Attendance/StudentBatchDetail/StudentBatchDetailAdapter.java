package com.htlconline.sm.classmate.Attendance.StudentBatchDetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.htlconline.sm.classmate.R;

import java.util.List;

/**
 * Created by Shikhar Garg on 02-02-2017.
 */
public class StudentBatchDetailAdapter extends RecyclerView.Adapter<StudentBatchDetailViewHolder> {
    List<StudentBatchDetailModel.Results> slist;
    Context context;

    public StudentBatchDetailAdapter(List<StudentBatchDetailModel.Results> slist, Context context) {
        this.slist = slist;
        this.context = context;
    }

    @Override
    public StudentBatchDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.student_batch_detail_item,null);
        StudentBatchDetailViewHolder slvh=new StudentBatchDetailViewHolder(v,context);
        return slvh;
    }

    @Override
    public void onBindViewHolder(StudentBatchDetailViewHolder holder, int position) {
        holder.date.setText(slist.get(position).getDate());
        holder.class_type.setText(slist.get(position).getClass_type());
        holder.att_type.setText(slist.get(position).getAtt_type());
    }
    @Override
    public int getItemCount() {
        return slist.size();
    }
}
