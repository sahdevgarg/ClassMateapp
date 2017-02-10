package com.htlconline.sm.classmate.Schedule;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.htlconline.sm.classmate.Schedule.Fragment.AsynchronousFragment;
import com.htlconline.sm.classmate.Schedule.Fragment.MainFragment;
import com.htlconline.sm.classmate.Student.StudentAttendanceFragment;
import com.htlconline.sm.classmate.Student.StudentReportFragment;
import com.htlconline.sm.classmate.interfaces.FragmentChangeListener;


public class PagerAdapter extends FragmentStatePagerAdapter {

     private Context context;

    public final class FirstPageListener implements
            FragmentChangeListener {
        public void OnSwitchToNextFragment(FragmentManager fragmentManager) {
            fragmentManager.beginTransaction().remove(mFragmentAtPos0)
                    .commitAllowingStateLoss();
            if (mFragmentAtPos0 instanceof AsynchronousFragment){
                mFragmentAtPos0 = new MainFragment(listener);
            }else{ // Instance of NextFragment
                mFragmentAtPos0 = new AsynchronousFragment(listener);
            }
            notifyDataSetChanged();
        }

        @Override
        public void OnSwitchToNextFragment(int id) {
            mFragmentManager.beginTransaction().remove(mFragmentAtPos0)
                    .commitAllowingStateLoss();
                  mFragmentAtPos0 = new AsynchronousFragment(id);
            notifyDataSetChanged();
        }
    }

    private final FragmentManager mFragmentManager;
    public Fragment mFragmentAtPos0;
    FirstPageListener listener = new FirstPageListener();



    public PagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mFragmentManager = fm;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;
        switch (position){
            case 1:
                fragment = new StudentAttendanceFragment();
                break;
            case 2:
                fragment = new StudentReportFragment();
                break;
            case 0:
                if (mFragmentAtPos0 == null)
                {
                    mFragmentAtPos0 = new AsynchronousFragment(listener);
                }
                return mFragmentAtPos0;

        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        super.getPageTitle(position);
        switch (position){
            case 0:
                return "Schedule";
            case 1:
                return "Attendance";
            case 2:
                return "Report";

        }
        return null;
    }

    @Override
    public int getItemPosition(Object object)
    {
        if (object instanceof AsynchronousFragment &&
                mFragmentAtPos0 instanceof MainFragment) {
            return POSITION_NONE;
        }
        if (object instanceof MainFragment &&
                mFragmentAtPos0 instanceof AsynchronousFragment) {
            return POSITION_NONE;
        }
        return POSITION_UNCHANGED;
    }
}
