package com.example.administrator.myme.datacalendar.Bean.calendars;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.myme.R;
import com.example.administrator.myme.datacalendar.Adapter.AllUseAdapter;
import com.example.administrator.myme.datacalendar.Holder.AllUseViewHolder;
import com.example.administrator.myme.datacalendar.Utils.CalendarManagerUtils;
import com.example.administrator.myme.datacalendar.Utils.MeasureUtil;
import com.example.administrator.myme.datacalendar.View.MyGridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by zhq_zhao on 2017/12/21.
 */
public class VerticalSlideCalendar {
    //数据源嵌套在内部的实体
    List<Object> innerlist = new ArrayList<>();
    //内部嵌套的数据实体
    private CalendarManagerUtils instance;
    private List<Object> threeArrList;
    private LunarCalendar lunarCalendar;
    private ArrayList arryLunarCalendarList;
    //记录第一次点时间的位置集合
    Map<String, Integer> selectStart = new HashMap<>();
    //记录第二次点击选择时间的位置集合
    Map<String, Integer> selectEnd = new HashMap<>();
    private int clickNumber = 1;//记录点击次数
    private OutAdapter outAdapter;
    private Context context;

    public VerticalSlideCalendar(Context context) {
        this.context = context;
        for (int i = 0; i < 36; i++) {
            innerlist.add(i);
        }
        if (instance == null) {
            instance = CalendarManagerUtils.getInstance();

        }
        //获取最近三年数据信息集合
        instance.setThreeYearArralistDatas();
        threeArrList = instance.getThreeArrList();
        //获取农历日历信息
        lunarCalendar = new LunarCalendar();
        selectStart.clear();
        selectEnd.clear();


    }

    public View getCalendarView(Context context, int layoutId) {
        View view = View.inflate(context, layoutId, null);
        initview(view);
        return view;
    }

    /**
     * 获取农历日历信息
     *
     * @param year
     * @param month
     */
    private void getLunarCalendar(int year, int month) {

        //获取农历数据
        String[][] strings = lunarCalendar.buildMonthFestival(year, month);
        arryLunarCalendarList = new ArrayList();
        for (int i = 0; i < strings.length; i++) {
            String[] string = strings[i];
            for (int j = 0; j < string.length; j++) {
                String s = string[j];
                arryLunarCalendarList.add(s);
            }
        }
    }


    /**
     * innerlist：三年的月份长度36个月
     */
    private void initview(View view) {
        final ListView listview = (ListView) view.findViewById(R.id.list_item);
        //获取当前时间
        instance.initCalendarDate();
        int year = instance.getmYear();
        int day = instance.getmDay();
        int month = instance.getmMonth();
        if (outAdapter == null) {
            outAdapter = new OutAdapter(context, innerlist, R.layout.list_items, year, month, day);

        }
        listview.setAdapter(outAdapter);

        //定位到当前月份数据
        showCurrentMonth(year, month, listview, threeArrList);

    }

    public class OutAdapter extends AllUseAdapter {
        private final int year;
        private final int month;
        private final int day;

        public OutAdapter(Context context, List list, int itemlayoutId, int year, int month, int day) {
            super(context, list, itemlayoutId);
            this.year = year;
            this.month = month;
            this.day = day;
        }

