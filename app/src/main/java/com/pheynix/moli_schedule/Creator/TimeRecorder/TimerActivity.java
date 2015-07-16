package com.pheynix.moli_schedule.Creator.TimeRecorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.pheynix.moli_schedule.CustomView.MoliTimer;
import com.pheynix.moli_schedule.R;
import com.pheynix.moli_schedule.ScheduleFragment.ScheduleFragment;

public class TimerActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private Button btn_done,btn_other,btn_start_pause;
    private MoliTimer moliTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        initializeView();


        Intent intent = getIntent();
        long extra = intent.getLongExtra(ScheduleFragment.EXTRA_NAME_TIMER_ACTIVITY, -28800*1000);
        moliTimer.setTimeStart(extra);

    }

    private void initializeView() {
        //初始化ToolBar
        mToolbar = (Toolbar) findViewById(R.id.tb_timer_activity);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //返回按钮销毁CreateSchedule Activity
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_done = (Button) findViewById(R.id.btn_done_timer_activity);
        btn_other = (Button) findViewById(R.id.btn_other_timer_activity);
        btn_start_pause = (Button) findViewById(R.id.btn_start_pause_timer_activity);

        moliTimer = (MoliTimer) findViewById(R.id.moli_timer_timer_activity);
    }
}
