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

public class ScheduleFragment extends Fragment implements View.OnClickListener,ScheduleClickListener {
    public static final int REQUEST_CODE_ALTER_SCHEDULE = 007;
    public static final int REQUEST_CODE_CREATE_SCHEDULE = 004;
    public static final String SCHEDULE = "schedule_need_to_be_modify";

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
        mFab = (FloatingActionButton) view.findViewById(R.id.fab_fragment_schedule);
        mFab.setOnClickListener(this);

        //设置recyclerView的layoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        adapter = new ScheduleAdapter(getActivity(),schedules,this);
        mRecyclerView.setAdapter(adapter);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL));


        return view;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.fab_fragment_schedule:

                //启动创建Schedule的Activity
                Intent intent = new Intent(getActivity(),CreateSchedule.class);
                startActivityForResult(intent, REQUEST_CODE_CREATE_SCHEDULE);

                break;
        }
    }

    //创建日程后返回主页面，更新数据
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == CreateSchedule.RESULT_CODE_CREATE_SCHEDULE){
            updateSchedules();
        }
        if (resultCode == CreateSchedule.RESULT_CODE_ALTER_SCHEDULE){
            updateSchedules();
        }
        if (resultCode == CreateSchedule.RESULT_CODE_DELETE_SCHEDULE){
            updateSchedules();
        }
    }

    //日程项点击时的回调函数
    @Override
    public void onScheduleClick(View view, int position) {

        Bundle bundle = new Bundle();
        bundle.putSerializable(SCHEDULE,schedules.get(position));
        Intent intent = new Intent(getActivity(),CreateSchedule.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUEST_CODE_ALTER_SCHEDULE);

    }

    //紧急度点击时的回调函数（紧急度点击时会把日程状态改变为“已完成”）
    @Override
    public void onUrgencyClick(View view, int position) {

        Schedule schedule = schedules.get(position);
        switch (schedule.getStatus()){
            case 1:
                dbUtil.changeScheduleStatus(schedule.getId(),3);
                break;
            case 2:

                break;

            case 3:
                dbUtil.changeScheduleStatus(schedule.getId(),1);
                break;
            default:
                break;
        }
        updateSchedules();
    }


    //重新装载RecyclerView的数据
    //日程创建后，更新schedules的数据，不能简单地更改schedules的指向，必须清空schedules并重新添加数据
    //详细原因在http://www.bkjia.com/Androidjc/869179.html
    //更改为public
    public void updateSchedules(){
        schedules.clear();
        schedules.addAll(dbUtil.getAllSchedules());
        adapter.notifyDataSetChanged();
    }
}
