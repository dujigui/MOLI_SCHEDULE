package com.pheynix.moli_schedule.Util;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by pheynix on 7/17/15.
 */
public class CalendarUtil {

    //Calendar.getTimeInMillis得到的时间戳与时区无关，全球一致
    //Calendar.getInstance(TimeZone.getTimeZone("Etc/Greenwich"))获得格林威治标准时间GMT+0;
    //Calendar.getInstance()获得格林威治标准时间并转换成当前时区，例如香港的GMT+8;

    //涉及到持续时间(时间长度)的使用GMT+0，方便运算
    //涉及到开始时间(时间点)的使用当前时区真实时间，方便向用户呈现

    //获取当前时区Calendar
    public static Calendar getEmptyCalendarCurrentTimeZone(){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        return calendar;
    }

    //获取GMT+0的Calendar
    public static Calendar getEmptyCalendarGreenwich(){
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Etc/Greenwich"));
        calendar.clear();
        return calendar;
    }


}


