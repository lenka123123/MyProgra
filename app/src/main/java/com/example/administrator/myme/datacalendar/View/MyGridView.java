package com.example.administrator.myme.datacalendar.View;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by zhq_zhao on 2017/5/9.
 */
public class MyGridView extends GridView {
    public MyGridView(Context context, AttributeSet attrs) {

        this(context, attrs,0);
    }

    public MyGridView(Context context) {
        this(context,null);
    }

    public MyGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}