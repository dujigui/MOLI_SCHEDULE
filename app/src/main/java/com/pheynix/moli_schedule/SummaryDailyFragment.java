package com.pheynix.moli_schedule;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pheynix.moli_schedule.PercentageView.PercentageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class SummaryDailyFragment extends Fragment {
    private View view;
    private PercentageView pv_complete_ratio_daily,pv_time_summary_daily;

    public SummaryDailyFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_summary_daily, container, false);
        pv_complete_ratio_daily = (PercentageView) getChildFragmentManager().findFragmentById(R.id.pv_complete_ratio_daily);
        pv_time_summary_daily = (PercentageView) getChildFragmentManager().findFragmentById(R.id.pv_time_summary_daily);

        pv_complete_ratio_daily.setPercentage(47);
        pv_time_summary_daily.setPercentage(78);

        pv_complete_ratio_daily.setColor(PercentageView.colors.get(0));
        pv_time_summary_daily.setColor(PercentageView.colors.get(1));


        return view;
    }
}
