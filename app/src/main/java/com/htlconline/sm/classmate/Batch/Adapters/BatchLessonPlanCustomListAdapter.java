package com.htlconline.sm.classmate.Batch.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.htlconline.sm.classmate.Batch.BatchFragments.BatchLessonPlanFragment;
import com.htlconline.sm.classmate.R;

import java.util.List;

/**
 * Created by Anurag on 27-01-2017.
 */

public class BatchLessonPlanCustomListAdapter extends BaseAdapter {
    private List<Integer> lecture;
    private List<String> lecture_name;
    private LayoutInflater mInflater;
    private onClickList listener;

    BatchLessonPlanCustomListAdapter(Context context, List<Integer> lecture, List<String> lecture_name, BatchLessonPlanFragment batchLessonPlanFragment) {

        this.lecture = lecture;
        this.lecture_name = lecture_name;
        mInflater = LayoutInflater.from(context);
        this.listener = batchLessonPlanFragment;

    }

    public int getCount() {
        return lecture.size();
    }

    public Object getItem(int position) {
        return lecture.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.batch_lesson_plan_list_row, null);
            holder = new ViewHolder();
            holder.lecture = (TextView) convertView.findViewById(R.id.batch_lesson_plan_lecture);
            holder.lecture_name = (TextView) convertView.findViewById(R.id.batch_lesson_plan_lect_name);
           // holder.details = (TextView) convertView.findViewById(R.id.batch_lesson_plan_details);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.lecture.setText(String.valueOf(this.lecture.get(position)));
        holder.lecture_name.setText(this.lecture_name.get(position));



        return convertView;


    }


    private static class ViewHolder {
        TextView lecture;
        TextView lecture_name;
        TextView details;
    }

    public interface onClickList {
        void onClick(View view, String s);
        // display pop up screen
    }

}


