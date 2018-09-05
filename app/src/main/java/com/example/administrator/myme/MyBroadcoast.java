package com.example.administrator.myme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class MyBroadcoast extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
      System.out.println("dddddd");//可以收到通知
    }
}
