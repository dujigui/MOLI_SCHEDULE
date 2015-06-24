package com.pheynix.moli_schedule;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ScheduleFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private ScheduleAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.id_recyclerView_schedule);

        adapter = new ScheduleAdapter(getActivity(),getData());
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    public ArrayList<ScheduleInfo> getData(){
        ArrayList<ScheduleInfo> scheduleInfos = new ArrayList<>();

        for (int i=0 ; i<20 ; i++){
            ScheduleInfo scheduleInfo = new ScheduleInfo("20:0" + i + " - go for lunch",R.drawable.ic_schedule_done);
            scheduleInfos.add(scheduleInfo);
        }

        return scheduleInfos;
    }
}
