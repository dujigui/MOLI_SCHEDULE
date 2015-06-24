package com.pheynix.moli_schedule;

/**
 * Created by pheynix on 6/24/15.
 */
public class ScheduleInfo {
    private String detail;
    private int imgId;

    public ScheduleInfo(String detail, int imgId) {
        this.detail = detail;
        this.imgId = imgId;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }
}
