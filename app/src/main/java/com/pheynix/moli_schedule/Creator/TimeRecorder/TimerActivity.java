package com.pheynix.moli_schedule.Creator.TimeRecorder;

import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.pheynix.moli_schedule.CustomView.MoliTimer;
import com.pheynix.moli_schedule.Item.Schedule;
import com.pheynix.moli_schedule.R;
import com.pheynix.moli_schedule.ScheduleFragment.ScheduleFragment;
import com.pheynix.moli_schedule.Util.DBUtil;

import me.drakeet.materialdialog.MaterialDialog;

public class TimerActivity extends AppCompatActivity implements View.OnClickListener, MoliTimer.OnTimeChangeListener {

    private boolean isTimerStarted = false;

    private DBUtil dbUtil;
    private Schedule schedule;

    private Toolbar mToolbar;
    private Button btn_done,btn_other,btn_start_pause;
    private MoliTimer moliTimer;

    private long timeStart = 0;
    private long timeRemain = 0;
    private long timePass = 0;

    public static final int RESULT_CODE_DONE = 054;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        initializeView();

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

        moliTimer = (MoliTimer) findViewById(R.id.moli_timer_timer_activity);
        btn_done = (Button) findViewById(R.id.btn_done_timer_activity);
        btn_other = (Button) findViewById(R.id.btn_other_timer_activity);
        btn_start_pause = (Button) findViewById(R.id.btn_start_pause_timer_activity);

        btn_done.setOnClickListener(this);
        btn_start_pause.setOnClickListener(this);
        btn_other.setOnClickListener(this);

        Intent intent = getIntent();
        schedule = (Schedule) intent.getSerializableExtra(ScheduleFragment.EXTRA_NAME_TIMER_ACTIVITY);

        //日程已完成，但是用户希望增加时间
        if (schedule.getStatus() == 3){
            moliTimer.setTimeStart(0);
        }else {
            moliTimer.setTimeStart(schedule.getTime_last()-schedule.getTime_recorded());
        }


        moliTimer.setOnTimeChangeListener(this);

        dbUtil = new DBUtil(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_done_timer_activity:

                done();

                break;

            case R.id.btn_other_timer_activity:

                if (timePass != 0){

                    schedule.setTime_recorded(schedule.getTime_recorded() + timePass);

                    if (schedule.getStatus() == 3){

                        schedule.setTime_last(schedule.getTime_last() + timeStart);
                        schedule.setStatus(1);

                    }

                    dbUtil.alterSchedule(schedule);

                }

                moliTimer.stop();
                returnToPrevious();

                break;

            case R.id.btn_start_pause_timer_activity:

                if (!isTimerStarted){
                    btn_start_pause.setText("暂停计时");
                    moliTimer.start();
                    isTimerStarted = true;

                }else {
                    btn_start_pause.setText("开始计时");
                    moliTimer.stop();
                    isTimerStarted = false;
                }

                break;
        }
    }

    private void done() {
        if (timePass == 0){

            schedule.setTime_recorded(schedule.getTime_last());

        }else {

            schedule.setTime_recorded(schedule.getTime_recorded()+timePass);
            schedule.setTime_last(schedule.getTime_recorded());

        }

        schedule.setStatus(3);
        dbUtil.alterSchedule(schedule);

        Toast.makeText(this, "Well done!", Toast.LENGTH_SHORT).show();
        returnToPrevious();
    }

    private void returnToPrevious(){

        Intent intent = new Intent();
        setResult(RESULT_CODE_DONE,intent);
        moliTimer.stop();
        finish();

    }

    @Override
    public void onTimerStart(long timeStart) {

    }

    @Override
    public void onTimeChange(long timeStart, long timeRemain) {
        this.timeStart = timeStart;
        this.timeRemain = timeRemain;
        timePass = timeStart - timeRemain;
    }

    @Override
    public void onTimeStop(final long timeStart, final long timeRemain) {

        //播放提示音
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), uri);
        r.play();

        //弹出对话框
        final MaterialDialog mMaterialDialog = new MaterialDialog(this);
        mMaterialDialog.setTitle("计时结束");
        mMaterialDialog.setMessage("日程是否已完成？");

        mMaterialDialog.setPositiveButton("已完成", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                done();
                mMaterialDialog.dismiss();
            }
        });

        mMaterialDialog.setNegativeButton("未完成", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                schedule.setTime_recorded(schedule.getTime_last());
                dbUtil.alterSchedule(schedule);
                returnToPrevious();
                mMaterialDialog.dismiss();
            }
        });

        mMaterialDialog.setCanceledOnTouchOutside(false);
        mMaterialDialog.show();


    }
}
