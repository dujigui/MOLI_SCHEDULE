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
        buffer.append(calendar.get(Calendar.HOUR_OF_DAY) + "时");
        buffer.append(calendar.get(Calendar.MINUTE) + "分");

        return buffer.toString();
    }

    public static String longToWord(long millis){
        Calendar calendar = CalendarUtil.getEmptyCalendarCurrentTimeZone();
        calendar.setTimeInMillis(millis);
//        Calendar today = Calendar.getInstance();
        int timeStart = calendar.get(Calendar.DAY_OF_MONTH);
        int today = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        String result = "";

        switch (timeStart - today){
            case -2:
                result = "前天";
                break;
            case -1:
                result = "昨天";
                break;
            case 0:
                result = "今天";
                break;
            case 1:
                result = "明天";
                break;
            case 2:
                result = "后天";
                break;
        }
        return result+calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE);
    }


}
