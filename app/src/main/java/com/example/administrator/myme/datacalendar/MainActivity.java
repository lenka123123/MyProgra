package com.example.administrator.myme.datacalendar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.myme.R;
import com.example.administrator.myme.datacalendar.Bean.calendars.SlideWayManager;
import com.example.administrator.myme.datacalendar.Utils.DPMode;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SlideWayManager slideWayManager=new SlideWayManager(MainActivity.this, R.layout.data_select_activity_main);
        View view = slideWayManager.SlideMode(DPMode.VERTICAL);
        setContentView(view);
        slideWayManager.setCalendarClickListener(new SlideWayManager.OnCalendarClickListener() {
            @Override
            public void setOnCalendarClickListener(String startDate, String endDate) {
                Toast.makeText(MainActivity.this,"选择范围：startDate"+startDate+",endDate:"+endDate,Toast.LENGTH_SHORT).show();
            }
        });
    }
}