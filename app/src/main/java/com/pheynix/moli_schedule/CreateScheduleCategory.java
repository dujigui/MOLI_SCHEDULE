package com.pheynix.moli_schedule;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.rey.material.widget.Button;
import com.rey.material.widget.CheckBox;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

public class CreateScheduleCategory extends AppCompatActivity implements View.OnClickListener {
    private EditText et_name,et_time_target;
    private Button btn_time_cycle;
    private TextInputLayout textInputLayout_name,textInputLayout_time_target;
    private CheckBox cb_monday,cb_tuesday,cb_wednesday,cb_thursday,cb_friday,cb_saturday,cb_sunday;
    private FloatingActionButton mFab;

    private Category category;
    private DBUtil dbUtil;

    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_schedule_category);

        mToolbar = (Toolbar) findViewById(R.id.tb_create_category);
        setSupportActionBar(mToolbar);
        //显示返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //返回按钮销毁CreateSchedule Activity
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.putExtra("isCreated",false);
                setResult(003,intent);

                finish();
            }
        });

        initView();

        initEvent();

    }



    private void initView() {
        textInputLayout_name = (TextInputLayout) findViewById(R.id.tip_create_category_name);
        et_name = textInputLayout_name.getEditText();

        textInputLayout_time_target = (TextInputLayout) findViewById(R.id.tip_create_category_time_target);
        et_time_target = textInputLayout_time_target.getEditText();


        btn_time_cycle = (Button) findViewById(R.id.btn_create_category_time_cycle);
        mFab = (FloatingActionButton) findViewById(R.id.fab_create_category_done);
        mFab.setOnClickListener(this);

        cb_monday = (CheckBox) findViewById(R.id.cb_monday);
        cb_tuesday = (CheckBox) findViewById(R.id.cb_tuesday);
        cb_wednesday = (CheckBox) findViewById(R.id.cb_wednesday);
        cb_thursday = (CheckBox) findViewById(R.id.cb_thursday);
        cb_friday = (CheckBox) findViewById(R.id.cb_friday);
        cb_saturday = (CheckBox) findViewById(R.id.cb_saturday);
        cb_sunday = (CheckBox) findViewById(R.id.cb_sunday);
    }

    private void initEvent() {

        category = new Category();
        dbUtil = new DBUtil(this);

        et_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0){
                    textInputLayout_name.setError("名称不能为空");
                    textInputLayout_name.setErrorEnabled(true);
                }else {
                    textInputLayout_name.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_time_target.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0){
                    textInputLayout_time_target.setError("总时间不能为空！");
                    textInputLayout_time_target.setErrorEnabled(true);
                }else {
                    textInputLayout_time_target.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btn_time_cycle.setOnClickListener(this);
//        btn_periodicity.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_create_category_time_cycle:
                TimePickerDialog dialog = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(RadialPickerLayout radialPickerLayout, int i, int i1) {
                        category.setTime_cycle(i + " " + i1);
                        btn_time_cycle.setText(i+"时"+i1+"分");
                    }
                }, 0, 0, true);
                dialog.show(getFragmentManager(),"time_cycle");
                break;

            case R.id.fab_create_category_done:

                category.setPeriodicity(getPeriodicity());
                category.setTime_summary("0");
                category.setTime_target(et_time_target.getText().toString());
                category.setCategory_name(et_name.getText().toString());

                dbUtil.addCategory(category);//初始化Category表格，新增“普通日程”和“新建日程”

                Intent intent = new Intent();
                intent.putExtra("isCreated",true);
                setResult(002,intent);
                finish();

                break;
        }

    }

    public String getPeriodicity() {
//        cb_monday = (CheckBox) findViewById(R.id.cb_monday);
//        cb_tuesday = (CheckBox) findViewById(R.id.cb_tuesday);
//        cb_wednesday = (CheckBox) findViewById(R.id.cb_wednesday);
//        cb_thursday = (CheckBox) findViewById(R.id.cb_thursday);
//        cb_friday = (CheckBox) findViewById(R.id.cb_friday);
//        cb_saturday = (CheckBox) findViewById(R.id.cb_saturday);
//        cb_sunday = (CheckBox) findViewById(R.id.cb_sunday);

        StringBuffer buffer = new StringBuffer();
        if (cb_sunday.isChecked()){
            buffer.append("sunday ");
        }
        if (cb_monday.isChecked()){
            buffer.append("monday ");
        }
        if (cb_tuesday.isChecked()){
            buffer.append("tuesday ");
        }
        if (cb_wednesday.isChecked()){
            buffer.append("wednesday ");
        }
        if (cb_thursday.isChecked()){
            buffer.append("thursday ");
        }
        if (cb_friday.isChecked()){
            buffer.append("friday ");
        }
        if (cb_saturday.isChecked()){
            buffer.append("saturday ");
        }

        return buffer.toString().trim();
    }
}
