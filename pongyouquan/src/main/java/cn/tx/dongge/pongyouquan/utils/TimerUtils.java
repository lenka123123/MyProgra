package cn.tx.dongge.pongyouquan.utils;

import android.annotation.SuppressLint;

import java.util.concurrent.TimeUnit;

import cn.tx.dongge.pongyouquan.interfaces.OnTimerResultListener;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * @author KCrason
 * @date 2018/5/6
 */
public class TimerUtils {
    @SuppressLint("CheckResult")
    public static void timerTranslation(OnTimerResultListener onTimerResultListener) {
        Single.timer(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread()).subscribe(aLong -> {
            if (onTimerResultListener != null) {
                onTimerResultListener.onTimerResult();
            }
        });
    }
}
