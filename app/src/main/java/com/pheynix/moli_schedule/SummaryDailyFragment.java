package com.pheynix.moli_schedule;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SummaryDailyFragment extends Fragment implements View.OnClickListener {
    private View view;
    private RecyclerView mRecyclerView;
    private FloatingActionButton mFab;
    private ArrayList<SummaryDailyItem> items;

    private DBUtil dbUtil;


    public SummaryDailyFragment() {
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
        SummaryDailyAdapter mAdapter = new SummaryDailyAdapter(getActivity(),dbUtil.getDailySummaryItems());
        mRecyclerView.setAdapter(mAdapter);

        return view;

    }

    @Override
    public void onClick(View v) {

    }
}
