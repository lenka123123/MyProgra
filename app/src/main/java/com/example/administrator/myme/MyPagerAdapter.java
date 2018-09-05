package com.example.administrator.myme;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyPagerAdapter extends FragmentPagerAdapter {
    private String[] mTitles = {"远程对讲","报警记录","开门记录","留言记录"};
    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public BaseFragment getItem(int position) {
        BaseFragment fragment = SmartFragmentFactory.createFragment(position);
        return fragment;
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
