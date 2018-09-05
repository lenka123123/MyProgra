package com.example.administrator.myme;

import android.os.Message;
import android.support.v4.app.Fragment;


    /**
     * Created by gyq on 2018/2/28 15:57
     */
    @ActivityFragmentInject(
            contentViewId = R.layout.fragement_item,
            hasNavigationView = false)
    public class RemoteVideoFragment extends BaseFragment {
        @Override
        protected void toHandleMessage(Message msg) {

        }

        @Override
        protected void findViewAfterViewCreate() {

        }

        @Override
        protected void initDataAfterFindView() {

        }
    }