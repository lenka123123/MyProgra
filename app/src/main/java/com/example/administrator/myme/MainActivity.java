package com.example.administrator.myme;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import q.rorbin.verticaltablayout.VerticalTabLayout;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewpager;
    private VerticalTabLayout tablayout;

    private MyPagerAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        tablayout = (VerticalTabLayout) findViewById(R.id.vertical_tab);

        viewpager.setAdapter(mAdapter);

        tablayout.setupWithViewPager(viewpager);
    }
}
