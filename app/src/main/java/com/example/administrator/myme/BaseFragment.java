package com.example.administrator.myme;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by gyq on 2018/2/28 15:55
 */

public abstract class BaseFragment extends Fragment implements MyToolBarClickListener {

    protected static final int DEFAULT_LOADING_TIME = 2000;
    protected static final int WHAT_LOAD = 1;
    protected static final int WHAT_LOAD_FINISH = 2;
    protected static final int WHAT_REFRESH = 3;

    protected Activity mActivity;
    protected Context mContext;
    protected View mRootView;

    protected ActivityFragmentInject annotation;
    protected int contentViewId;

    protected Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            toHandleMessage(msg);
        }
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        mContext = getContext();
        if (!getClass().isAnnotationPresent(ActivityFragmentInject.class)) {
            throw new RuntimeException("must use ActivityFragmentInitParams.class");
        }
        annotation = getClass().getAnnotation(ActivityFragmentInject.class);
        contentViewId = annotation.contentViewId();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = View.inflate(mContext, contentViewId, null);
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        initViewNData();
        return mRootView;
    }

    protected void initViewNData() {
        if (annotation.hasToolbar()) {
            //initToolbar();
        }
        findViewAfterViewCreate();
        initDataAfterFindView();
    }

    protected void initToolbar() {

    }

    protected abstract void toHandleMessage(Message msg);

    protected abstract void findViewAfterViewCreate();

    protected abstract void initDataAfterFindView();


    @Override
    public void leftTextClick(View view) {
    }

    @Override
    public void leftIconClick(View view) {
    }

    @Override
    public void rightTextClick(View view) {
    }

    @Override
    public void rightIconClick(View view) {
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return true;
    }}