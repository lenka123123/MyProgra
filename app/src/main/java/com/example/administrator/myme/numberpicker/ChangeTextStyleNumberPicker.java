package com.example.administrator.myme.numberpicker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.example.administrator.myme.R;

public class ChangeTextStyleNumberPicker extends NumberPicker {


    public ChangeTextStyleNumberPicker(Context context) {
        super(context);
    }

    public ChangeTextStyleNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChangeTextStyleNumberPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void addView(View child) {
        super.addView(child);
        updateView(child);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        super.addView(child, params);
        updateView(child);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        updateView(child);
    }

    @SuppressLint("ResourceType")
    private void updateView(View view) {
        if (view instanceof EditText) {
            ((EditText) view).setTextColor(getResources().getColor(Color.RED));
            ((EditText) view).setTextSize(16);
        }
    }
}
