package com.pheynix.moli_schedule;

/**
 * 日程数据封装
 * Created by pheynix on 6/24/15.
 */
public class Schedule {

    //日程数据
    //sqlite可保存null,INTEGER,REAL,TEXT,BLOB
    //BLOB二进制大型文件对象


    private String category;//日程属于什么类别
    private String detail;//日程具体内容
    private int time_start;//201506280859,日期＋时间，取出来后分割
    private int time_last;//分钟
    private int urgency;//0==空 1==紧急重要 2==不紧急重要 3==紧急不重要 4==不紧急不重要
    private boolean vibration;//是否震动提示
    private int volume;//提示声音的大小

    public Schedule(String category, String detail, int time_start, int time_last, int urgency, boolean vibration, int volume) {
        this.category = category;
        this.detail = detail;
        this.time_start = time_start;
        this.time_last = time_last;
        this.urgency = urgency;
        this.vibration = vibration;
        this.volume = volume;
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

    public void setTime_start(int time_start) {
        this.time_start = time_start;
    }

    public int getTime_last() {
        return time_last;
    }

    public void setTime_last(int time_last) {
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
}
