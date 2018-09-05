package com.example.administrator.myme.dong;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.administrator.myme.R;

import java.util.List;

public class ww extends AppCompatActivity {

    private Context context;
    private PopupWindow pop;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragement_item);
        context = this;
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPop();
            }
        });
    }

    public void showPop() {
        LayoutInflater inflater = LayoutInflater.from(getApplication());
// 获取当面界面的主布局
        View rootView = ww.this.findViewById(R.id.button);
        View popView = inflater.inflate(R.layout.fragement_item, null, false);
        pop = new PopupWindow(popView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);
// 需要设置一下此参数，点击外边可消失
// 如果不设置PopupWindow的背景，有些版本就会出现一个问题：无论是点击外部区域还是Back键都无法dismiss弹框
        pop.setBackgroundDrawable(new BitmapDrawable());
// 设置此参数获得焦点，否则无法点击
        pop.setFocusable(true);
// 设置点击窗口外边窗口消失
        pop.setOutsideTouchable(true);


        View windowContentViewRoot = popView;
        int windowPos[] = calculatePopWindowPos(rootView, windowContentViewRoot);
        int xOff = 20;// 可以自己调整偏移

        //在当前界面正下方显示
        //   pop.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
// 显示在相对于view的左下方（这个左下方是在屏幕的左下方）
//        pop.showAtLocation(view, Gravity.TOP | Gravity.RIGHT, 0, 0);
// 用来保存按钮view的坐标，location[0]是X轴，location[1]是Y轴
        int[] location = new int[2];
        Log.d("Taonce", "location:" + location.toString());
//        view.getLocationOnScreen(location);
//// 在按钮view的左边
//        pop.showAtLocation(view, Gravity.NO_GRAVITY, location[0] - view.getWidth(), location[1]);
//// 左上方
//        pop.showAtLocation(view, Gravity.NO_GRAVITY, location[0] - view.getWidth(), location[1] - view.getHeight());
//// 左下方
//        pop.showAtLocation(view, Gravity.NO_GRAVITY, location[0] - view.getWidth(), location[1] + view.getHeight());
//


    }

    /**
     * 计算出来的位置，y方向就在anchorView的上面和下面对齐显示，x方向就是与屏幕右边对齐显示
     * 如果anchorView的位置有变化，就可以适当自己额外加入偏移来修正
     *
     * @param anchorView  呼出window的view
     * @param contentView window的内容布局
     * @return window显示的左上角的xOff, yOff坐标
     */
    private int[] calculatePopWindowPos(final View anchorView, final View contentView) {
        final int windowPos[] = new int[2];
        final int anchorLoc[] = new int[2];
        // 获取锚点View在屏幕上的左上角坐标位置
        anchorView.getLocationOnScreen(anchorLoc);
        final int anchorHeight = anchorView.getHeight();
        // 获取屏幕的高宽
        final int screenHeight = getScreenHeight(anchorView.getContext());
        final int screenWidth = getScreenWidth(anchorView.getContext());
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        // 计算contentView的高宽
        final int windowHeight = contentView.getMeasuredHeight();
        final int windowWidth = contentView.getMeasuredWidth();
        // 判断需要向上弹出还是向下弹出显示
        final boolean isNeedShowUp = (screenHeight - anchorLoc[1] - anchorHeight < windowHeight);
        if (isNeedShowUp) {
            windowPos[0] = screenWidth - windowWidth;
            windowPos[1] = anchorLoc[1] - windowHeight;
        } else {
            windowPos[0] = screenWidth - windowWidth;
            windowPos[1] = anchorLoc[1] + anchorHeight;
        }
        return windowPos;
    }


    /**
     * 获取屏幕高度(px)
     */
    public int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取屏幕宽度(px)
     */
    public int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }
}
