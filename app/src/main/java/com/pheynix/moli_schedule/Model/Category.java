package com.pheynix.moli_schedule.Model;

/**
 * 日程类的数据封装
 * Created by pheynix on 6/30/15.
 */
public class Category {

    private String category_name;
    private long time_target;
    private long time_summary;
    private long time_cycle;
    private String periodicity;

    public Category() {
    }

    public Category(String category_name, long time_target, long time_summary, String periodicity, long time_cycle) {
        this.category_name = category_name;
        this.time_target = time_target;
        this.time_summary = time_summary;
        this.periodicity = periodicity;
        this.time_cycle = time_cycle;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public long getTime_target() {
        return time_target;
    }

    public void setTime_target(long time_target) {
        this.time_target = time_target;
    }

    public long getTime_summary() {
        return time_summary;
    }

    public void setTime_summary(long time_summary) {
        this.time_summary = time_summary;
    }

    public String getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(String periodicity) {
        this.periodicity = periodicity;
    }

    public long getTime_cycle() {
        return time_cycle;
    }

    public void setTime_cycle(long time_cycle) {
        this.time_cycle = time_cycle;
    }
}
