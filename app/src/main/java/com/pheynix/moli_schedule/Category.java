package com.pheynix.moli_schedule;

/**
 * 日程类的数据封装
 * Created by pheynix on 6/30/15.
 */
public class Category {

    private String category_name;
    private String time_target;
    private String time_summary;
    private String periodicity;
    private String time_cycle;

    public Category() {
    }

    public Category(String category_name, String time_target, String time_summary, String periodicity, String time_cycle) {
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

    public String getTime_target() {
        return time_target;
    }

    public void setTime_target(String time_target) {
        this.time_target = time_target;
    }

    public String getTime_summary() {
        return time_summary;
    }

    public void setTime_summary(String time_summary) {
        this.time_summary = time_summary;
    }

    public String getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(String periodicity) {
        this.periodicity = periodicity;
    }

    public String getTime_cycle() {
        return time_cycle;
    }

    public void setTime_cycle(String time_cycle) {
        this.time_cycle = time_cycle;
    }
}
