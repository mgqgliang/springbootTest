package com.example.springboottest.util;

import java.text.SimpleDateFormat;
import java.time.temporal.Temporal;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    private static final DateUtils utils = new DateUtils();


    public static String dataToStringByYear(Date d){
        return utils.format(d,4);
    }

    public static String dataToStringByMonth(Date d){

        return utils.format(d,7);
    }
    public static String dataToStringByDay(Date d){

        return utils.format(d,10);
    }
    public static String dataToStr(Date d){

        return utils.format(d,10);
    }
    public static String dataToStringByHour(Date d){

        return utils.format(d,13);
    }
    public static String dataToStringByMinute(Date d){

        return utils.format(d,16);
    }
    public static String dataToStringBySecond(Date d){

        return utils.format(d,19);
    }

    public static String dataToStringByMonthAndDay(Date d){
        return utils.format(d,5);
    }

    private String format(Date d , int i){
        if(d==null)return"";
        SimpleDateFormat sdf = getFormat(i);
        return sdf.format(d);
    }

    private SimpleDateFormat getFormat(int i) {
        SimpleDateFormat s = null;
        switch (i) {
            case 4:
                s = new SimpleDateFormat("yyyy");
                break;
            case 7:
                s = new SimpleDateFormat("yyyy-MM");
                break;
            case 10:
                s = new SimpleDateFormat("yyyy-MM-dd");
                break;
            case 13:
                s = new SimpleDateFormat("yyyy-MM-dd HH");
                break;
            case 16:
                s = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                break;
            case 19:
                s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                break;
            default:
                s = new SimpleDateFormat("yyyy-MM-dd");
                break;
        }
        return s;
    }

    public static long getCurrentMilliSecond(){
        Calendar c = Calendar.getInstance();
        return c.getTimeInMillis();
    }

    public static Long getBetweenDays(Temporal begin, Temporal end) {
        return Math.abs(ChronoUnit.DAYS.between(begin, end));
    }

    public static Long getBetweenMonths(Temporal begin, Temporal end) {
        return Math.abs(ChronoUnit.MONTHS.between(begin, end));
    }
    public static Long getBetweenWeeks(Temporal begin, Temporal end) {
        if (end.getLong(ChronoField.EPOCH_DAY) - begin.getLong(ChronoField.EPOCH_DAY) < 0) {
            Temporal temp = begin;
            begin = end;
            end = temp;
        }
        int beginWeekDay = begin.get(ChronoField.DAY_OF_WEEK);
        long daysBetween = getBetweenDays(begin, end);
        long weeksBetween = daysBetween / 7;
        int offset = (daysBetween % 7 + beginWeekDay) > 7 ? 1 : 0;
        return offset + weeksBetween;
    }
}
