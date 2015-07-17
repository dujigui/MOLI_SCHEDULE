package com.pheynix.moli_schedule.Util;

import java.util.Calendar;

/**
 * Created by pheynix on 7/10/15.
 */
public class DateTimeUtil {

    public static String longToDate(long millis){
        Calendar calendar = CalendarUtil.getEmptyCalendarCurrentTimeZone();
        calendar.setTimeInMillis(millis);

        StringBuffer buffer = new StringBuffer();
//        buffer.append(calendar.get(Calendar.YEAR) + R.string.year);
//        buffer.append(calendar.get(Calendar.MONTH) + R.string.month);
//        buffer.append(calendar.get(Calendar.DAY_OF_MONTH) + R.string.day);
//        buffer.append(calendar.get(Calendar.HOUR_OF_DAY) + R.string.hour);
//        buffer.append(calendar.get(Calendar.MINUTE) + R.string.minute);
        buffer.append(calendar.get(Calendar.YEAR) + "年");
        buffer.append((calendar.get(Calendar.MONTH)+1) + "月");//Calendar中的月份是0基的....
        buffer.append(calendar.get(Calendar.DAY_OF_MONTH) + "日");
        buffer.append(calendar.get(Calendar.HOUR_OF_DAY) + "时");
        buffer.append(calendar.get(Calendar.MINUTE) + "分");




        return buffer.toString();
    }

    public static String longToTime(long millis){
        Calendar calendar = CalendarUtil.getEmptyCalendarGreenwich();
        calendar.setTimeInMillis(millis);

        StringBuffer buffer = new StringBuffer();
//        buffer.append(calendar.get(Calendar.HOUR_OF_DAY) + R.string.hour);
//        buffer.append(calendar.get(Calendar.MINUTE) + R.string.minute);
        buffer.append(calendar.get(Calendar.HOUR_OF_DAY) + "时");
        buffer.append(calendar.get(Calendar.MINUTE) + "分");

        return buffer.toString();
    }


}
