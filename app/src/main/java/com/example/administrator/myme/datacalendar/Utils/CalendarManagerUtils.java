package com.example.administrator.myme.datacalendar.Utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by zhq_zhao on 2017/12/15.
 * 日期管理类
 * getActualMaximum（）是获取给定日历字段的可能最大值这个指定是调用这个方法的calender对象，在你的程序中就是c中指定的c.setTime(d)
 */
public class CalendarManagerUtils {
    //获取当前的日期数据
    private Date currentDate;
    //获取指定的日期数据
    private Date specificDate;
    //获取下一个月的日期数据
    private Date nextMonthDate;
    //获取上一个月的日期数据
    private Date preMonthDate;
    //获取农历日期数据
    private Date lunarDate;
    //获取特殊节日的日期数据
    private Date specialDate;
    //获取当前的操作模式，单选、多选、连选、不选
    private String selectMode;
    private static CalendarManagerUtils managerUtils;
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mdayofWeek;
    private int maxNum;
    private Calendar instancecalendar;
    private static Date curDate; // 当前日历显示的月
    //装载去年今年明年数据
    ArrayList threeArrList = new ArrayList<>();

    public ArrayList getThreeArrList() {
        return threeArrList;
    }

    public void setThreeArrList(ArrayList<Map> threeArrList) {
        this.threeArrList = threeArrList;
    }

    /**
     * 初始化当前日期管理对象
     *
     * @return
     */
    public static CalendarManagerUtils getInstance() {
        if (managerUtils == null) {
            managerUtils = new CalendarManagerUtils();

        }
        return managerUtils;
    }

    /**
     * 初始化当前日期
     */
    public void initCalendarDate() {
        instancecalendar = Calendar.getInstance();
        curDate = new Date();
        instancecalendar.setTime(curDate);
        mYear = instancecalendar.get(Calendar.YEAR); //获取当前年份
        mMonth = instancecalendar.get(Calendar.MONTH) + 1;//获取当前月份
        mDay = instancecalendar.get(Calendar.DAY_OF_MONTH);//获取当前月份的日期号码
        instancecalendar.set(Calendar.DATE, 1);//把日期设置为当月第一天
        mdayofWeek = instancecalendar.get(Calendar.DAY_OF_WEEK);//这周第几天
        instancecalendar.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        maxNum = instancecalendar.get(Calendar.DATE);


    }

