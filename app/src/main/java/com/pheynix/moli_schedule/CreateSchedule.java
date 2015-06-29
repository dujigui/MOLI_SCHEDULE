package com.pheynix.moli_schedule;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;


public class CreateSchedule extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener,DatePickerDialog.OnDateSetListener{
    private Toolbar mToolbar;
    private TextView tv_time_start,tv_time_last;
    private String date;
    private String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_schedule);

        mToolbar = (Toolbar) findViewById(R.id.id_toolbar_create_schedule);
        tv_time_start = (TextView) findViewById(R.id.id_time_start);
        tv_time_last = (TextView) findViewById(R.id.id_time_last);
        tv_time_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        tv_time_last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void showDatePickerDialog(){
        Calendar now = Calendar.getInstance();
        DatePickerDialog dialog = DatePickerDialog.newInstance(this,now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH));
        dialog.show(getFragmentManager(),"data_picker_dialog");
    }

    private void showTimePickerDialog(){
        Calendar now = Calendar.getInstance();
//        DatePickerDialog dialog = DatePickerDialog.newInstance(this,now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH));
//        dialog.show(getFragmentManager(),"data_picker_dialog");
        TimePickerDialog dialog = TimePickerDialog.newInstance(this,now.get(Calendar.HOUR_OF_DAY),now.get(Calendar.MINUTE),true);
        dialog.show(getFragmentManager(),"tiem_picker_dialog");
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int i, int i1, int i2) {
//        StringBuffer buffer = new StringBuffer();
//        buffer.append(i+"年"+i1+"月"+i2+"日");
        date = i+"年"+i1+"月"+i2+"日";
        showTimePickerDialog();
        new TestMessage(CreateSchedule.this,datePickerDialog.getTag());
    }

    @Override
    public void onTimeSet(RadialPickerLayout radialPickerLayout, int i, int i1) {
        time = i+"时"+i1+"分";
        tv_time_start.setText(date+time);
    }
}
