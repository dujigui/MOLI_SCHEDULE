package com.pheynix.moli_schedule;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
//    private SharedPreferences sharedPreferences;
//    private SharedPreferences.Editor editor;
//    private String[] category;
    private ArrayAdapter<String> spinnerAdapter;
    private ArrayList<String> category;


//    private static final String PRE_NAME = "create_schedule_preferences";
    private static final String SCHEDULE_CATEGORY = "category";
    private static final String DEFAULT_CATEGORY_NORMAL = "普通日程";
    private static final String DEFAULT_CATEGORY_NEW = "新日程类";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_schedule);

        //初始化应该在所有操作前
        initView();

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

                    }
                }, 0, 0, true);
                dialog.show(getFragmentManager(), "time_picker_dialog_time_last");
            }
        });

//        sharedPreferences = getSharedPreferences(PRE_NAME, Context.MODE_PRIVATE);
//        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
//        editor = sharedPreferences.edit();
//        editor.clear();
//
//        category = preferenceToString(sharedPreferences);
//
//        spinner_category = (Spinner) findViewById(R.id.spinner_category);
//        spinnerAdapter = new ArrayAdapter<String>(CreateSchedule.this,android.R.layout.simple_spinner_dropdown_item,category);
//        spinner_category.setAdapter(spinnerAdapter);
//        spinner_category.setOnItemClickListener(this);

        spinner_category = (Spinner) findViewById(R.id.spinner_category);
        category = new DBUtil(this).getAllCategoryName();
        spinnerAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,category);
        spinner_category.setAdapter(spinnerAdapter);
        spinner_category.setOnItemClickListener(this);

        //FloatingActionBar响应逻辑，1保存当前日程2保存当前的偏好设置（是否新建日程类、提示音大小、震动）
        mFab = (FloatingActionButton) findViewById(R.id.fab_create_schedule);

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
        date = i+"年"+i1+"月"+i2+"日";
        showTimePickerDialog();

    }

    //响应开始时间－选择时间Dialog
    @Override
    public void onTimeSet(RadialPickerLayout radialPickerLayout, int i, int i1) {
        time = i+"时"+i1+"分";
        tv_time_start.setText(date+time);
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
            case R.id.ic_quadrant_0_1:
                tv_quadrant_0_1.setVisibility(View.VISIBLE);
                break;
            //不重要－紧急
            case R.id.ic_quadrant_1_0:
                tv_quadrant_1_0.setVisibility(View.VISIBLE);
                break;
            //不重要－不紧急
            case R.id.ic_quadrant_0_0:
                tv_quadrant_0_0.setVisibility(View.VISIBLE);
                break;
            //FloatingActionBar响应逻辑
            case R.id.fab_create_schedule:

//                editor = sharedPreferences.edit();
//                editor.putString(SCHEDULE_CATEGORY,DEFAULT_CATEGORY_NORMAL + " " + DEFAULT_CATEGORY_NEW + " " + );

                break;
        }
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


                //inflate dialog要用到的view
//                LayoutInflater inflater = getLayoutInflater();
//                final View dialogView = inflater.inflate(R.layout.schedule_category_create_dialog,null);
//
//                final Dialog dialog = new Dialog(this);
//                dialog.setTitle("新日程类");
//                dialog.positiveAction("确定");
//                dialog.negativeAction("取消");
//                dialog.positiveActionClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        EditText editText = (EditText) dialogView.findViewById(R.id.category_create_dialog_edit_text);
//                        addScheduleCategory(sharedPreferences,editor,editText.getText().toString());
//
//                        Toast.makeText(CreateSchedule.this,"新日程类已增加!",Toast.LENGTH_SHORT).show();
//
//                        category = preferenceToString(getSharedPreferences(PRE_NAME, Context.MODE_PRIVATE));
//                        spinnerAdapter.notifyDataSetChanged();
//                        dialog.dismiss();
//                    }
//                });
//                dialog.negativeActionClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        spinner_category.setSelection(0);
//                        dialog.dismiss();
//                    }
//                });
//                dialog.setContentView(dialogView);
//                dialog.cancelable(false);
//                dialog.show();


                break;
            default:

                break;
        }
        return true;
    }

//    private String[] preferenceToString(SharedPreferences sharedPreferences){
//        String sharedPreferencesString = sharedPreferences.getString(SCHEDULE_CATEGORY, DEFAULT_CATEGORY_NORMAL + " " + DEFAULT_CATEGORY_NEW);//默认显示“普通日程”和“新日程类"
//        return sharedPreferencesString.split(" ");
//    }

//    private void addScheduleCategory(SharedPreferences sharedPreferences,SharedPreferences.Editor editor,String newCategory){
//        String[] oldCategory = preferenceToString(sharedPreferences);
//        StringBuffer buffer = new StringBuffer();
//
//        for (int i = 0 ; i<oldCategory.length ; i++){
//            buffer.append(oldCategory[i]);
//            buffer.append(" ");
//        }
//
//        editor.clear();
//        buffer.append(newCategory);
//        editor.putString(SCHEDULE_CATEGORY,buffer.toString());
//        editor.commit();
//        editor.clear();

//    }


//add on Change Listener
//    share.registerOnSharedPreferenceChangeListener(mListener);


//remove on Change Listener
//    preferences.unregisterOnSharedPreferenceChangeListener(mListener);

    // listener example


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //创建成功更新spinner
        if (resultCode == 002){
            Toast.makeText(this,"新日程类创建成功",Toast.LENGTH_SHORT).show();
            category = new DBUtil(this).getAllCategoryName();
            ArrayAdapter<String> newAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,category);
            spinner_category.setAdapter(newAdapter);
        }
        //创建取消更新spinner
        if (resultCode == 003){
            spinner_category.setSelection(0);
            new TestMessage(CreateSchedule.this,"wuwu");
        }
    }
}
