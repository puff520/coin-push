package com.study.zeus.utils;


import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * $DESCRIPTION$
 *
 * @author : zt
 * @version :
 * @date : Created in 上午1:03$
 */
public class DateUtils {

    private static String EMPTY = "";
    private static String datePattern = "yyyy-MM-dd";
    private static String datePattern2 = "yyyy/MM/dd";
    private static String datePattern1 = "yyyy-MM";
    private static String dateTimePattern = "yyyy-MM-dd HH:mm:ss";
    public static String datePatternYMD = "yyyyMMdd";
    public static String datePatternY_M_D = "yyyy-MM-dd";
    public static String patternYMD = "yyyy-MM-dd";
    public static String patternHMS = "HH:mm:ss";

    private static SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);
    private static SimpleDateFormat dateFormat1 = new SimpleDateFormat(datePattern1);
    private static SimpleDateFormat dateFormat2 = new SimpleDateFormat(datePattern2);
    private static SimpleDateFormat dateTimeFormat = new SimpleDateFormat(dateTimePattern);

    private static SimpleDateFormat timeFormatter = new SimpleDateFormat(patternHMS);
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(datePattern);
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimePattern);


    /**
     * 日期格式，年月日，用横杠分开，例如：06-12-25，08-08-08
     */
    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

    public static final String DATE_FORMAT_YYYY_MM_CN = "YYYY年MM月";

    private final static ZoneId ZONE_ID = ZoneId.of("Asia/Shanghai");

    public final static String FORMAT_TIME_FOR_ROW_KEY = "yyyyMMddHHmmss";

    public static final String DATE_FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_YYYY_MM_DD_HH_MM_SS2 = "yyyy/MM/dd HH:mm:ss";
    public static final String DATE_FORMAT_YYYY_MM_DD2 = "yyyy/MM/dd";

    /**
     * 默认的日期时间格式工具
     */
    private static final ThreadLocal<SimpleDateFormat> DEFAULT_DATE_FORMAT = ThreadLocal.withInitial(() -> new SimpleDateFormat(dateTimePattern));

    /**
     * 默认年月日时间格式工具
     */
    private static final ThreadLocal<SimpleDateFormat> DEFAULT_YTD_DATE_FORMAT = ThreadLocal.withInitial(() -> new SimpleDateFormat(datePattern));


    public static final ThreadLocal<SimpleDateFormat> dateFormatBrief = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(FORMAT_TIME_FOR_ROW_KEY);
        }
    };

    /**
     * String 转 Date
     * 格式: yyyy-MM-dd HH:mm:ss
     *
     * @param strDate
     * @return
     */
    public static Date stringToDateTime(String strDate) {
        if (strDate == null) {
            return null;
        }
        ParsePosition pos = new ParsePosition(0);
        return dateTimeFormat.parse(strDate, pos);
    }

    /**
     * String 转 Date
     * 格式: yyyy-MM-dd
     *
     * @param strDate
     * @return
     */
    public static Date stringToDate(String strDate) {
        if (strDate == null) {
            return null;
        }
        ParsePosition pos = new ParsePosition(0);
        return dateFormat.parse(strDate, pos);
    }

    /**
     * Date 转 String
     * 格式: yyyy-MM-dd HH:mm:ss
     *
     * @param dateDate
     * @return
     */
    public static String dateTimeToString(Date dateDate) {
        if (dateDate == null) {
            return EMPTY;
        }
        return dateTimeFormat.format(dateDate);
    }

    public static String timeToString(Date dateDate) {
        if (dateDate == null) {
            return EMPTY;
        }
        return timeFormatter.format(dateDate);
    }


    /**
     * Date 转 String
     * 格式: yyyy/MM/dd
     *
     * @param dateDate
     * @param
     * @return
     */
    public static String dateToStringFormat(Date dateDate) {
        if (dateDate == null) {
            return EMPTY;
        }

        return dateFormat2.format(dateDate);
    }

    /**
     * Date 转 String
     * 格式: yyyy-MM
     *
     * @param dateDate
     * @param
     * @return
     */
    public static String dateYearMonth(Date dateDate) {
        if (dateDate == null) {
            return EMPTY;
        }
        return dateFormat1.format(dateDate);
    }

    /**
     * 格式化String时间
     *
     * @param time       String类型时间
     * @param timeFromat String类型格式
     * @return 格式化后的Date日期
     */
    public static Date parseStrToDate(String time, String timeFromat) {
        if (time == null || time.equals("")) {
            return null;
        }

        Date date = null;
        try {
            DateFormat dateFormat = new SimpleDateFormat(timeFromat);
            date = dateFormat.parse(time);
        } catch (Exception e) {

        }
        return date;
    }

    /**
     * 格式化String时间为YYYY年MM月
     *
     * @param stringDate
     * @return
     */
    public static String StringToDateYearMonth(String stringDate) {
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.DATE_FORMAT_YYYY_MM_CN);
        Calendar cal = Calendar.getInstance();
        cal.setTime(DateUtils.parseStrToDate(stringDate, datePattern1));
        return sdf.format(cal.getTime());
    }

    /**
     * LocalDate 转 String
     * 格式: yyyy-MM-dd
     *
     * @param dateDate
     * @return
     */
    public static String dateToString(LocalDate dateDate) {
        if (dateDate == null) {
            return EMPTY;
        }
        return dateFormatter.format(dateDate);
    }

    /**
     * Date 转 String
     * 格式: yyyy-MM-dd
     *
     * @param dateDate
     * @return
     */
    public static String dateToString(Date dateDate) {
        if (dateDate == null) {
            return EMPTY;
        }

        return dateFormat.format(dateDate);
    }

    /**
     * LocalDateTime 转 String
     * 格式: yyyy-MM-dd HH:mm:ss
     *
     * @param dateDate
     * @return
     */
    public static String dateTimeToString(LocalDateTime dateDate) {
        if (dateDate == null) {
            return EMPTY;
        }
        return dateTimeFormatter.format(dateDate);
    }

    /**
     * Date 转 LocalDate
     *
     * @param date
     * @return
     */
    public static LocalDate dateToLocalDate(Date date) {
        if (date == null) {
            return null;
        }

        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone).toLocalDate();
    }


    /**
     * 距离今天的时间差(天)
     *
     * @param before
     * @return
     */
    public static long getDays(LocalDate before) {
        if (before == null) {
            return 0;
        }

        LocalDate now = LocalDate.now();

        return now.toEpochDay() - before.toEpochDay();
    }

    /**
     * 距离今天的时间差(周),不足一周的舍去
     *
     * @param before
     * @return
     */
    public static long getWeeks(LocalDate before) {
        if (before == null) {
            return 0;
        }
        long days = getDays(before);
        return days / 7;
    }

    /**
     * 距离今天的时间差(周),不足一周的舍去
     *
     * @param before
     * @return
     */
    public static long getWeeks(Date before) {
        if (before == null) {
            return 0;
        }
        LocalDate localDate = dateToLocalDate(before);
        long days = getDays(localDate);
        return days / 7;
    }

    /**
     * 获取前月的第一天
     *
     * @return
     */
    public static Date getMonthFirstDay(Integer month) {
        //获取当前日期
        Calendar cal_1 = Calendar.getInstance();
        cal_1.add(Calendar.MONTH, month);
        //设置为1号,当前日期既为本月第一天
        cal_1.set(Calendar.DAY_OF_MONTH, 1);
        //将小时至0
        cal_1.set(Calendar.HOUR_OF_DAY, 0);
        //将分钟至0
        cal_1.set(Calendar.MINUTE, 0);
        //将秒至0
        cal_1.set(Calendar.SECOND, 0);
        //将毫秒至0
        cal_1.set(Calendar.MILLISECOND, 0);
        return cal_1.getTime();
    }

    /**
     * 获取前月的第一天 9:00
     *
     * @return
     */
    public static Date getMonthFirstDayNiHour(Integer month) {
        //获取当前日期
        Calendar cal_1 = Calendar.getInstance();
        cal_1.add(Calendar.MONTH, month);
        //设置为1号,当前日期既为本月第一天
        cal_1.set(Calendar.DAY_OF_MONTH, 1);
        //将小时至0
        cal_1.set(Calendar.HOUR_OF_DAY, 9);
        //将分钟至0
        cal_1.set(Calendar.MINUTE, 0);
        //将秒至0
        cal_1.set(Calendar.SECOND, 0);
        //将毫秒至0
        cal_1.set(Calendar.MILLISECOND, 0);
        return cal_1.getTime();
    }

    /**
     * 获取某月的最后一天
     */
    public static Date getLastDayOfMonth(int month) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, month);
        //将小时至0
        cal.set(Calendar.HOUR_OF_DAY, 23);
        //将分钟至0
        cal.set(Calendar.MINUTE, 59);
        //将秒至0
        cal.set(Calendar.SECOND, 59);
        //将毫秒至0
        cal.set(Calendar.MILLISECOND, 999);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        return cal.getTime();
    }

    public static long getMonths(Date before) {
        dateToString(before);
        int i = calDiffMonth(dateToString(before), dateToString(new Date()));
        return i;
    }


    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE);
    }

    /**
     * 返回日期的月份，1-12,即yyyy-MM-dd中的MM
     *
     * @param date
     * @return
     */
    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 返回日期的年,即yyyy-MM-dd中的yyyy
     *
     * @param date Date
     * @return int
     */
    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    public static int getDaysOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }


    public static int calDiffMonth(String startDate, String endDate) {
        int result = 0;
        try {
            SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd");
            Date start = sfd.parse(startDate);
            Date end = sfd.parse(endDate);
            int startYear = getYear(start);
            int startMonth = getMonth(start);
            int startDay = getDay(start);
            int endYear = getYear(end);
            int endMonth = getMonth(end);
            int endDay = getDay(end);
            // 1月17  大于 2月28
            if (startDay > endDay) {
                // 也满足一月
                if (endDay == getDaysOfMonth(getYear(new Date()), 2)) {
                    result = (endYear - startYear) * 12 + endMonth - startMonth;
                } else {
                    result = (endYear - startYear) * 12 + endMonth - startMonth - 1;
                }
            } else {
                result = (endYear - startYear) * 12 + endMonth - startMonth;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 列出两个日期中所有的年份
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return
     */
    public static List<Integer> yearsInTwoDate(Date startDate, Date endDate) {
        Calendar start = Calendar.getInstance();
        start.setTime(startDate);
        Calendar end = Calendar.getInstance();
        end.setTime(endDate);
        int startYear = start.get(Calendar.YEAR);
        int endYear = end.get(Calendar.YEAR);
        List<Integer> years = new ArrayList<>();
        for (int i = startYear; i <= endYear; i++) {
            years.add(i);
        }
        return years;
    }

    /**
     * 列出两个日期中每年的月数
     * 暂未考虑天的影响（开始日均按当月第一日，结束日均按当月最后一日处理）
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return
     */
    public static Map<Integer, Integer> monthDurationPerYear(Date startDate, Date endDate) {
        Calendar start = Calendar.getInstance();
        start.setTime(startDate);
        Calendar end = Calendar.getInstance();
        end.setTime(endDate);
        int startYear = start.get(Calendar.YEAR);
        int endYear = end.get(Calendar.YEAR);
        Map<Integer, Integer> duration = new LinkedHashMap<>();
        if (startYear == endYear) {
            duration.put(startYear, end.get(Calendar.MONTH) - start.get(Calendar.MONTH) + 1);
            return duration;
        }
        duration.put(startYear, 12 - start.get(Calendar.MONTH));
        for (int i = startYear + 1; i < endYear; i++) {
            duration.put(i, 12);
        }
        duration.put(endYear, end.get(Calendar.MONTH) + 1);
        return duration;
    }

    /**
     * 列出两个日期中经过的月数
     * 暂未考虑天的影响（开始日均按当月第一日，结束日均按当月最后一日处理）
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return
     */
    public static Integer monthDuration(Date startDate, Date endDate) {
        Map<Integer, Integer> monthDurationPerYear = DateUtils.monthDurationPerYear(startDate, endDate);
        return monthDurationPerYear.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.summingInt(Integer::intValue));
    }

    public static int longOfTwoDate(Date first, Date second) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(first);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(second);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        // 同一年
        if (year1 != year2) {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                // 闰年
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {
                    timeDistance += 366;
                } else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2 - day1);
        } else    //不同年
        {
            System.out.println("判断day2 - day1 : " + (day2 - day1));
            return day2 - day1;
        }
    }

    public static int longOfTwoDate(String start, String end) {
        Date first = DateUtils.stringToDate(start);
        Date second = DateUtils.stringToDate(end);
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(first);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(second);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        // 同一年
        if (year1 != year2) {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                // 闰年
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {
                    timeDistance += 366;
                } else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2 - day1);
        } else    //不同年
        {
            System.out.println("判断day2 - day1 : " + (day2 - day1));
            return day2 - day1;
        }
    }

    /**
     * 获取上个月足后一天
     */
    public static Date getLastMonthLastDay(Date date) {
        Calendar c = Calendar.getInstance();
        //设置为指定日期
        c.setTime(date);
        //指定日期月份减去一
        c.add(Calendar.MONTH, -1);
        //指定日期月份减去一后的 最大天数
        c.set(Calendar.DATE, c.getActualMaximum(Calendar.DATE));
        //获取最终的时间
        Date lastDateOfPrevMonth = c.getTime();
        return lastDateOfPrevMonth;
    }

    /**
     * 获取某个日期的最后一天
     */
    public static String getLastDay(String dateStr) {
        Date date = DateUtils.stringToDate(dateStr);
        int year = DateUtils.getYear(date);
        int month = DateUtils.getMonth(date);
        Calendar cal = Calendar.getInstance();
// 不加下面2行，就是取当前时间前一个月的第一天及最后一天
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date lastDate = cal.getTime();
        return DateUtils.dateToString(lastDate);
    }

    /**
     * 获取当前时间的前一天时间
     */
    public static String getTheDayBefore(String dateStr) {
        Calendar c = Calendar.getInstance();
        Date date = DateUtils.stringToDate(dateStr);
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 1);
        return dateToString(c.getTime());
    }

    /**
     * 获取当前时间的前一天时间
     */
    public static Date getSpecifiedDayBefore(Date date) {
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 1);
        return c.getTime();
    }

    /**
     * 获取当前时间的前一天时间
     */
    public static Date getSpecifiedDayStrBefore(String dateStr) {
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        Date date = DateUtils.stringToDate(dateStr);
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 1);
        return c.getTime();
    }

    /**
     * 获取当前时间的后一天一天时间
     */
    public static Date getSpecifiedDayNext(String dateStr) {
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = DateUtils.stringToDate(dateStr);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + 1);
        return c.getTime();
    }

    /**
     * 输入的日期和当前日期作比较 0-表示时间日期相同 1-表示当前日期>大于输入日期  -1-表示当前日期<输入日期
     *
     * @param inputTime
     * @return
     */
    public static int compareCurrentDate(String inputTime) {

        // 获取当前时间
        String currentTime = getCurrentTime();
        DateFormat df = new SimpleDateFormat(DateUtils.DATE_FORMAT_YYYY_MM_DD);
        try {
            Date inputDate = df.parse(inputTime);
            Date currentDate = df.parse(currentTime);
            return currentDate.compareTo(inputDate);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取当前时间 格式是:yyyy-MM-dd
     *
     * @return
     */
    public static String getCurrentTime() {
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.DATE_FORMAT_YYYY_MM_DD);
        String currentStr = sdf.format(dt);
        return currentStr;
    }

    /**
     * 输入的日期和当前日期作比较 0-表示时间日期相同 1-表示当前日期>大于输入日期  -1-表示当前日期<输入日期
     *
     * @param inputTime
     * @return
     */
    public static int compareCurrentDateTime(String inputTime) {

        // 获取当前时间
        String currentTime = getCurrentDateTime();
        DateFormat df = new SimpleDateFormat(DateUtils.FORMAT_TIME_FOR_ROW_KEY);
        try {
            Date inputDate = df.parse(inputTime);
            Date currentDate = df.parse(currentTime);
            return currentDate.compareTo(inputDate);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取当前时间 格式是:yyyy-MM-dd
     *
     * @return
     */
    public static String getCurrentDateTime() {
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.FORMAT_TIME_FOR_ROW_KEY);
        String currentStr = sdf.format(dt);
        return currentStr;
    }

    /**
     * yyyyMMdd
     *
     * @return
     */
    public static String getNowStrTime() {
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.datePatternYMD);
        String nowStr = sdf.format(dt);
        return nowStr;
    }

    /**
     * 向前用正整数 向后用负整数
     *
     * @param num
     * @return
     */
    public static Date getcalculateDate(int num) {
        SimpleDateFormat sf = new SimpleDateFormat(DateUtils.datePatternYMD);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, num);
        return c.getTime();
    }

    /**
     * int
     * 输入的日期作比较 0-表示时间日期相同 1-表示日期1>大于日期2  -1-表示日期1<日期2
     *
     * @param time1
     * @param time2
     * @return
     */
    public static int compare(String time1, String time2) {
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.DATE_FORMAT_YYYY_MM_DD);

        try {
            Date time1Date = sdf.parse(time1);
            Date time2Date = sdf.parse(time2);
            return time1Date.compareTo(time2Date);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取指定日期的下一天
     *
     * @return
     */
    public static String getInputTimeNextDay(String time) {
        DateFormat dft = new SimpleDateFormat(DateUtils.DATE_FORMAT_YYYY_MM_DD);
        String nextDay = "";
        try {
            Date temp = dft.parse(time);
            Calendar cld = Calendar.getInstance();
            cld.setTime(temp);
            cld.add(Calendar.DATE, 1);
            temp = cld.getTime();
            //获得下一天日期字符串
            nextDay = dft.format(temp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return nextDay;
    }

    /**
     * 获取两个日期的所有月份
     */
    public static List<String> getMonthBetween(String minDate, String maxDate) {
        ArrayList<String> result = new ArrayList<String>();
        // 格式化为年月
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();

        try {
            min.setTime(sdf.parse(minDate));
            min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

            max.setTime(sdf.parse(maxDate));
            max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar curr = min;
        while (curr.before(max)) {
            result.add(sdf.format(curr.getTime()));
            curr.add(Calendar.MONTH, 1);
        }
        min = null;
        max = null;
        curr = null;
        return result;
    }

    /**
     * 将日期字符串转换成日期
     *
     * @param dateStr 日期字符串
     * @return
     * @throws ParseException
     */
    public static Date toYMDDate(String dateStr) throws ParseException {
        return DEFAULT_YTD_DATE_FORMAT.get().parse(dateStr);
    }

    /**
     * 将日期字符串转换成日期
     *
     * @param dateStr 日期字符串
     * @return
     * @throws ParseException
     */
    public static String toStartDate(String dateStr) throws ParseException {
        Date dateTime = DateUtils.toYMDDate(dateStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return DEFAULT_DATE_FORMAT.get().format(calendar.getTime());
    }

    /**
     * 将日期字符串转换成日期
     *
     * @param dateStr 日期字符串
     * @return
     * @throws ParseException
     */
    public static String toEndDate(String dateStr) throws ParseException {
        Date dateTime = DateUtils.toYMDDate(dateStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return DEFAULT_DATE_FORMAT.get().format(calendar.getTime());
    }

    /**
     * 获取n天的零点
     *
     * @return
     */
    public static String getBeforeDayZeroTime(int n) {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.add(Calendar.DAY_OF_YEAR, n);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return DEFAULT_DATE_FORMAT.get().format(calendar.getTime());
    }

    /**
     * 获取n天的6点
     *
     * @return
     */
    public static String getBeforeDaySixTime(int n) {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.add(Calendar.DAY_OF_YEAR, n);
        calendar.set(Calendar.HOUR_OF_DAY, 6);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return DEFAULT_DATE_FORMAT.get().format(calendar.getTime());
    }

    /**
     * 计算日期相差天数的绝对值
     *
     * @param start 较小的日期
     * @param end   较大的日期
     * @return
     */
    public static Integer diffDays(Date start, Date end) {
        LocalDate startDate = LocalDateTime.ofInstant(start.toInstant(), ZONE_ID).toLocalDate();
        LocalDate endDate = LocalDateTime.ofInstant(end.toInstant(), ZONE_ID).toLocalDate();
        if (Objects.isNull(startDate) || Objects.isNull(endDate)) {
            return null;
        }
        Long diffDay = endDate.toEpochDay() - startDate.toEpochDay();
        return Math.abs(diffDay.intValue());
    }

    /**
     * start <= date <= end true
     * else false
     *
     * @param date      比较日期
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return the boolean
     */
    public static boolean isBetween(Date date, Date startDate, Date endDate) {
        return org.apache.commons.lang3.time.DateUtils.truncatedCompareTo(date, startDate, Calendar.DATE) >= 0 && org.apache.commons.lang3.time.DateUtils.truncatedCompareTo(date, endDate, Calendar.DATE) <= 0;
    }

    public static boolean isValiDate(String strDate, String dateFormat) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        try {
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(strDate);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) throws ParseException {
       /* String minDate = "2018-01";
        String maxDate = "2018-06";
        List<String> list = getMonthBetween(minDate, maxDate);
        for (String s : list) {
            System.out.println(s);
        }*/
        System.out.println(DateUtils.getBeforeDaySixTime(-90));
        System.out.println(DateUtils.getBeforeDaySixTime(0));

        BigDecimal num1 = new BigDecimal("13.88");
        BigDecimal num2 = new BigDecimal("0.6678");
        BigDecimal num3 = new BigDecimal("-1000000");
        System.out.println(num2.compareTo(BigDecimal.ZERO));
//        System.out.println(num1.subtract(num2).divide(num1,6,BigDecimal.ROUND_HALF_UP));
    }

    /**
     * 转换Excel日期为指定格式日期字符串
     *
     * @param value  需要转换的值
     * @param format 指定日期格式 yyyy-MM-dd HH:mm:ss
     * @return 转换后的日期
     */
    public static String conversionDoubleToDateStr(double value, String format) {
        boolean use1904windowing = false;
        int wholeDays = (int) Math.floor(value);
        int millisecondsInDay = (int) ((value - (double) wholeDays) * 8.64E7D + 0.5D);

        Calendar calendar = new GregorianCalendar();

        short startYear = 1900;
        byte dayAdjust = -1;

        if (use1904windowing) {
            startYear = 1904;
            dayAdjust = 1;
        } else if (wholeDays < 61) {
            dayAdjust = 0;
        }
        calendar.set(startYear, 0, wholeDays + dayAdjust, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, millisecondsInDay);

        if (calendar.get(Calendar.MILLISECOND) == 0) {
            calendar.clear(Calendar.MILLISECOND);
        }

        Date date = calendar.getTime();
        SimpleDateFormat s = new SimpleDateFormat(format);
        System.out.println(s.format(date));
        return s.format(date);
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * yyyy-MM-dd 或者 yyyy-M-dd
     **/
    private static String DATE_REGEX = "^([1-9]\\d{3}-)(([0]{0,1}[1-9]-)|([1][0-2]-))(([0-3]{0,1}[0-9]))$";

    /***
     * @desc 校验日期的格式，yyyy-MM-dd，无法校验dd的完整性，
     * 就是可能出现 2020-2-32，2020-1-33这样的天数，可以通过设置日期的严禁性来转成日期，若报错则日期不正确
     * @param datestr：日期，格式：yyyy-MM-dd
     * @return boolean
     */
    public static boolean validDateEffecitive(String datestr) {
        boolean matches = false;
        try {
            matches = Pattern.matches(DATE_REGEX, datestr);
            if (!matches) {
                return matches;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //设置日期格式转的严谨性
            sdf.setLenient(false);
            sdf.parse(datestr);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return matches;
    }


    /**
     * 格式化当前时间
     */
    public static String formatCurrentDate(String format) {
        return formatDate(Calendar.getInstance().getTime(), format);
    }

    /**
     * 格式化时间
     *
     * @param date
     * @param format
     * @return
     */
    public static String formatDate(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    public static Date endTimeDate(Date date) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String start = sdf.format(date);
            try {
                date = sdf1.parse(start + " 23:59:59");
            } catch (Exception ignored) {
            }
        }
        return date;
    }

}



