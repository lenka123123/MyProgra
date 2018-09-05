package com.example.administrator.myme.numberpicker;

import android.app.AlertDialog;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myme.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class NumberPickerAct extends AppCompatActivity {


    NumberPicker np1, np2, center;
    //定义最低价格、最高价格的初始值
    int minPrice = 0, maxPrice = 0;
    private String[] array;
    private String[] center1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List list = new ArrayList();
        for (int i = 16; i < 51; i++) {
            list.add(String.valueOf(i));
        }
        list.add(0, "不限");
        array = new String[list.size()];
        list.toArray(array);
        center1 = new String[]{"一"};

        showTime();
    }

    void showTime() {
        final AlertDialog dlg = new AlertDialog.Builder(NumberPickerAct.this).create();
        dlg.show();
        Window window = dlg.getWindow();
        window.setGravity(Gravity.BOTTOM);//设置弹框在屏幕的下方
        window.setContentView(R.layout.layout_number_picker);

        np1 = window.findViewById(R.id.left);
        np2 = window.findViewById(R.id.right);
        center = window.findViewById(R.id.center);
        TextView textCancel = window.findViewById(R.id.tv_cancel);
        TextView textConfirm = window.findViewById(R.id.tv_confirm);
        textCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlg.dismiss();
            }
        });

        textConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlg.dismiss();
            }
        });

        setDatePickerDividerColor(np1);
        //设置np1的最小值和最大值
        np1.setMinValue(0);
        np1.setMaxValue(array.length - 1);
        //设置np1的当前值
        np1.setValue(0);

        np1.setWrapSelectorWheel(false);//是否循环滚动
        np1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);//禁止编辑
        np1.setDisplayedValues(array);
        np1.setOnValueChangedListener(new OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                minPrice = newVal;
                np2.setValue(minPrice);
                np2.setMaxValue(array.length - 1);
            }
        });


        center.setMinValue(0);
        center.setValue(0);

        center.setWrapSelectorWheel(false);//是否循环滚动
        center.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);//禁止编辑
        center.setDisplayedValues(center1);


        //设置np2的当前值
        np2.setMinValue(0);
        np2.setValue(0);
        np2.setMaxValue(array.length - 1);

        np2.setWrapSelectorWheel(false);//是否循环滚动
        np2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);//禁止编辑
        np2.setDisplayedValues(array);
        np2.setOnValueChangedListener(new OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                maxPrice = newVal;

            }
        });

    }

    private void showSelectedPrice() {

//        final String[] array = new String[]{"频道1", "频道2", "频道3", "频道4", "频道5"};
//        View view = View.inflate(this, R.layout.layout_number_picker, null);
//        NumberPicker numberPicker = (NumberPicker) view.findViewById(R.id.number_picker);
//        numberPicker.setMaxValue(array.length - 1);
//        numberPicker.setMinValue(0);
//        numberPicker.setValue(0);
//        numberPicker.setWrapSelectorWheel(false);//是否循环滚动
//        numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);//禁止编辑
//        numberPicker.setDisplayedValues(array);
//        new AlertDialog.Builder(this)
//                .setTitle("")
//                .setView(view)
//                .create()
//                .show();


    }


    private void setDatePickerDividerColor(NumberPicker picker) {
        Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    pf.set(picker, new ColorDrawable(Color.RED));//这里是将其隐藏,如有其它需要,修改这里的文字即可
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }


}
