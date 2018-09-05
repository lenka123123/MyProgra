package com.example.administrator.myme.qinrushi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.myme.R;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.BindView;

/**
 * Created by geyifeng on 2017/5/12.
 */

public class ServiceThreeFragment extends BaseThreeFragment {

    @BindView(R.id.text)
    TextView text;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImmersionBar.setTitleBar(getActivity(), text);
        text.setText("tttttttttttttt");
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_one_service;
    }
}
