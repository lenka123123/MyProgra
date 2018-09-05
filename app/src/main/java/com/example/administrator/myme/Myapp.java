package com.example.administrator.myme;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.util.DisplayMetrics;

import com.baidu.mapapi.SDKInitializer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.jpush.android.api.JPushInterface;

public class Myapp extends Application {
    private static Myapp instance;


    public static ExecutorService cThreadPool;
    static DisplayMetrics mDisplayMetrics;

    public static int dpToPx(int dp) {
        return (int) (mDisplayMetrics.density * dp);
    }

    public static int getScreenWidth() {
        return mDisplayMetrics.widthPixels;
    }

    public static Context getInstance() {
        return instance;
    }

    //BDAbstractLocationListener为7.2版本新增的Abstract类型的监听接口
//原有BDLocationListener接口暂时同步保留。具体介绍请参考后文中的说明
    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }

        instance = this;


        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        mDisplayMetrics = getResources().getDisplayMetrics();
        cThreadPool = Executors.newFixedThreadPool(5);
        SDKInitializer.initialize(this.getApplicationContext());
        //注册监听函数
    }


    public static String getFromAssets(Context context, String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
