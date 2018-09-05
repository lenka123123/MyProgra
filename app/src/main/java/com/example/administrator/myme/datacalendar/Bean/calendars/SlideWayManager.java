package com.example.administrator.myme.datacalendar.Bean.calendars;

import android.content.Context;
import android.view.View;

import com.example.administrator.myme.datacalendar.Utils.DPMode;


/**
 * Created by zhq_zhao on 2017/12/21.
 * 管理水平滑动和上下滑动日历操作管理
 */
public  class SlideWayManager {
    private Context context;
    private int layoutId;
    private  View   view ;
    public SlideWayManager(Context context,int layoutId){
        this.context=context;
        this.layoutId=layoutId;
    }
    //管理的滑动方式
    public View SlideMode(DPMode mode) {
        if (mode.equals(DPMode.HORIZONTAL)) {//上下滑动

        } else if (mode.equals(DPMode.VERTICAL)) {//水平滑动
            VerticalSlideCalendar verticalSlideCalendar = new VerticalSlideCalendar(context);
             view = verticalSlideCalendar.getCalendarView(context, layoutId);
             initOnclickListener(verticalSlideCalendar);
        }
        return view;
    }

    private void initOnclickListener(VerticalSlideCalendar verticalSlideCalendar) {
        verticalSlideCalendar.setmOnTwoClickListener(new VerticalSlideCalendar.OnTwoClickListener() {
            @Override
            public void setOnTwoClickListener(String startDate, String endDate) {
                if(startDate!=null&&endDate!=null){
                    if(mOnCalendarClickListener!=null){
                        mOnCalendarClickListener.setOnCalendarClickListener(startDate,endDate);
                    }
                }
            }
        });
    }
    private OnCalendarClickListener mOnCalendarClickListener;

    public interface OnCalendarClickListener {
        void setOnCalendarClickListener(String startDate, String endDate);
    }

    public void setCalendarClickListener(OnCalendarClickListener mOnCalendarClickListener) {
        this.mOnCalendarClickListener = mOnCalendarClickListener;
    }
}
