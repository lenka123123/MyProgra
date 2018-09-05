package cn.tx.dongge.pongyouquan;

import android.app.Application;
import android.content.Context;

import cn.tx.dongge.pongyouquan.others.DataCenter;


/**
 * @author KCrason
 * @date 2018/5/3
 */
public class FriendsCircleApplication extends Application {

    public static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        DataCenter.init();
    }
}
