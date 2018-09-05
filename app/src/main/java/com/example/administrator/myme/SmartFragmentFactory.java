package com.example.administrator.myme;

import java.util.HashMap;

public class SmartFragmentFactory {
    private static HashMap<Integer, BaseFragment> mBaseFragments = new HashMap<Integer, BaseFragment>();
    public static BaseFragment createFragment(int position){
        BaseFragment baseFragment = mBaseFragments.get(position);

        if (baseFragment == null) {
            switch (position) {
                case 0:
                    baseFragment = new RemoteVideoFragment();
                    break;
                case 1:
                    baseFragment = new RemoteVideoFragment();
                    break;
                case 2:
                    baseFragment = new RemoteVideoFragment();
                    break;
                case 3:
                    baseFragment = new RemoteVideoFragment();
                    break;
            }

        }

        mBaseFragments.put(position, baseFragment);
        return baseFragment;
    }
}
