package com.htlconline.sm.classmate.Student;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Shikhar Garg on 05-01-2017.
 */
public class StudentViewPager extends ViewPager {

    private boolean swipeable=false;

    public StudentViewPager(Context context) {
        super(context);
    }

    public StudentViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setSwipeable(boolean swipeable){
        this.swipeable=swipeable;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return (this.swipeable)?super.onInterceptTouchEvent(ev):false;

    }
}
