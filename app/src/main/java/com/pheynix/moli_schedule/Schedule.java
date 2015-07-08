package com.pheynix.moli_schedule;

import java.io.Serializable;

/**
 * 日程数据封装
 * Created by pheynix on 6/24/15.
 */
public class Schedule implements Serializable{

    //日程数据
    //sqlite可保存null,INTEGER,REAL,TEXT,BLOB
    //BLOB二进制大型文件对象

    private int id;//即数据库的PRIMARY KEY,写入数据库时由数据库自动生成，读出数据库时为"_id"，用于定位；

    private String category;//日程属于什么类别
    private String detail;//日程具体内容
    private String time_start;//2015 06 28 08 59,日期＋时间，取出来后分割
    private String time_last;//分钟
    private int urgency;//0==空 1==紧急重要 2==不紧急重要 3==紧急不重要 4==不紧急不重要
    private boolean vibration;//是否震动提示
    private int volume;//提示声音的大小1~100
    private int status;//完成状态123“未完成”“推迟”“已完成”

    public Schedule() {
    }

    public Schedule(int id, String category, String detail, String time_start, String time_last, int urgency, boolean vibration, int volume, int status) {
        this.id = id;
        this.category = category;
        this.detail = detail;
        this.time_start = time_start;
        this.time_last = time_last;
        this.urgency = urgency;
        this.vibration = vibration;
        this.volume = volume;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTime_start() {
        return time_start;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public String getTime_last() {
        return time_last;
    }

    public void setTime_last(String time_last) {
        this.time_last = time_last;
    }

    public int getUrgency() {
        return urgency;
    }

    public void setUrgency(int urgency) {
        this.urgency = urgency;
    }

    public boolean isVibration() {
        return vibration;
    }

    public void setVibration(boolean vibration) {
        this.vibration = vibration;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
