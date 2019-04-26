package com.emerging.framework.core.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.emerging.framework.core.exception.CommonException;



public class DateTimeUtils extends Utils {

    private static final int MON_JAN_1 = 1;
    private static final int NONTH_NOV_11 = 11;
    public static final String TIME_SEPARATOR_CHARS = ":";
    public static final String DATE_SEPARATOR_CHARS = "-";
    public static final int DAY_OF_MONTH = 30;
    public static final int MINUTE_OF_SECONDS = 60;
    public static final int HOUR_OF_SECONDS = 60 * 60;
    public static final int DAY_OF_MILLISECOND = 24 * 60 * 60 * 1000;
    public static final String FORMAT_YYYYMMDD = "yyyy-mm-dd";
    public static final int TEN = 10;
    public static final String PLACE_HOLDER = "0";

    // "private" to "public", for System using (Space, 2008-09-25)
    public static final String[] PATTERNS = new String[] {
            "MMM d, yyyy h:mm a", "MM/dd/yyyy", "yyyy-MM-dd", "MMM d, yyyy, haaa", "yyyy-MM-dd HH:mm:ss",
            "yyyy-MM-dd HH:mm:ss.SSS", "yyyy-MM-dd@@HH:mm:ssZ", "yyyy.MM.dd "
    };

