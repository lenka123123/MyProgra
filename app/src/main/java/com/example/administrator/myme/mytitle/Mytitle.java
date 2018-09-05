package com.example.administrator.myme.mytitle;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.example.administrator.myme.R;

public class Mytitle extends AppCompatActivity {

    private Window window;

    public void dong(View view) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        View decorView = getWindow().getDecorView();//获取屏幕的decorView
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);//设置全屏

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mytitle);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        View decorView = getWindow().getDecorView();//获取屏幕的decorView
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);//设置全屏

//
//        window = getWindow();
//        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//
//        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);//设置全屏
//
//        ViewGroup decorViewGroup = (ViewGroup) window.getDecorView();
//        View statusBarView = new View(window.getContext());
//        int statusBarHeight = getStatusBarHeight(window.getContext());
//        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, statusBarHeight);
//        params.gravity = Gravity.TOP;
//        statusBarView.setLayoutParams(params);
//        decorViewGroup.addView(statusBarView);

/**
 *
 setSystemUiVisibility(int visibility)传入的实参类型如下：

 1.View.SYSTEM_UI_FLAG_VISIBLE ：状态栏和Activity共存，Activity不全屏显示。也就是应用平常的显示画面

 2.View.SYSTEM_UI_FLAG_FULLSCREEN ：Activity全屏显示，且状态栏被覆盖掉

 3. View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN ：Activity全屏显示，但是状态栏不会被覆盖掉，而是正常显示，只是Activity顶端布   局会被覆盖住

 4.View.INVISIBLE ： Activity全屏显示，隐藏状态栏
 */
    }

    private static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }
}
