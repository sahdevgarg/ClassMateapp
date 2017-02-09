package com.htlconline.sm.classmate.Student;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.htlconline.sm.classmate.R;

import org.w3c.dom.Text;

/**
 * Created by Shikhar Garg on 28-12-2016.
 */
public class StudentListingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ImageView profilepicture;
    public TextView username,userid,usercenter,user_att_present;
    public Context context;
    public FrameLayout frameLayout;
    public StudentListingViewHolder(View itemView,Context context) {
        super(itemView);
        this.context=context;
        username=(TextView)itemView.findViewById(R.id.student_listing_name);
        userid=(TextView)itemView.findViewById(R.id.student_listing_id);
        usercenter=(TextView)itemView.findViewById(R.id.student_listing_center);
        user_att_present=(TextView)itemView.findViewById(R.id.student_listing_att_present);
        frameLayout = (FrameLayout) itemView.findViewById(R.id.flay);
        //itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.i("onclick ","yes");
        //context.startActivity(new Intent(context,StudentDetailActivity.class));
    }
}