    /**
     * 获取当日星期
     * 
     * @param dt
     * @return
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {
                "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"
        };
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;

        return weekDays[w];
    }

    // compare two date, return the difference days
    public static final int compare2Date(final Date date1, final Date date2) {
        if (date1 == null || date2 == null) {
            return 0;
        }
        long time1 = date1.getTime();
        long time2 = date2.getTime();

        int days = (int) (long) ((time2 - time1) / DAY_OF_MILLISECOND);
        return days;

    }

    // month add 1.
    public static final Date getDateAfterOneMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        return calendar.getTime();

    }
    // month add 1.
    public static final Date getDateAddMin(int time) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, time);
        return calendar.getTime();

    }
    
    // month add 1.
    public static final Date getDateAddMin(Date date ,int time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, time);
        return calendar.getTime();

    }
    
    

    // get the date 30 days after
    // Use this function to set expiration date for a new account
    // DO NOT USE getDateAfterOneMonth()
    public static final Date getDateAfter30Days() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, DAY_OF_MONTH);
        return calendar.getTime();

    }

    /**
     * 
     * @param strDateTime
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static final boolean checkoutDateTime(final String strDateTime, final String pattern) throws ParseException {
        final Date nowDateTime = new Date();
        final DateFormat df = new SimpleDateFormat(pattern, Locale.ENGLISH);
        final Date tempDateTime = df.parse(strDateTime);
        return nowDateTime.before(tempDateTime);
    }

    /**
     * @param pattern
     *            "yyyy.MM.dd G 'at' HH:mm:ss z" 2001.07.04 AD at 12:08:56 PDT
     *            "EEE, MMM d, ''yy" Wed, Jul 4, '01 "h:mm a" 12:08 PM
     *            "hh 'o''clock' a, zzzz" 12 o'clock PM, Pacific Daylight Time
     *            "K:mm a, z" 0:08 PM, PDT "yyyyy.MMMMM.dd GGG hh:mm aaa"
     *            02001.July.04 AD 12:08 PM "EEE, d MMM yyyy HH:mm:ss Z" Wed, 4
     *            Jul 2001 12:08:56 -0700 "yyMMddHHmmssZ" 010704120856-0700
     *            "yyyy-MM-dd'T'HH:mm:ss.SSSZ" 2001-07-04T12:08:56.235-0700
     *            "EEE MMM d HH:mm:ss z yyyy" Wed Sep 24 17:57:45 GMT+08:00 2008
     * @return
     * @author Jim.Bi Sep 24, 2008 3:41:53 PM
     */
    public static final String formatDate(final Date date, String pattern) {
        if (StringUtils.isBlank(pattern)) {
            pattern = PATTERNS[1];
        }
        if (null == date) {
            return null;
        }

        final SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.ENGLISH);
        final String c = sdf.format(date);
        return c;
    }
    
    
    public static final Date stringToDate(String str,String pattern) throws CommonException, Exception{
    	   final SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    	 return  sdf.parse(str);
    }
    

    /**
     * Convert date to string with format
     * @Title: date2String
     * @param date
     * @param format
     * @return String
     */
    public static String date2String(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
    /**
     * 
     * @param date
     * @param pattern
     * @param locale
     * @return
     */
    public static final String formatDate(final Date date, final String pattern, final Locale locale) {
        try {
            final DateFormat df = new SimpleDateFormat("MMM,d yyyy hh:mm:ss", locale);
            final Date te = new Date();
            return df.format(te);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static final Date parse(final String str) {

        if (StringUtils.isBlank(str)) {
            return null;
        }
        try {
            return DateUtils.parseDate(str, PATTERNS);
        } catch (final ParseException e) {
            return null;
        }
    }

    /**
     * String to Date by format
     * 
     * @author Space 2008-09-25
     * @param strDate
     * @param pattern
     *            "yyyy.MM.dd G 'at' HH:mm:ss z" 2001.07.04 AD at 12:08:56 PDT
     *            "EEE, MMM d, ''yy" Wed, Jul 4, '01 "h:mm a" 12:08 PM
     *            "hh 'o''clock' a, zzzz" 12 o'clock PM, Pacific Daylight Time
     *            "K:mm a, z" 0:08 PM, PDT "yyyyy.MMMMM.dd GGG hh:mm aaa"
     *            02001.July.04 AD 12:08 PM "EEE, d MMM yyyy HH:mm:ss Z" Wed, 4
     *            Jul 2001 12:08:56 -0700 "yyMMddHHmmssZ" 010704120856-0700
     *            "yyyy-MM-dd'T'HH:mm:ss.SSSZ" 2001-07-04T12:08:56.235-0700
     * @return
     */
    public static final Date strToDateLong(final String strDate, final String pattern) {
        try {
            final SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.ENGLISH);
            final ParsePosition pos = new ParsePosition(0);
            final Date strtodate = formatter.parse(strDate, pos);
            return strtodate;
        } catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static final long toNumberButNotYear() {
        return NumberUtils.toLong(new SimpleDateFormat("MMddHHmmss").format(new Date()), 0L);
    }

    /**
     * convert "1:09:09" to 4149
     * 
     * @param time
     * @return int
     */
    public static final int toSecond(final String time) {

        int result = -1;

        final String[] strs = StringUtils.split(time, TIME_SEPARATOR_CHARS);

        if (strs != null && strs.length == 3) {
            final int hour = NumberUtils.toInt(strs[0], 0);
            final int minute = NumberUtils.toInt(strs[1], 0);
            final int second = NumberUtils.toInt(strs[2], 0);

            result += second + 1;

            result += minute * MINUTE_OF_SECONDS;

            result += hour * HOUR_OF_SECONDS;
        }

        return result;
    }

    /**
     * Get the first date of quarter that the given month belong to.
     * 
     * @param month
     * @return
     */
    public static String getQuarterFirstDay(int month) {

        String quarter = "";

        if (month >= Calendar.JANUARY && month <= Calendar.APRIL) {
            quarter = "Jan 01,";
        } else if (month >= Calendar.MAY && month <= Calendar.JULY) {
            quarter = "Apr 01,";
        } else if (month >= Calendar.AUGUST && month <= Calendar.OCTOBER) {
            quarter = "Jul 01,";
        } else if (month >= Calendar.NOVEMBER && month <= Calendar.DECEMBER) {
            quarter = "Oct 01,";
        }
        return quarter;
    }

    public static int getCurrentYear() {
        Calendar cD = Calendar.getInstance();
        int yY = cD.get(Calendar.YEAR);
        return yY;
    }

    public static int getCurrentMonth() {
        Calendar cD = Calendar.getInstance();
        int mm = cD.get(Calendar.MONTH);
        return mm;
    }

    public static int getCurrentDay() {
        Calendar cD = Calendar.getInstance();
        int dd = cD.get(Calendar.DAY_OF_MONTH);
        return dd;
    }

    public static String getServerTimeZone() {
        SimpleDateFormat sdf = new SimpleDateFormat("zzzz");
        Calendar cD = Calendar.getInstance();
        return sdf.format(cD.getTime());
    }

    public static String getServerTimeZoneGMT() {
        SimpleDateFormat sdf = new SimpleDateFormat("Z");
        Calendar cD = Calendar.getInstance();
        return sdf.format(cD.getTime());
    }

    public static int getWeekFromDate(Date date) {
        Calendar cD = Calendar.getInstance();
        cD.setTime(date);
        cD.setFirstDayOfWeek(Calendar.SUNDAY);
        int week = cD.get(Calendar.WEEK_OF_YEAR);
        return week;
    }

    public static int getMonthFromDate(Date date) {
        Calendar cD = Calendar.getInstance();
        cD.setTime(date);
        int month = cD.get(Calendar.MONTH);
        return month + 1;
    }

    public static int getYearFromDate(Date date) {
        Calendar cD = Calendar.getInstance();
        cD.setTime(date);
        int year = cD.get(Calendar.YEAR);
        return year;
    }

    /**
     * Get the first day of current month.
     * 
     * @return
     */
    public static Date getMonthFirstDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    /**
     * Get the last day of current month.
     * 
     * @return
     */
    public static Date getMonthLastDay() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int value = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, value);
        return cal.getTime();
    }

    public static Date getPreviousMonthDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        return calendar.getTime();
    }

    public static Date getPreviousYearDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -1);
        return calendar.getTime();
    }

    public static Date getPreviousDailyDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }

    public static Date getPreviousDailyWeekDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -7);
        return calendar.getTime();
    }

    public static Date getPreviousDay(int count) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -count);
        return calendar.getTime();
    }

    public static Date getCurrentDailyDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 0);
        return calendar.getTime();
    }

    public static List<Date> getDateList(Date startDate, Date endDate) {
        ArrayList<Date> dateList = new ArrayList<Date>();
        try {
            dateList.add(startDate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(startDate);
            cal.add(Calendar.DAY_OF_YEAR, +1);
            Date temp = cal.getTime();
            while (temp.getTime() < endDate.getTime()) {
                dateList.add(temp);
                cal.add(Calendar.DAY_OF_YEAR, +1);
                temp = cal.getTime();
            }
            dateList.add(endDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateList;
    }

    public static Date getDate(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, days);
        return cal.getTime();
    }

    public static List<String> getWeekList(List<Date> datelist) {
        ArrayList<String> weekList = new ArrayList<String>();
        for (Date date : datelist) {
            String week = "";
            if (getMonthFromDate(date) > NONTH_NOV_11 && getWeekFromDate(date) == MON_JAN_1) {
                week = getYearFromDate(date) + "-53";
            } else {
                week = getYearFromDate(date) + DATE_SEPARATOR_CHARS + getWeekFromDate(date);
            }
            if (weekList.contains(week)) {
                continue;
            }
            weekList.add(week);
        }
        return weekList;
    }

    public static List<String> getMonthList(List<Date> datelist) {
        ArrayList<String> monthList = new ArrayList<String>();
        for (Date date : datelist) {
            if (monthList.contains(getYearFromDate(date) + DATE_SEPARATOR_CHARS + getMonthFromDate(date))) {
                continue;
            }
            monthList.add(getYearFromDate(date) + DATE_SEPARATOR_CHARS + getMonthFromDate(date));
        }
        return monthList;
    }

    /**
     * format XX second into hh:mm:ss style
     * 
     * @param second
     * @return
     */
    public static String formatTime(final long second) {
        final StringBuffer result = new StringBuffer();
        final long hours = second / HOUR_OF_SECONDS;
        final long minutes = second % MINUTE_OF_SECONDS;
        final long seconds = second % MINUTE_OF_SECONDS % HOUR_OF_SECONDS;

        if (hours < TEN) {
            result.append(PLACE_HOLDER);
        }
        result.append(Long.toString(hours));

        result.append(TIME_SEPARATOR_CHARS);
        if (minutes < TEN) {
            result.append(PLACE_HOLDER);
        }
        result.append(Long.toString(minutes));
        result.append(TIME_SEPARATOR_CHARS);

        if (seconds < TEN) {
            result.append(PLACE_HOLDER);
        }
        result.append(Long.toString(seconds));
        return result.toString();

    }

    public static String formatTimeToW3C(final Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(PATTERNS[6]);
        String w3cDate = StringUtils.replace(sdf.format(date), "@@", "T");
        w3cDate = w3cDate.substring(0, w3cDate.length() - 2) + TIME_SEPARATOR_CHARS
                + w3cDate.substring(w3cDate.length() - 2);

        return w3cDate;

    }

    public static Calendar getHourDay() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR_OF_DAY, 1);
        return cal;
    }

    public static Boolean isValid(Date end, Date today, Date start) {
        if (today.after(end) || today.before(start)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断指定时间是否介于开始时间和结束时间
     * @return
     */
    public static boolean isBetweenStartAndEnd(Date toCheckDate,Date startDate,Date endDate){
        boolean result = false;
        if(toCheckDate.after(startDate) && toCheckDate.before(endDate)){
            result = true;
        }
        return  result;
    }

    /**
     * 获取当前时间一年前的时间
     * @return
     */
    public static final Date getDateBeforeOneYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -1);
        return calendar.getTime();
    }

public static void main(String[] args) {
	System.out.println(getCurrentYear());
}
}