        @Override
        protected void convert(AllUseViewHolder viewHolder, Object item, final int fatherposition) {
            final TextView tv_year = viewHolder.getView(R.id.tv_year);
            final TextView tv_month = viewHolder.getView(R.id.tv_month);
            //外面一层控制年份和月份
            MyGridView item_grideview = viewHolder.getView(R.id.item_gridview);
            item_grideview.setAdapter(new AllUseAdapter<Object>(context, threeArrList, R.layout.item_gride) {


                @Override
                protected void convert(final AllUseViewHolder viewHolder, Object item, final int position) {

                    //里面一层控制当前的天数
                    final TextView textView = viewHolder.getView(R.id.tv_date);
                    final TextView tv_lunarCalendar = viewHolder.getView(R.id.tv_lunarCalendar);
                    final LinearLayout mll_select = viewHolder.getView(R.id.ll_select);
                    Map o = (Map) threeArrList.get(fatherposition);
                    String mdayofWeek = String.valueOf(o.get("mdayofWeek"));
                    String mMonth = String.valueOf(o.get("mMonth"));
                    String mYear = String.valueOf(o.get("mYear"));
                    String maxNum = String.valueOf(o.get("maxNum"));
                    tv_month.setText(mMonth);
                    tv_year.setText(mYear);

                    //获取农历信息集合
                    getLunarCalendar(Integer.parseInt(mYear), Integer.parseInt(mMonth));
                    //农历信息展示
                    setLurCalendarShows(position, tv_lunarCalendar, mdayofWeek, maxNum);
                    //设置阳历信息
                    setCalendarDayInfos(position, textView, mdayofWeek, maxNum);
                    //如果是今日以前的数据显示灰色
                    showPreDataBackGroundGray(year, month, day, textView, tv_year, tv_month, tv_lunarCalendar, mll_select);
                    if (textView.getText().toString().equals("0")) {
                        textView.setVisibility(View.GONE);
                    } else {
                        textView.setVisibility(View.VISIBLE);
                    }
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            Toast.makeText(context, tv_year.getText().toString() + tv_month.getText().toString() + textView.getText().toString(), Toast.LENGTH_SHORT).show();

                            outAdapter.notifyDataSetChanged();
                            if (clickNumber % 2 == 0) {//偶数说明是结尾
                                if (selectEnd.size() == 0) {//记录第二次点击
                                    selectEnd.put("end_year", Integer.parseInt(tv_year.getText().toString()));
                                    selectEnd.put("end_month", Integer.parseInt(tv_month.getText().toString()));
                                    selectEnd.put("end_day", Integer.parseInt(textView.getText().toString()));
                                } else if (selectEnd.size() == 3) {
                                    selectEnd.clear();
                                    selectEnd.put("end_year", Integer.parseInt(tv_year.getText().toString()));
                                    selectEnd.put("end_month", Integer.parseInt(tv_month.getText().toString()));
                                    selectEnd.put("end_day", Integer.parseInt(textView.getText().toString()));

                                }
                                if(mOnTwoClickListener!=null){
                                    int startRange = 0;
                                    int endRange = 0;
                                    //拿到第一次点击的年月日
                                    Integer start_year = selectStart.get("start_year");
                                    Integer start_month = selectStart.get("start_month");
                                    Integer start_day = selectStart.get("start_day");
                                    //拿到第二次点击的年月日
                                    Integer end_year = selectEnd.get("end_year");
                                    Integer end_month = selectEnd.get("end_month");
                                    Integer end_day = selectEnd.get("end_day");

                                    if (start_month < 10) {
                                        if (start_day < 10) {
                                            startRange = Integer.parseInt(start_year + "0" + start_month + "0" + start_day);
                                        } else {
                                            startRange = Integer.parseInt(start_year + "0" + start_month + "" + start_day);
                                        }
                                    } else {
                                        if (start_day < 10) {
                                            startRange = Integer.parseInt(start_year + "" + start_month + "0" + start_day);
                                        } else {
                                            startRange = Integer.parseInt(start_year + "" + start_month + "" + start_day);
                                        }
                                    }
                                    if (end_month < 10) {
                                        if (end_day < 10) {
                                            endRange = Integer.parseInt(end_year + "0" + end_month + "0" + end_day);
                                        } else {
                                            endRange = Integer.parseInt(end_year + "0" + end_month + "" + end_day);
                                        }
                                    } else {
                                        if (start_day < 10) {
                                            endRange = Integer.parseInt(end_year + "" + end_month + "0" + end_day);
                                        } else {
                                            endRange = Integer.parseInt(end_year + "" + end_month + "" + end_day);
                                        }
                                    }
                                    mOnTwoClickListener.setOnTwoClickListener(String.valueOf(startRange),String.valueOf(endRange));
                                }

                            } else {//奇数说明是开头
                                if (selectStart.size() == 0) {//记录第一次已经点击了
                                    selectStart.put("start_year", Integer.parseInt(tv_year.getText().toString()));
                                    selectStart.put("start_month", Integer.parseInt(tv_month.getText().toString()));
                                    selectStart.put("start_day", Integer.parseInt(textView.getText().toString()));
                                } else if (selectStart.size() == 3) {
                                    selectStart.clear();
                                    selectStart.put("start_year", Integer.parseInt(tv_year.getText().toString()));
                                    selectStart.put("start_month", Integer.parseInt(tv_month.getText().toString()));
                                    selectStart.put("start_day", Integer.parseInt(textView.getText().toString()));
                                    if (selectEnd.size() == 3) {
                                        selectEnd.clear();
                                    }

                                }
                            }

                            clickNumber = clickNumber + 1;
                        }
                    });


                    //判断连续点击逻辑
                    if (selectStart.size() > 0 && selectEnd.size() > 0) {
                        int startRange = 0;
                        int endRange = 0;
                        int selectRange = 0;
                        //拿到第一次点击的年月日
                        Integer start_year = selectStart.get("start_year");
                        Integer start_month = selectStart.get("start_month");
                        Integer start_day = selectStart.get("start_day");
                        //拿到第二次点击的年月日
                        Integer end_year = selectEnd.get("end_year");
                        Integer end_month = selectEnd.get("end_month");
                        Integer end_day = selectEnd.get("end_day");

                        if (start_month < 10) {
                            if (start_day < 10) {
                                startRange = Integer.parseInt(start_year + "0" + start_month + "0" + start_day);
                            } else {
                                startRange = Integer.parseInt(start_year + "0" + start_month + "" + start_day);
                            }
                        } else {
                            if (start_day < 10) {
                                startRange = Integer.parseInt(start_year + "" + start_month + "0" + start_day);
                            } else {
                                startRange = Integer.parseInt(start_year + "" + start_month + "" + start_day);
                            }
                        }
                        if (end_month < 10) {
                            if (end_day < 10) {
                                endRange = Integer.parseInt(end_year + "0" + end_month + "0" + end_day);
                            } else {
                                endRange = Integer.parseInt(end_year + "0" + end_month + "" + end_day);
                            }
                        } else {
                            if (start_day < 10) {
                                endRange = Integer.parseInt(end_year + "" + end_month + "0" + end_day);
                            } else {
                                endRange = Integer.parseInt(end_year + "" + end_month + "" + end_day);
                            }
                        }
                        if (Integer.parseInt(tv_month.getText().toString()) < 10) {
                            if (Integer.parseInt(textView.getText().toString()) < 10) {
                                selectRange = Integer.parseInt(Integer.parseInt(tv_year.getText().toString()) + "0" + Integer.parseInt(tv_month.getText().toString()) + "0" + Integer.parseInt(textView.getText().toString()));
                            } else {
                                selectRange = Integer.parseInt(Integer.parseInt(tv_year.getText().toString()) + "0" + Integer.parseInt(tv_month.getText().toString()) + "" + Integer.parseInt(textView.getText().toString()));
                            }
                        } else {
                            if (Integer.parseInt(textView.getText().toString()) < 10) {
                                selectRange = Integer.parseInt(Integer.parseInt(tv_year.getText().toString()) + "" + Integer.parseInt(tv_month.getText().toString()) + "0" + Integer.parseInt(textView.getText().toString()));
                            } else {
                                selectRange = Integer.parseInt(Integer.parseInt(tv_year.getText().toString()) + "" + Integer.parseInt(tv_month.getText().toString()) + "" + Integer.parseInt(textView.getText().toString()));
                            }
                        }

                        if (selectRange >= startRange && selectRange <= endRange) {
                            if (textView.getText().toString().equals("0")) {
                                viewHolder.setBackground(R.id.ll_select, R.color.transparent);
                            } else {

                                viewHolder.setBackground(R.id.ll_select, R.color.btn_press_color);
                            }
                        } else {
                            viewHolder.setBackground(R.id.ll_select, R.color.transparent);
                        }


                    } else if (selectStart.size() > 0 && selectEnd.size() == 0) {//第一次点击
                        //拿到第一次点击的年月日
                        Integer start_year = selectStart.get("start_year");
                        Integer start_month = selectStart.get("start_month");
                        Integer start_day = selectStart.get("start_day");

                        if (Integer.parseInt(tv_year.getText().toString()) == start_year && Integer.parseInt(tv_month.getText().toString()) == start_month && Integer.parseInt(textView.getText().toString()) == start_day) {
                            viewHolder.setBackground(R.id.ll_select, R.color.btn_press_color);

                        } else {
                            viewHolder.setBackground(R.id.ll_select, R.color.transparent);
                        }

                    }

                }
            });
        }


    }


    /**
     * 农历信息展示
     *
     * @param position
     * @param tv_lunarCalendar
     * @param mdayofWeek
     * @param maxNum
     */
    private void setLurCalendarShows(int position, TextView tv_lunarCalendar, String mdayofWeek, String maxNum) {
        if (position < 42) {
            if (mdayofWeek.equals("1")) {
                if (maxNum.equals("28")) {
                    tv_lunarCalendar.setText(arryLunarCalendarList.get(position).toString());
                } else if (maxNum.equals("29")) {
                    tv_lunarCalendar.setText(arryLunarCalendarList.get(position).toString());
                } else if (maxNum.equals("30")) {
                    tv_lunarCalendar.setText(arryLunarCalendarList.get(position).toString());
                } else if (maxNum.equals("31")) {
                    tv_lunarCalendar.setText(arryLunarCalendarList.get(position).toString());
                }
            } else if (mdayofWeek.equals("2")) {
                if (maxNum.equals("28")) {
                    tv_lunarCalendar.setText(arryLunarCalendarList.get(position).toString());
                } else if (maxNum.equals("29")) {
                    tv_lunarCalendar.setText(arryLunarCalendarList.get(position).toString());
                } else if (maxNum.equals("30")) {
                    tv_lunarCalendar.setText(arryLunarCalendarList.get(position).toString());
                } else if (maxNum.equals("31")) {
                    tv_lunarCalendar.setText(arryLunarCalendarList.get(position).toString());
                }
            } else if (mdayofWeek.equals("3")) {
                if (maxNum.equals("28")) {
                    tv_lunarCalendar.setText(arryLunarCalendarList.get(position).toString());
                } else if (maxNum.equals("29")) {
                    tv_lunarCalendar.setText(arryLunarCalendarList.get(position).toString());
                } else if (maxNum.equals("30")) {
                    tv_lunarCalendar.setText(arryLunarCalendarList.get(position).toString());
                } else if (maxNum.equals("31")) {
                    tv_lunarCalendar.setText(arryLunarCalendarList.get(position).toString());
                }
            } else if (mdayofWeek.equals("4")) {
                if (maxNum.equals("28")) {
                    tv_lunarCalendar.setText(arryLunarCalendarList.get(position).toString());
                } else if (maxNum.equals("29")) {
                    tv_lunarCalendar.setText(arryLunarCalendarList.get(position).toString());
                } else if (maxNum.equals("30")) {
                    tv_lunarCalendar.setText(arryLunarCalendarList.get(position).toString());
                } else if (maxNum.equals("31")) {
                    tv_lunarCalendar.setText(arryLunarCalendarList.get(position).toString());
                }
            } else if (mdayofWeek.equals("5")) {
                if (maxNum.equals("28")) {
                    tv_lunarCalendar.setText(arryLunarCalendarList.get(position).toString());
                } else if (maxNum.equals("29")) {
                    tv_lunarCalendar.setText(arryLunarCalendarList.get(position).toString());
                } else if (maxNum.equals("30")) {
                    tv_lunarCalendar.setText(arryLunarCalendarList.get(position).toString());
                } else if (maxNum.equals("31")) {
                    tv_lunarCalendar.setText(arryLunarCalendarList.get(position).toString());

                }
            } else if (mdayofWeek.equals("6")) {

                if (maxNum.equals("28")) {
                    tv_lunarCalendar.setText(arryLunarCalendarList.get(position).toString());
                } else if (maxNum.equals("29")) {
                    tv_lunarCalendar.setText(arryLunarCalendarList.get(position).toString());
                } else if (maxNum.equals("30")) {
                    tv_lunarCalendar.setText(arryLunarCalendarList.get(position).toString());
                } else if (maxNum.equals("31")) {
                    tv_lunarCalendar.setText(arryLunarCalendarList.get(position).toString());


                }
            } else if (mdayofWeek.equals("7")) {
                if (maxNum.equals("28")) {
                    tv_lunarCalendar.setText(arryLunarCalendarList.get(position).toString());
                } else if (maxNum.equals("29")) {
                    tv_lunarCalendar.setText(arryLunarCalendarList.get(position).toString());
                } else if (maxNum.equals("30")) {
                    tv_lunarCalendar.setText(arryLunarCalendarList.get(position).toString());
                } else if (maxNum.equals("31")) {
                    tv_lunarCalendar.setText(arryLunarCalendarList.get(position).toString());
                }
            }
        }

    }

    /**
     * 设置阳历信息
     *
     * @param position
     * @param textView
     * @param mdayofWeek
     * @param maxNum
     */
    private void setCalendarDayInfos(int position, TextView textView, String mdayofWeek, String maxNum) {

        //星期天到星期六依次对应1-7
        if (mdayofWeek.equals("1")) {
            if (maxNum.equals("28")) {
                if (position <= 27) {
                    textView.setText(String.valueOf(position + 1));

                }

            } else if (maxNum.equals("29")) {
                if (position <= 28) {
                    textView.setText(String.valueOf(position + 1));
                }
            } else if (maxNum.equals("30")) {
                if (position <= 29) {
                    textView.setText(String.valueOf(position + 1));
                }
            } else if (maxNum.equals("31")) {
                if (position <= 30) {
                    textView.setText(String.valueOf(position + 1));
                }
            }

        } else if (mdayofWeek.equals("2")) {
            if (maxNum.equals("28")) {
                if (position <= 28 && position >= 1) {
                    textView.setText(String.valueOf(position));
                }
            } else if (maxNum.equals("29")) {
                if (position <= 29 && position >= 1) {
                    textView.setText(String.valueOf(position));
                }
            } else if (maxNum.equals("30")) {
                if (position <= 30 && position >= 1) {
                    textView.setText(String.valueOf(position));
                }
            } else if (maxNum.equals("31")) {
                if (position <= 31 && position >= 1) {
                    textView.setText(String.valueOf(position));
                }
            }
        } else if (mdayofWeek.equals("3")) {
            if (maxNum.equals("28")) {
                if (position < 30 && position >= 2) {
                    textView.setText(String.valueOf(position - 1));
                }
            } else if (maxNum.equals("29")) {
                if (position < 31 && position >= 2) {
                    textView.setText(String.valueOf(position - 1));
                }
            } else if (maxNum.equals("30")) {
                if (position < 32 && position >= 2) {
                    textView.setText(String.valueOf(position - 1));
                }
            } else if (maxNum.equals("31")) {
                if (position < 33 && position >= 2) {
                    textView.setText(String.valueOf(position - 1));
                }
            }
        } else if (mdayofWeek.equals("4")) {
            if (maxNum.equals("28")) {
                if (position < 31 && position >= 3) {
                    textView.setText(String.valueOf(position - 2));
                }
            } else if (maxNum.equals("29")) {
                if (position < 32 && position >= 3) {
                    textView.setText(String.valueOf(position - 2));
                }
            } else if (maxNum.equals("30")) {
                if (position < 33 && position >= 3) {
                    textView.setText(String.valueOf(position - 2));
                }
            } else if (maxNum.equals("31")) {
                if (position < 34 && position >= 3) {
                    textView.setText(String.valueOf(position - 2));
                }
            }
        } else if (mdayofWeek.equals("5")) {
            if (maxNum.equals("28")) {
                if (position < 32 && position >= 4) {
                    textView.setText(String.valueOf(position - 3));
                }
            } else if (maxNum.equals("29")) {
                if (position < 33 && position >= 4) {
                    textView.setText(String.valueOf(position - 3));
                }
            } else if (maxNum.equals("30")) {
                if (position < 34 && position >= 4) {
                    textView.setText(String.valueOf(position - 3));
                }
            } else if (maxNum.equals("31")) {
                if (position < 35 && position >= 4) {
                    textView.setText(String.valueOf(position - 3));
                }
            }
        } else if (mdayofWeek.equals("6")) {
            if (maxNum.equals("28")) {
                if (position < 33 && position >= 5) {
                    textView.setText(String.valueOf(position - 4));
                }
            } else if (maxNum.equals("29")) {
                if (position < 34 && position >= 5) {
                    textView.setText(String.valueOf(position - 4));
                }
            } else if (maxNum.equals("30")) {
                if (position < 35 && position >= 5) {
                    textView.setText(String.valueOf(position - 4));
                }
            } else if (maxNum.equals("31")) {
                if (position < 36 && position >= 5) {
                    textView.setText(String.valueOf(position - 4));
                }
            }
        } else if (mdayofWeek.equals("7")) {

            if (maxNum.equals("28")) {
                if (position < 34 && position >= 6) {
                    textView.setText(String.valueOf(position - 5));
                }
            } else if (maxNum.equals("29")) {
                if (position < 35 && position >= 6) {
                    textView.setText(String.valueOf(position - 5));
                }
            } else if (maxNum.equals("30")) {
                if (position < 36 && position >= 6) {
                    textView.setText(String.valueOf(position - 5));
                }
            } else if (maxNum.equals("31")) {
                if (position < 37 && position >= 6) {
                    textView.setText(String.valueOf(position - 5));

                }
            }
        }
    }

    /**
     * 定位到当前月份
     *
     * @param year
     * @param month
     * @param listview
     * @param threeArrList
     */
    private void showCurrentMonth(int year, int month, ListView listview, List<Object> threeArrList) {
        for (int i = 0; i < 36; i++) {
            Map map = (Map) threeArrList.get(i);
            String cMonth = String.valueOf(map.get("mMonth"));
            String cYear = String.valueOf(map.get("mYear"));
            if (year == Integer.parseInt(cYear) && month == Integer.parseInt(cMonth)) {
                listview.setSelection(i);

            }
        }
    }

    /**
     * 如果是今日以前的数据显示灰色
     * 规定灰色的不能点击
     *
     * @param year
     * @param month
     * @param day
     * @param mTextView
     * @param tv_year
     * @param tv_month
     */
    private void showPreDataBackGroundGray(int year, int month, int day, TextView mTextView, TextView tv_year, TextView tv_month, TextView tv_lunarCalendar, LinearLayout mll_select) {
        if (!mTextView.getText().toString().equals("") && mTextView.getText().toString() != null) {
            if (year > Integer.parseInt(tv_year.getText().toString())) {//去年显示灰色
                mTextView.setTextColor(context.getResources().getColor(R.color.gray));
                mTextView.setEnabled(false);
            } else if (year == Integer.parseInt(tv_year.getText().toString()) && month > Integer.parseInt(tv_month.getText().toString())) {//今年当月以前的月份日期显示灰色
                mTextView.setTextColor(context.getResources().getColor(R.color.gray));
                mTextView.setEnabled(false);
            } else if (year == Integer.parseInt(tv_year.getText().toString()) && month == Integer.parseInt(tv_month.getText().toString()) && day
                    > Integer.parseInt(mTextView.getText().toString())) {//今年当月以前的天数显示灰色
                mTextView.setTextColor(context.getResources().getColor(R.color.gray));
                mTextView.setEnabled(false);
            } else if (year == Integer.parseInt(tv_year.getText().toString()) && month == Integer.parseInt(tv_month.getText().toString()) && day
                    == Integer.parseInt(mTextView.getText().toString())) {//今天
                tv_lunarCalendar.setTextColor(context.getResources().getColor(R.color.red));
                tv_lunarCalendar.setText("今天");
                tv_lunarCalendar.setTextSize(MeasureUtil.dp2px(context, 12));
                mll_select.setBackgroundColor(context.getResources().getColor(R.color.login_forget_pwd));

            }

        } else {
            mTextView.setTextColor(context.getResources().getColor(R.color.gray));
            mTextView.setEnabled(false);
        }
    }

    private OnTwoClickListener mOnTwoClickListener;

    public interface OnTwoClickListener {
        void setOnTwoClickListener(String startDate, String endDate);
    }

    public void setmOnTwoClickListener(OnTwoClickListener mOnTwoClickListener) {
        this.mOnTwoClickListener = mOnTwoClickListener;
    }
}
