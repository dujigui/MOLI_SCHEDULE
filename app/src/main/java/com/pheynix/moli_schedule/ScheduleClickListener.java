package com.pheynix.moli_schedule;

import android.view.View;

/**
 * Created by pheynix on 7/3/15.
 */
public interface ScheduleClickListener {
    public void onScheduleClick(View view,int position);//响应日程项点击事件，跳转到编辑页面
    public void onUrgencyClick(View view,int position);//响应紧急度点击事件，更新数据库和RecyclerView
}
