package com.htlconline.sm.classmate;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.htlconline.sm.classmate.pojo.LessionPlanPojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by M82061 on 2/21/2017.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private final ArrayList<LessionPlanPojo.Result> _lessionPlanArrayList;
    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;

//    public ExpandableListAdapter(Context context, List<String> listDataHeader,
//                                 HashMap<String, List<String>> listChildData) {
//        this._context = context;
//        this._listDataHeader = listDataHeader;
//        this._listDataChild = listChildData;
//    }

    public ExpandableListAdapter(Context context,ArrayList<LessionPlanPojo.Result> lessionPlanArrayList){
        this._context = context;
        this._lessionPlanArrayList = lessionPlanArrayList;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
//        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
//                .get(childPosititon);
        return this._lessionPlanArrayList.get(groupPosition).getLectures().get(childPosititon);
    }



    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        //final String childText = (String) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.batch_lesson_plan_list_row, null);
        }

        TextView lecture = (TextView) convertView.findViewById(R.id.batch_lesson_plan_lecture);
        TextView lecture_name = (TextView) convertView.findViewById(R.id.batch_lesson_plan_lect_name);
        TextView status = (TextView) convertView.findViewById(R.id.detail_lesson_status);
        TextView completedDate = (TextView) convertView.findViewById(R.id.detail_lesson_plan_completed);
        LinearLayout completedDateLayout = (LinearLayout) convertView.findViewById(R.id.completedDateLayout);

        lecture.setText("Lecture "+_lessionPlanArrayList.get(groupPosition).getLectures().get(childPosition).getLectureNo());
        lecture_name.setText(_lessionPlanArrayList.get(groupPosition).getLectures().get(childPosition).getLectureName());

        if(_lessionPlanArrayList.get(groupPosition).getLectures().get(childPosition).getIsCompleted()){
            completedDateLayout.setVisibility(View.VISIBLE);
            status.setText("Completed");
            completedDate.setText(_lessionPlanArrayList.get(groupPosition).getLectures().get(childPosition).getCompletionDate());
        }else {
            completedDateLayout.setVisibility(View.GONE);
            status.setText("Not Completed");
        }

//        TextView txtListChild = (TextView) convertView
//                .findViewById(R.id.lblListItem);
//
//        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
//        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
//                .size();
        return this._lessionPlanArrayList.get(groupPosition).getLectures().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._lessionPlanArrayList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._lessionPlanArrayList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        //String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.batch_lesson_plan_content, null);
        }

        TextView ChapterNo = (TextView) convertView.findViewById(R.id.batch_lesson_plan_chapter);
        TextView ChapterName = (TextView) convertView.findViewById(R.id.batch_lesson_plan_chap_name);
        ImageView arrowImageView = (ImageView) convertView.findViewById(R.id.batch_lesson_plan_toggle);

        ChapterName.setText(_lessionPlanArrayList.get(groupPosition).getChapterName());
        ChapterNo.setText(String.valueOf("Chapter "+_lessionPlanArrayList.get(groupPosition).getChapterNo()));

        if(isExpanded){
            arrowImageView.setImageResource(R.drawable.ic_expand_less_black_24dp);
        }else {
            arrowImageView.setImageResource(R.drawable.ic_expand_more_black_24dp);
        }

//        TextView lblListHeader = (TextView) convertView
//                .findViewById(R.id.lblListHeader);
//        lblListHeader.setTypeface(null, Typeface.BOLD);
//        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}