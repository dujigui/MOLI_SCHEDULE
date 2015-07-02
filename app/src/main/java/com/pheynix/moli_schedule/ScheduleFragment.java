package com.pheynix.moli_schedule;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ScheduleFragment extends Fragment implements View.OnClickListener {
    private RecyclerView mRecyclerView;
    private ScheduleAdapter adapter;
    private FloatingActionButton mFab;
    private DBUtil dbUtil;
    private ArrayList<Schedule> schedules;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);

        dbUtil = new DBUtil(getActivity());
        schedules = dbUtil.getAllSchedules();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_schedule);
        mFab = (FloatingActionButton) view.findViewById(R.id.id_fab_fragment_schedule);
        mFab.setOnClickListener(this);

        //设置recyclerView的layoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        adapter = new ScheduleAdapter(getActivity(),schedules);
        mRecyclerView.setAdapter(adapter);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL));

        mRecyclerView.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.id_fab_fragment_schedule:

                //启动创建Schedule的Activity
                Intent intent = new Intent(getActivity(),CreateSchedule.class);
                startActivityForResult(intent, 004);

                break;
            case R.id.rv_schedule:

                new TestMessage(getActivity(),"haha");

                break;
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //日程创建后，更新schedules的数据，不能简单地更改schedules的指向，必须清空schedules并重新添加数据
        //详细原因在http://www.bkjia.com/Androidjc/869179.html
        if (resultCode == 005){
            schedules.clear();
            schedules.addAll(dbUtil.getAllSchedules());
            adapter.notifyDataSetChanged();
        }
    }
}
