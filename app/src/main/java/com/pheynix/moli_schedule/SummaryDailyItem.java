package com.pheynix.moli_schedule;

/**
 * Created by pheynix on 7/10/15.
 */
public class SummaryDailyItem {
    private String name;
    private float value;

    public SummaryDailyItem(String name, int value) {
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
