package com.pheynix.moli_schedule.SummaryFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pheynix.moli_schedule.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class WeeklySummaryFragment extends Fragment {


    public WeeklySummaryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_summary_weekly, container, false);
    }


}
