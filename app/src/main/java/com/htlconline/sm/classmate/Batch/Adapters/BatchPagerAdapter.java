package com.htlconline.sm.classmate.Batch.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.htlconline.sm.classmate.Batch.BatchFragments.BatchAttendenceFragment;
import com.htlconline.sm.classmate.Batch.BatchFragments.BatchLessonPlanFragment;
import com.htlconline.sm.classmate.Batch.BatchFragments.BatchMarksFragment;
import com.htlconline.sm.classmate.Batch.BatchFragments.BatchSummaryFragment;
import com.htlconline.sm.classmate.Student.StudentListingFragment;
import com.htlconline.sm.classmate.interfaces.FragmentChangeListener;
import com.htlconline.sm.classmate.Schedule.Fragment.AsynchronousFragment;
import com.htlconline.sm.classmate.Schedule.Fragment.MainFragment;


public class BatchPagerAdapter extends FragmentStatePagerAdapter {

     private Context context;

    public final class FirstPageListener implements
            FragmentChangeListener {
        public void OnSwitchToNextFragment(FragmentManager fragmentManager) {
            fragmentManager.beginTransaction().remove(mFragmentAtPos3)
                    .commitAllowingStateLoss();
            if (mFragmentAtPos3 instanceof AsynchronousFragment){
                mFragmentAtPos3 = new MainFragment(listener);
            }else{ // Instance of NextFragment
                mFragmentAtPos3 = new AsynchronousFragment(listener);
            }
            notifyDataSetChanged();
        }

        @Override
        public void OnSwitchToNextFragment(int id) {
            mFragmentManager.beginTransaction().remove(mFragmentAtPos3)
                    .commitAllowingStateLoss();
                  mFragmentAtPos3 = new AsynchronousFragment(id);
            notifyDataSetChanged();
        }
    }

    private final FragmentManager mFragmentManager;
    public Fragment mFragmentAtPos3;
    FirstPageListener listener = new FirstPageListener();



    public BatchPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mFragmentManager = fm;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new BatchSummaryFragment();
                break;
            case 1:
                fragment = new StudentListingFragment();
                break;
            case 2:
                fragment = new BatchAttendenceFragment();
                break;
            case 3:
                if (mFragmentAtPos3 == null)
                {
                    mFragmentAtPos3 = new AsynchronousFragment(listener);
                }

                return mFragmentAtPos3;
            case 4:
                fragment = new BatchMarksFragment();
                break;
            case 5:
                fragment = new BatchLessonPlanFragment();
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 6;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        super.getPageTitle(position);
        switch (position){
            case 0:
                return "Summary";
            case 1:
                return "Students";
            case 2:
                return "Attendance";
            case 3:
                return "Schedule";
            case 4:
                return "Marks";
            case 5:
                return "Lesson Plan";
        }
        return null;
    }

    @Override
    public int getItemPosition(Object object)
    {
        if (object instanceof AsynchronousFragment &&
                mFragmentAtPos3 instanceof MainFragment) {
            return POSITION_NONE;
        }
        if (object instanceof MainFragment &&
                mFragmentAtPos3 instanceof AsynchronousFragment) {
            return POSITION_NONE;
        }
        return POSITION_UNCHANGED;
    }


}
