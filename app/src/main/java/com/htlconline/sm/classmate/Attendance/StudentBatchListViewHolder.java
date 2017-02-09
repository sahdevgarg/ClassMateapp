package com.htlconline.sm.classmate.Attendance;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.htlconline.sm.classmate.R;
import com.htlconline.sm.classmate.Student.StudentDetailActivity;

/**
 * Created by Shikhar Garg on 02-02-2017.
 */
public class StudentBatchListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public RelativeLayout rl;
    public TextView batchid,att;
    public PieChart chart;
    public Context context;
    public StudentBatchListViewHolder(View itemView, Context context) {
        super(itemView);

        batchid = (TextView) itemView.findViewById(R.id.student_batch_list_batchid);
        att = (TextView) itemView.findViewById(R.id.student_batch_list_att);
        rl = (RelativeLayout) itemView.findViewById(R.id.rlay);
//        chart = (PieChart) itemView.findViewById(R.id.student_batch_list_piechart_att);
//        rl = (RelativeLayout) itemView.findViewById(R.id.rlay);

        this.context=context;

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.i("sbl ", "yes");
        context.startActivity(new Intent(context,StudentDetailActivity.class));
    }
}
