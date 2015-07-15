package com.pheynix.moli_schedule.Creator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pheynix.moli_schedule.R;
import com.pheynix.moli_schedule.ScheduleFragment.ScheduleFragment;
import com.pheynix.moli_schedule.TestMessage;
import com.pheynix.moli_schedule.Util.DBUtil;
import com.pheynix.moli_schedule.Util.DateTimeUtils;
import com.pheynix.moli_schedule.Item.Schedule;
import com.rey.material.widget.Slider;
import com.rey.material.widget.Spinner;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;


public class CreateSchedule extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener,DatePickerDialog.OnDateSetListener, View.OnClickListener,Spinner.OnItemClickListener{
    private Toolbar mToolbar;
    private TextView tv_time_start,tv_time_last;
    private String date;
    private String time;
    private TextView tv_quadrant_1_1,tv_quadrant_0_1,tv_quadrant_1_0,tv_quadrant_0_0;
    private ImageView ic_quadrant_1_1,ic_quadrant_0_1,ic_quadrant_1_0,ic_quadrant_0_0;
    private FloatingActionButton mFab;
    private Spinner spinner_category;
    private EditText et_schedule_detail;
    private ArrayAdapter<String> spinnerAdapter;
    private ArrayList<String> category;
    private Schedule oldSchedule = null;
    private Schedule newSchedule;
    private Slider slider_volume;
    private SwitchCompat switch_vibration;
    private Intent intent_schedule_fragment;

    private static final String PRE_CREATE_SCHEDULE = "create_schedule";
    private static final String VOLUME = "volume";
    private static final String VIBRATION = "vibration";

//    private StringBuffer dateBuffer;
//    private StringBuffer timeBuffer;
    private Calendar calendar_time_start;
    private Calendar calendar_time_last;

    private DBUtil dbUtil;

    public static final int RESULT_CODE_CREATE_SCHEDULE = 005;
    public static final int RESULT_CODE_ALTER_SCHEDULE = 010;
    public static final int RESULT_CODE_DELETE_SCHEDULE = 011;



