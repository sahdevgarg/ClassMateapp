package com.htlconline.sm.classmate.Batch.Adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.htlconline.sm.classmate.R;

import java.util.Arrays;
import java.util.List;



/**
 * Created by Anurag on 30-01-2017.
 */

public class BatchMarksAdapter extends RecyclerView.Adapter<BatchMarksAdapter.MarksViewHolder> {

    private LayoutInflater inflater;
    private View view;
    List<Boolean> data;


    public BatchMarksAdapter(FragmentActivity activity) {
        this.inflater = LayoutInflater.from(activity);
        this.data = Arrays.asList(true, false);
    }

    @Override
    public MarksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = inflater.inflate(R.layout.batch_marks_content, parent, false);
        return new MarksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MarksViewHolder holder, int position) {

    }



    @Override
    public int getItemCount() {
        return 10;
    }

    class MarksViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView studentName;
        private TextView studentId;
        private TextView status;

        MarksViewHolder(View itemView) {

            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.batch_marks_card);
            studentName = (TextView) itemView.findViewById(R.id.batch_attendance_name);
            studentId = (TextView) itemView.findViewById(R.id.batch_attendance_id);
            status = (TextView) itemView.findViewById(R.id.status);
        }
    }


}
