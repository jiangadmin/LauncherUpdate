package com.jiang.launcherupdate;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;

import java.util.List;

/**
 * Created by wwwfa on 2017/10/31.
 */

public class Main_Activity extends Activity {
    private static final String TAG = "Main_Activity";
    TimeCount timeCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        timeCount = new TimeCount(2 * 60 * 1000, 1000);
        timeCount.start();
        LogUtil.e(TAG, "Launcher_Update_Start");
        if (Tools.isNetworkConnected())
            new Update_Servlet(this).execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Loading.dismiss();
        if (isForeground())
            ActivityCompat.finishAfterTransition(this);
    }

    /**
     * 当前应用是否处于前台
     *
     * @return
     */
    public boolean isForeground() {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processes = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo processInfo : processes) {
            if (processInfo.processName.equals(getPackageName())) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() {

    }

    /**
     * 计时器
     */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        //倒计时完成
        @Override
        public void onFinish() {
            timeCount.start();
            //再次启动
            new Update_Servlet(Main_Activity.this).execute();

        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示

        }
    }
}