    //应重新设计排版此Activity的布局，参考http://www.jcodecraeer.com/a/opensource/2015/0125/2358.html
    //应重新设计排版此Activity的布局，参考http://www.jcodecraeer.com/a/opensource/2015/0125/2358.html
    //应重新设计排版此Activity的布局，参考http://www.jcodecraeer.com/a/opensource/2015/0125/2358.html
    //应重新设计排版此Activity的布局，参考http://www.jcodecraeer.com/a/opensource/2015/0125/2358.html
    //应重新设计排版此Activity的布局，参考http://www.jcodecraeer.com/a/opensource/2015/0125/2358.html
    //同时参考http://huaban.com/pins/418456085/
    //http://huaban.com/pins/404132521/




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_schedule);

        dbUtil = new DBUtil(this);

        //初始化应该尽量在所有操作前
        initView();

        intent_schedule_fragment = getIntent();

        try {
            oldSchedule = (Schedule) intent_schedule_fragment.getExtras().getSerializable(ScheduleFragment.SCHEDULE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (oldSchedule != null) {
            spinner_category.setSelection(getSpinnerPosition());
            et_schedule_detail.setText(oldSchedule.getDetail());
            et_schedule_detail.setSelection(oldSchedule.getDetail().length());

//            tv_time_start.setText(getDisplayDate());
//            tv_time_last.setText(getDisplayTime());
            tv_time_start.setText(DateTimeUtils.longToDate(oldSchedule.getTime_start()));
            tv_time_last.setText(DateTimeUtils.longToTime(oldSchedule.getTime_last()));

            setUpUrgency();
            slider_volume.setValue((float) oldSchedule.getVolume(),false);
            switch_vibration.setChecked(oldSchedule.isVibration());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create_schedule, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.delete_schedule) {
            if (oldSchedule != null){
                dbUtil.deleteSchedule(oldSchedule.getId());

                Intent intent = new Intent();
                intent.putExtra("isDeleted",true);
                setResult(RESULT_CODE_DELETE_SCHEDULE,intent);
                Toast.makeText(this,"成功删除！",Toast.LENGTH_SHORT).show();

                finish();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private void setUpUrgency(){
        hideTextView();
        switch (oldSchedule.getUrgency()){
            case 1:
                tv_quadrant_1_1.setVisibility(View.VISIBLE);
                break;
            case 2:
                tv_quadrant_1_0.setVisibility(View.VISIBLE);
                break;
            case 3:
                tv_quadrant_0_1.setVisibility(View.VISIBLE);
                break;
            case 4:
                tv_quadrant_0_0.setVisibility(View.VISIBLE);
                break;
            default:
                tv_quadrant_1_1.setVisibility(View.VISIBLE);
        }
    }

//    private String getDisplayTime(){
//        String[] time = oldSchedule.getTime_last().split(" ");
//        timeBuffer = new StringBuffer();
//        timeBuffer.append(time[0]+" "+time[1]);
//        return time[0]+"时"+time[1]+"分";
//
//
//    }
//
//    private String getDisplayDate(){
//        String[] date = oldSchedule.getTime_start().split(" ");
//        dateBuffer = new StringBuffer();
//        dateBuffer.append(date[0] + " " + date[1] + " " + date[2] + " " + date[3] + " " + date[4]);
//        return date[0]+"年"+date[1]+"月"+date[2]+"日"+date[3]+"时"+date[4]+"分";
//    }

    private int getSpinnerPosition() {
        int i;
        for (i = 0; i<category.size() ; i++){
            //String与String之间的比较应该用String.equals，不能用“==”
            if (oldSchedule.getCategory().equals(category.get(i))){
                return i;
            }
        }
        return i;
    }


    private void initView() {

        //显示Toolbar
        mToolbar = (Toolbar) findViewById(R.id.id_toolbar_create_schedule);
        setSupportActionBar(mToolbar);
        //显示返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //返回按钮销毁CreateSchedule Activity
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //重要－紧急
        tv_quadrant_1_1 = (TextView) findViewById(R.id.tv_quadrant_1_1);
        //重要－不紧急
        tv_quadrant_1_0 = (TextView) findViewById(R.id.tv_quadrant_1_0);
        //不重要－紧急
        tv_quadrant_0_1 = (TextView) findViewById(R.id.tv_quadrant_0_1);
        //不重要－不紧急
        tv_quadrant_0_0 = (TextView) findViewById(R.id.tv_quadrant_0_0);

        ic_quadrant_1_1 = (ImageView) findViewById(R.id.ic_quadrant_1_1);
        ic_quadrant_1_0 = (ImageView) findViewById(R.id.ic_quadrant_1_0);
        ic_quadrant_0_1 = (ImageView) findViewById(R.id.ic_quadrant_0_1);
        ic_quadrant_0_0 = (ImageView) findViewById(R.id.ic_quadrant_0_0);

        ic_quadrant_1_1.setOnClickListener(this);
        ic_quadrant_1_0.setOnClickListener(this);
        ic_quadrant_0_1.setOnClickListener(this);
        ic_quadrant_0_0.setOnClickListener(this);

        calendar_time_last = Calendar.getInstance();
        calendar_time_start = Calendar.getInstance();
        calendar_time_last.clear();
        calendar_time_start.clear();
//        Log.e("pheynix",calendar_time_start.getTimeInMillis()+"");
//        Log.e("pheynix",calendar_time_last.getTimeInMillis()+"");

        tv_time_start = (TextView) findViewById(R.id.id_time_start);
        tv_time_last = (TextView) findViewById(R.id.id_time_last);
        tv_time_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        //持续时间响应逻辑
        tv_time_last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog dialog = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(RadialPickerLayout radialPickerLayout, int i, int i1) {
                        if (i == 0) {
                            tv_time_last.setText(i1 + "分钟");
                        } else {
                            tv_time_last.setText(i + "时" + i1 + "分");
                        }
                        calendar_time_last.set(Calendar.HOUR_OF_DAY, i);
                        calendar_time_last.set(Calendar.MINUTE, i1);

                    }
                }, 0, 0, true);
                dialog.show(getFragmentManager(), "time_picker_dialog_time_last");
            }
        });

        spinner_category = (Spinner) findViewById(R.id.spinner_category);
        category = new DBUtil(this).getAllCategoryName();
        spinnerAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,category);
        spinner_category.setAdapter(spinnerAdapter);
        spinner_category.setOnItemClickListener(this);

        //FloatingActionBar响应逻辑，1保存当前日程2保存当前的偏好设置（是否新建日程类、提示音大小、震动）
        mFab = (FloatingActionButton) findViewById(R.id.fab_create_schedule);
        mFab.setOnClickListener(this);

        et_schedule_detail = (EditText) findViewById(R.id.et_schedule_detail);
        slider_volume = (Slider) findViewById(R.id.slider_volume);
        switch_vibration = (SwitchCompat) findViewById(R.id.switch_vibration);

        SharedPreferences sharedPreferences = getSharedPreferences(PRE_CREATE_SCHEDULE, Context.MODE_PRIVATE);

        switch_vibration.setChecked(sharedPreferences.getBoolean(VIBRATION,true));
        slider_volume.setValue((float)sharedPreferences.getInt(VOLUME,50),false);

        newSchedule = new Schedule();
    }

    //开始时间－选择日期的Dialog
    private void showDatePickerDialog(){
        Calendar now = Calendar.getInstance();
        DatePickerDialog dialog = DatePickerDialog.newInstance(this,now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH));
        dialog.show(getFragmentManager(),"data_picker_dialog");
    }

    //开始时间－选择时间的Dialog
    private void showTimePickerDialog(){
        Calendar now = Calendar.getInstance();
        TimePickerDialog dialog = TimePickerDialog.newInstance(this,now.get(Calendar.HOUR_OF_DAY),now.get(Calendar.MINUTE),true);
        dialog.show(getFragmentManager(),"time_picker_dialog");
    }

    //响应开始时间－选择日期Dialog
    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int i, int i1, int i2) {
        date = i+"年"+(i1+1)+"月"+i2+"日";
//        dateBuffer = new StringBuffer(i+" "+i1+" "+i2 +" ");
        calendar_time_start.set(Calendar.YEAR,i);
        calendar_time_start.set(Calendar.MONTH,i1);//Calendar中的month是0基的（0 == 1月），正好这个Dialog出来的i1也是0基的..........表示非常无语
        calendar_time_start.set(Calendar.DAY_OF_MONTH,i2);


        showTimePickerDialog();

    }

    //响应开始时间－选择时间Dialog
    @Override
    public void onTimeSet(RadialPickerLayout radialPickerLayout, int i, int i1) {
        time = i+"时"+i1+"分";
        tv_time_start.setText(date+time);

//        dateBuffer.append(i + " " + i1);
        calendar_time_start.set(Calendar.HOUR_OF_DAY,i);
        calendar_time_start.set(Calendar.MINUTE,i1);

    }

    //点击重要紧急度按钮的响应动作之前，把所有文字隐藏
    private void hideTextView(){
        tv_quadrant_1_1.setVisibility(View.GONE);
        tv_quadrant_1_0.setVisibility(View.GONE);
        tv_quadrant_0_1.setVisibility(View.GONE);
        tv_quadrant_0_0.setVisibility(View.GONE);
    }

    //响应重要紧急度按钮
    @Override
    public void onClick(View v) {

        //如果不是fab点击事件，则隐藏文字
        if (v.getId() != R.id.fab_create_schedule){
            hideTextView();
        }
        switch (v.getId()){
            //重要－紧急
            case R.id.ic_quadrant_1_1:
                tv_quadrant_1_1.setVisibility(View.VISIBLE);
                break;
            //重要－不紧急
            case R.id.ic_quadrant_1_0:
                tv_quadrant_1_0.setVisibility(View.VISIBLE);
                break;
            //不重要－紧急
            case R.id.ic_quadrant_0_1:
                tv_quadrant_0_1.setVisibility(View.VISIBLE);
                break;
            //不重要－不紧急
            case R.id.ic_quadrant_0_0:
                tv_quadrant_0_0.setVisibility(View.VISIBLE);
                break;
            //FloatingActionBar响应逻辑
            case R.id.fab_create_schedule:

                newSchedule.setCategory(category.get(spinner_category.getSelectedItemPosition()));
                newSchedule.setDetail(et_schedule_detail.getText().toString());

//                newSchedule.setTime_start(dateBuffer.toString());
//                newSchedule.setTime_last(timeBuffer.toString());
                newSchedule.setTime_start(calendar_time_start.getTimeInMillis());
                newSchedule.setTime_last(calendar_time_last.getTimeInMillis());

                newSchedule.setUrgency(getUrgency());
                newSchedule.setVibration(switch_vibration.isChecked());
                newSchedule.setVolume(slider_volume.getValue());


                if (oldSchedule != null){

                    newSchedule.setStatus(oldSchedule.getStatus());
                    newSchedule.setId(oldSchedule.getId());
                    dbUtil.alterSchedule(newSchedule);

                    Intent intent = new Intent();
                    intent.putExtra("isAltered",true);
                    setResult(RESULT_CODE_ALTER_SCHEDULE,intent);
                    Toast.makeText(this,"日程已修改！",Toast.LENGTH_SHORT).show();
                }else {

                    newSchedule.setStatus(1);//创建日程默认状态为“未完成”
                    dbUtil.addSchedule(newSchedule);

                    Intent intent = new Intent();
                    intent.putExtra("isCreated",true);
                    setResult(RESULT_CODE_CREATE_SCHEDULE,intent);
                    Toast.makeText(this,"日程创建成功！",Toast.LENGTH_SHORT).show();
                }

                SharedPreferences sharedPreferences = getSharedPreferences(PRE_CREATE_SCHEDULE,Context.MODE_PRIVATE);
                sharedPreferences.edit().putInt(VOLUME,slider_volume.getValue()).commit();
                sharedPreferences.edit().putBoolean(VIBRATION,switch_vibration.isChecked()).commit();
                finish();

                break;
        }
    }

    private int getUrgency() {
        if (tv_quadrant_1_1.getVisibility() == View.VISIBLE){
            return 1;
        }
        if (tv_quadrant_1_0.getVisibility() == View.VISIBLE){
            return 2;
        }
        if (tv_quadrant_0_1.getVisibility() == View.VISIBLE){
            return 3;
        }
        if (tv_quadrant_0_0.getVisibility() == View.VISIBLE){
            return 4;
        }

        return 0;
    }


    //日程类别Spinner响应逻辑，［spinner一定要记得设置setOnItemClickListener()］
    @Override
    public boolean onItemClick(final Spinner spinner, View view, int i, long l) {
        switch (i){
            case 0:

                break;
            case 1:
                //跳转到新的Activity完成Category的创建
                Intent intent = new Intent(this,CreateScheduleCategory.class);
                startActivityForResult(intent,001);
                break;
            default:

                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //创建成功更新spinner
        if (resultCode == 002){
            Toast.makeText(this,"新日程类创建成功",Toast.LENGTH_SHORT).show();
            category = new DBUtil(this).getAllCategoryName();
            ArrayAdapter<String> newAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,category);
            spinner_category.setAdapter(newAdapter);
            spinner_category.setSelection(category.size()-1);


        }
        //创建取消更新spinner
        if (resultCode == 003){
            spinner_category.setSelection(0);
            new TestMessage(CreateSchedule.this,"已取消创建新日程类");
        }
    }
}