    /**
     * 获取最近三年的数据
     *
     * @param
     * @return
     */
    public void setThreeYearArralistDatas() {
        threeArrList.clear();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR); //获取当前年份
        calendar.set(year - 1, 0, 1);//指定去年的1月1号
        //获取去年一月份数据
        int mYearone = calendar.get(Calendar.YEAR); //获取当前年份
        int mMonthonte = calendar.get(Calendar.MONTH) + 1;//获取当前月份
        calendar.set(Calendar.DATE, 1);//把日期设置为当月第一天
        int  mdayofWeekone = calendar.get(Calendar.DAY_OF_WEEK);//这周第几天
        calendar.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        int  maxNumone = calendar.get(Calendar.DATE);
        Map<String,Integer> mapone=new HashMap<>();
        mapone.put("mYear",mYearone);
        mapone.put("mMonth",mMonthonte);
        mapone.put("mdayofWeek",mdayofWeekone);
        mapone.put("maxNum",maxNumone);
        threeArrList.add(0,mapone);
        for (int i = 1; i < 36; i++) {
            calendar.add(Calendar.MONTH, 1);
            int mYear = calendar.get(Calendar.YEAR); //获取当前年份
            int mMonth = calendar.get(Calendar.MONTH) + 1;//获取当前月份
            calendar.set(Calendar.DATE, 1);//把日期设置为当月第一天
            int mdayofWeek = calendar.get(Calendar.DAY_OF_WEEK);//这周第几天
            calendar.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
            int maxNum = calendar.get(Calendar.DATE);
            Map<String,Integer> map=new HashMap<>();
            map.put("mYear",mYear);
            map.put("mMonth",mMonth);
            map.put("mdayofWeek",mdayofWeek);
            map.put("maxNum",maxNum);
            threeArrList.add(i,map);
        }
        //需要添加假数据来对应6*7=42
        for (int z = 36; z < 42; z++) {
            Map<Integer,Integer> constructMao=new HashMap<>();
            constructMao.put(z,0);
            threeArrList.add(z,"36");//这里添加是为了防止后面的第三十六位读取不到数据
        }



    }


    /**
     * 上一月数据
     */
    public void setPreMonthDate() {
        instancecalendar.setTime(curDate);
        instancecalendar.add(Calendar.MONTH, -1);
        curDate = instancecalendar.getTime();
        mYear = instancecalendar.get(Calendar.YEAR); //获取当前年份
        mMonth = instancecalendar.get(Calendar.MONTH) + 1;//获取当前月份
        instancecalendar.set(Calendar.DATE, 1);//把日期设置为当月第一天
        mdayofWeek = instancecalendar.get(Calendar.DAY_OF_WEEK);//这周第几天
        instancecalendar.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        maxNum = instancecalendar.get(Calendar.DATE);
        Log.i("sadf", "curDate" + curDate + ":mYear" + mYear + ":mMonth" + mMonth + ":mDay" + mDay + ":maxNum" + maxNum + ":mdayofWeek" + mdayofWeek);
    }

    /**
     * 下一月数据
     */
    public void setNextMonthDate() {
        instancecalendar.setTime(curDate);
        instancecalendar.add(Calendar.MONTH, 1);
        curDate = instancecalendar.getTime();
        mYear = instancecalendar.get(Calendar.YEAR); //获取当前年份
        mMonth = instancecalendar.get(Calendar.MONTH) + 1;//获取当前月份
        instancecalendar.set(Calendar.DATE, 1);//把日期设置为当月第一天
        mdayofWeek = instancecalendar.get(Calendar.DAY_OF_WEEK);//这周第几天
        instancecalendar.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        maxNum = instancecalendar.get(Calendar.DATE);

        Log.i("sadf", "curDate" + curDate + ":mYear" + mYear + ":mMonth" + mMonth + ":mDay" + mDay + ":maxNum" + maxNum + ":mdayofWeek" + mdayofWeek);


    }

    public int getmYear() {
        return mYear;
    }

    public void setmYear(int mYear) {
        this.mYear = mYear;
    }

    public int getmMonth() {
        return mMonth;
    }

    public void setmMonth(int mMonth) {
        this.mMonth = mMonth;
    }

    public int getmDay() {
        return mDay;
    }

    public void setmDay(int mDay) {
        this.mDay = mDay;
    }

    public int getMdayofWeek() {
        return mdayofWeek;
    }

    public void setMdayofWeek(int mdayofWeek) {
        this.mdayofWeek = mdayofWeek;
    }

    public int getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }

    /**
     * 取得当月天数
     */
    public static int getCurrentMonthLastDay() {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 得到指定月的天数
     */
    public static int getMonthLastDay(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

//-------------------------------------------------------------------------------------------------//
//    /**
//     * 得到二个日期间的间隔天数
//     */
//    public static String getTwoDay(String sj1, String sj2) {
//        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
//        long day = 0;
//        try {
//            java.util.Date date = myFormatter.parse(sj1);
//            java.util.Date mydate = myFormatter.parse(sj2);
//            day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
//        } catch (Exception e) {
//            return "";
//        }
//        return day + "";
//    }
//
//    // 上月第一天
//    public String getPreviousMonthFirst() {
//        String str = "";
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Calendar lastDate = Calendar.getInstance();
//        lastDate.set(Calendar.DATE, 1);//设为当前月的1 号
//        lastDate.add(Calendar.MONTH, -1);//减一个月，变为下月的1 号
////lastDate.add(Calendar.DATE,-1);//减去一天，变为当月最后一天
//        str = sdf.format(lastDate.getTime());
//        return str;
//    }
//
//    //获取当月第一天
//    public String getFirstDayOfMonth() {
//        String str = "";
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Calendar lastDate = Calendar.getInstance();
//        lastDate.set(Calendar.DATE, 1);//设为当前月的1 号
//        str = sdf.format(lastDate.getTime());
//        return str;
//    }
//
//    /**
//     * 获取当月的天数
//     */
//    public static int getCurrentMonthDay() {
//        Calendar a = Calendar.getInstance();
//        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
//        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
//        int maxDate = a.get(Calendar.DATE);
//        return maxDate;
//    }
//
//    /**
//     * 获取某月天数
//     *
//     * @param date
//     * @return
//     */
//    public static int getDaysOfMonth(Date date) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
//    }
//
//    /**
//     * 根据 年、月 获取对应的月份 的 天数
//     */
//    public static int getDaysByYearMonth(int year, int month) {
//        Calendar a = Calendar.getInstance();
//        a.set(Calendar.YEAR, year);
//        a.set(Calendar.MONTH, month - 1);
//        a.set(Calendar.DATE, 1);
//        a.roll(Calendar.DATE, -1);
//        int maxDate = a.get(Calendar.DATE);
//        return maxDate;
//    }
}
