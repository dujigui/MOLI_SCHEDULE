package com.pheynix.moli_schedule.Item;

import java.io.Serializable;

/**
 * 日程数据封装
 * Created by pheynix on 6/24/15.
 */
public class Schedule implements Serializable {

    //日程数据
    //sqlite可保存null,INTEGER,REAL,TEXT,BLOB
    //BLOB二进制大型文件对象

    private int id;//即数据库的PRIMARY KEY,写入数据库时由数据库自动生成，读出数据库时为"_id"，操作数据时用于定位；

    private String category;//日程属于什么类别
    private String detail;//日程具体内容
    private long time_start;//使用UNIX时间，秒为单位（非毫秒）
    private long time_last;//分钟
    private long time_recorded;
    private int urgency;//0==空 1==紧急重要 2==不紧急重要 3==紧急不重要 4==不紧急不重要
    private boolean vibration;//是否震动提示
    private int volume;//提示声音的大小1~100
    private int status;//完成状态123“未完成”“推迟”“已完成”

    public Schedule() {
    }

    public Schedule(int id, String category, String detail, long time_start, long time_last, long time_recorded, int urgency, boolean vibration, int volume, int status) {
        this.id = id;
        this.category = category;
        this.detail = detail;
        this.time_start = time_start;
        this.time_last = time_last;
        this.time_recorded = time_recorded;
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

    public long getTime_start() {
        return time_start;
    }

    public void setTime_start(long time_start) {
        this.time_start = time_start;
    }

    public long getTime_last() {
        return time_last;
    }

    public void setTime_last(long time_last) {
        this.time_last = time_last;
    }

    public long getTime_recorded() {
        return time_recorded;
    }

    public void setTime_recorded(long time_recorded) {
        this.time_recorded = time_recorded;
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
