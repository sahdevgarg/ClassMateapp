//package com.htlconline.sm.classmate.Student;
//
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentPagerAdapter;
//import android.support.v4.app.FragmentStatePagerAdapter;
//import android.util.Log;
//
//import com.htlconline.sm.classmate.Schedule.Fragment.AsynchronousFragment;
//import com.htlconline.sm.classmate.Schedule.Fragment.MainFragment;
//import com.htlconline.sm.classmate.interfaces.FragmentChangeListener;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by Shikhar Garg on 29-12-2016.
// */
//public class StudentViewPagerAdapter extends FragmentPagerAdapter {
//    private final List<Fragment> mFragmentList=new ArrayList<>();
//    private final List<String> mFragmentTitleList=new ArrayList<>();
//
//    //now..
//    private Fragment current=null;
//    private FragmentManager fm;
//
//    public class FirstPageListener implements
//            FragmentChangeListener {
//        public void OnSwitchToNextFragment(FragmentManager fm) {
//            //Log.d("test","reached here");
//            fm.beginTransaction().remove(current)
//                    .commitAllowingStateLoss();
//            if (current instanceof AsynchronousFragment){
////                fm.beginTransaction().replace(current.getId(),new MainFragment(listener)).commit();
//                Log.d("test","instance of asynchronous");
//                current = new MainFragment(listener);
//            }else{ // Instance of NextFragment
//  //              fm.beginTransaction().replace(current.getId(),new AsynchronousFragment(listener)).commit();
//                Log.d("test","instance of some another");
//                current = new AsynchronousFragment(listener);
//            }
//            notifyDataSetChanged();
//        }
//
//        @Override
//        public void OnSwitchToNextFragment(int id) {
//           fm.beginTransaction().remove(current)
//                    .commitAllowingStateLoss();
//            current = new AsynchronousFragment(id);
//            notifyDataSetChanged();
//        }
//    }
//
//    FirstPageListener listener=new FirstPageListener();
//
//    public StudentViewPagerAdapter(FragmentManager fragmentManager) {
//        super(fragmentManager);
//    }
//
//    public void addFragment(Fragment f,String title){
//        mFragmentList.add(f);
//        mFragmentTitleList.add(title);
//    }
//    @Override
//    public Fragment getItem(int position) {
//        if(position==0){
//            if(current==null){
//                Log.d("test", "current is null");
//                current=new AsynchronousFragment(listener);
//            }
//            return current;
//        }
//        return mFragmentList.get(position);
//    }
//    @Override
//    public CharSequence getPageTitle(int position) {
//        return mFragmentTitleList.get(position);
//    }
//    @Override
//    public int getCount() {
//        return mFragmentList.size();
//    }
//
//
//    @Override
//    public int getItemPosition(Object object) {
//        if (object instanceof AsynchronousFragment &&
//                current instanceof MainFragment) {
//            return POSITION_NONE;
//        }
//        if (object instanceof MainFragment &&
//                current instanceof AsynchronousFragment) {
//            return POSITION_NONE;
//        }
//        return POSITION_UNCHANGED;
//    }
//}
