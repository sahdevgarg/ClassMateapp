package com.htlconline.sm.classmate.Attendance.StudentBatchDetail;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.htlconline.sm.classmate.R;
import com.htlconline.sm.classmate.Student.StudentDetailActivity;

/**
 * Created by Shikhar Garg on 02-02-2017.
 */
public class StudentBatchDetailViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView date,class_type,att_type;
    public Context context;
    public StudentBatchDetailViewHolder(View itemView, Context context) {
        super(itemView);

        date = (TextView) itemView.findViewById(R.id.student_batch_detail_date);
        class_type = (TextView) itemView.findViewById(R.id.student_batch_detail_classtype);
        att_type = (TextView) itemView.findViewById(R.id.student_batch_detail_attType);

        this.context=context;

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.i("sbl ", "yes");
        //context.startActivity(new Intent(context,StudentDetailActivity.class));
    }
}
