package com.pheynix.moli_schedule;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.pheynix.moli_schedule.ScheduleFragment.ScheduleFragment;
import com.pheynix.moli_schedule.SummaryFragment.SummarySchedule;

/**
 * Created by pheynix on 6/24/15.
 */
public class MainActivityViewPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;

    public MainActivityViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        if (position == 0){
            fragment = new ScheduleFragment();
        }
//        在tab中删除记录页
//        if (position == 1){
//            fragment = new RecordFragment();
//        }
        if (position == 1){
            fragment = new SummarySchedule();
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }//在tab中删除记录页

    @Override
    public CharSequence getPageTitle(int position) {
        String [] titles = mContext.getResources().getStringArray(R.array.tabs_titles);
        return titles[position];
    }

}
