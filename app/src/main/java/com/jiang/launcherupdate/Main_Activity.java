package com.jiang.launcherupdate;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

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
        timeCount = new TimeCount(10 *60* 1000, 1000);
        timeCount.start();

        if (isForeground()){

                Intent home=new Intent(Intent.ACTION_MAIN);
                home.addCategory(Intent.CATEGORY_HOME);  
                startActivity(home);

        }

    }

    /**
     * 当前应用是否处于前台
     *
     * @return
     */
    public  boolean isForeground() {
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

            //即没连接wifi也没插网线
//            if (!Tools.isWifiConnected() && !Tools.isLineConnected() && !Tools.isNetworkConnected() && num == 1)
//
//                new NetWarningDialog(MyAppliaction.activity).show();
//            else {
//                //如果是有线网络接入
//                if (Tools.isLineConnected() && num == 3 && MyAppliaction.activity != null)
//                    new NetWarningDialog(MyAppliaction.activity).show();
//
//                //如果是WIFI接入
//                if (Tools.isWifiConnected() && num == 10 && MyAppliaction.activity != null)
//                    new NetWarningDialog(MyAppliaction.activity).show();
//            }
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示

        }
    }
}
