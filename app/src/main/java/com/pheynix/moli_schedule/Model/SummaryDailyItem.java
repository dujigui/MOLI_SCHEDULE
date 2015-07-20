package com.pheynix.moli_schedule.Model;

/**
 * Created by pheynix on 7/10/15.
 */
public class SummaryDailyItem {
    //名称和百分比
    private String name;
    private float value;

    public SummaryDailyItem(String name, float value) {
        this.name = name;
        this.value = value;
    }

    public SummaryDailyItem() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
