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

    public SummaryDailyFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_summary_daily, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_summary_daily);
        mFab = (FloatingActionButton) view.findViewById(R.id.fab_summary_daily);
        mFab.setOnClickListener(this);

//        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        items = new ArrayList<>();
        SummaryDailyItem item1 = new SummaryDailyItem("总记录时间",56);
        items.add(item1);
        SummaryDailyItem item2 = new SummaryDailyItem("日程完成率",86);
        items.add(item2);
        SummaryDailyItem item3 = new SummaryDailyItem("coding",36);
        items.add(item3);
        SummaryDailyItem item4 = new SummaryDailyItem("reading",66);
        items.add(item4);
        SummaryDailyItem item5 = new SummaryDailyItem("swim!",96);
        items.add(item5);

        SummaryDailyAdapter mAdapter = new SummaryDailyAdapter(getActivity(),items);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onClick(View v) {

    }
}
