package com.pheynix.moli_schedule.SummaryFragment;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pheynix.moli_schedule.R;
import com.pheynix.moli_schedule.Util.DBUtil;
import com.pheynix.moli_schedule.Item.SummaryDailyItem;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class DailySummaryFragment extends Fragment implements View.OnClickListener {
    private View view;
    private RecyclerView mRecyclerView;
    private FloatingActionButton mFab;
    private ArrayList<SummaryDailyItem> items;

    private DBUtil dbUtil;


    public DailySummaryFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_summary_daily, container, false);
        items = new ArrayList<>();
        dbUtil = new DBUtil(getActivity());


        mFab = (FloatingActionButton) view.findViewById(R.id.fab_summary_daily);
        mFab.setOnClickListener(this);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_summary_daily);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DailySummaryAdapter mAdapter = new DailySummaryAdapter(getActivity(),dbUtil.getSummaryItems(Calendar.getInstance()));
        mRecyclerView.setAdapter(mAdapter);

        return view;

    }

    @Override
    public void onClick(View v) {

    }
}
